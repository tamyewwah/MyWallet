package com.gmail.tamyewwah.mywallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Pin extends AppCompatActivity {

    private DatabaseReference Database = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = Database.child("Card");
    DatabaseReference conditionRefBank = Database.child("bankAccount");
    DatabaseReference conditionRefTransaction = Database.child("Transaction");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Date date = new Date();
    private String UserID;
    private EditText pinNumber;
    private String getPin;
    private String getData;
    private String getPinFromUser;
    private Double getTotalFromMessage;
    private String typePayment;
    private Button buttonPin;
    private Double DeductedTotal;
    private String CardNum;
    private String AccountName;
    private String AccountNum;
    private ArrayList<String> BillCode;
    private ArrayList<String> Company;
    private ArrayList<String> Amount;
    private boolean flag = false;
    private FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        pinNumber = findViewById(R.id.editTextPin);
        buttonPin = findViewById(R.id.buttonPin);

        BillCode = getIntent().getStringArrayListExtra("billCode");
        Company = getIntent().getStringArrayListExtra("Company");
        Amount = getIntent().getStringArrayListExtra("Amount");
        getData = getIntent().getStringExtra("pinNumber");
        getPinFromUser = getData.substring(0, getData.indexOf("-"));
        getTotalFromMessage = Double.parseDouble(getData.substring(getData.indexOf("-") + 1, getData.indexOf(",")));
        typePayment = getData.substring(getData.indexOf(",") + 1, getData.length());
        buttonPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getPin = pinNumber.getText().toString();
                if (getPin.matches(getPinFromUser)) {
                    UserID = currentUser.getUid();
                    conditionRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Double getTotal;
                            String getUserID;
                            String getName;


                            for (DataSnapshot postData : dataSnapshot.getChildren()) {


                                getName = postData.child("cardName").getValue().toString();
                                getTotal = Double.parseDouble(postData.child("total").getValue().toString());
                                getUserID = postData.child("user").getValue().toString();

                                if (getUserID.matches(UserID)) {
                                    DeductedTotal = getTotal - getTotalFromMessage;
                                    if (DeductedTotal >= 0) {
                                        CardNum = postData.getKey();
                                        AccountName = getName;
                                        flag = true;
                                        break;

                                    }
                                    if (DeductedTotal < 0) {
                                        flag = false;
                                    }


                                }
                            }


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    if (flag == true) {
                        conditionRefBank.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String getCardNumber;
                                for (DataSnapshot postData2 : dataSnapshot.getChildren()) {
                                    getCardNumber = postData2.child("card").getValue().toString();
                                    if (CardNum.matches(getCardNumber)) {
                                        AccountNum = postData2.getKey();
                                        CardNum = getCardNumber;
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                        try {
                            if (typePayment.matches("Bill")) {
                                for (int i = 0; i < BillCode.size(); i++) {
                                    Transaction transaction = new Transaction(Company.get(i), simpleDateFormat.format(date), Double.parseDouble(Amount.get(i)), UserID);
                                    Random random = new Random();
                                    int n = random.nextInt(1000) + 1;
                                    conditionRefTransaction.child("T" + n).setValue(transaction);
                                    Query query = Database.child("Message").orderByChild("Bill_Code").equalTo(BillCode.get(i));
                                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot companySnapshot : dataSnapshot.getChildren()) {
                                                companySnapshot.getRef().removeValue();
                                            }

                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                        }
                                    });
                                }
                            } else {
                                Transaction transaction = new Transaction(typePayment, simpleDateFormat.format(date), getTotalFromMessage, UserID);
                                Random random = new Random();
                                int n = random.nextInt(1000) + 1;
                                conditionRefTransaction.child("T" + n).setValue(transaction);
                            }
                            conditionRef.child(CardNum).child("total").setValue(DeductedTotal);
                            conditionRefBank.child(AccountNum).child("balance").setValue(DeductedTotal);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), "Pay Success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Pin.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    if (flag == false) {
                        Toast.makeText(getApplicationContext(), "Not enough balance to pay", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
