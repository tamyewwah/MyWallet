package com.gmail.tamyewwah.mywallet;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

    private DatabaseReference Database=FirebaseDatabase.getInstance().getReference();
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
    private String Message;
    private ArrayList<String> BillCode;
    private ArrayList<String> Company;
    private ArrayList<String> Amount;
    private boolean flag =false;
    private FirebaseUser currentUser =FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        pinNumber = findViewById(R.id.editTextPin);
        buttonPin = findViewById(R.id.buttonPin);
        conditionRef.getRoot();
        conditionRef.keepSynced(true);
        conditionRefBank.getRoot();
        conditionRefBank.keepSynced(true);
        conditionRefTransaction.getRoot();
        conditionRefTransaction.keepSynced(true);
        BillCode=getIntent().getStringArrayListExtra("billCode");
        Company=getIntent().getStringArrayListExtra("Company");
        Amount=getIntent().getStringArrayListExtra("Amount");
        getData = getIntent().getStringExtra("pinNumber");
        getPinFromUser = getData.substring(0,getData.indexOf("-"));
        getTotalFromMessage = Double.parseDouble(getData.substring(getData.indexOf("-")+1,getData.indexOf(",")));
        typePayment =getData.substring(getData.indexOf(",")+1,getData.length());
            buttonPin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Pin.this);

                    builder.setTitle("Confirmation");

                    getPin = pinNumber.getText().toString();
                    if (getPin.matches(getPinFromUser)) {

                        Message ="Confirm to pay";
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
                                            conditionRefBank.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    String getCardNumber;


                                                    for (DataSnapshot postData2 : dataSnapshot.getChildren()) {


                                                        getCardNumber = postData2.child("card").getValue().toString();

                                                        if (CardNum.matches(getCardNumber)) {
                                                            AccountNum = postData2.getKey();
                                                            CardNum = getCardNumber;
                                                            flag = true;
                                                            break;
                                                        }

                                                    }


                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
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
                        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (flag == true) {


                                    try {


                                        if (typePayment.matches("Bill")) {


                                            for (int i = 0; i < BillCode.size(); i++) {
                                                Transaction transaction = new Transaction(Company.get(i), simpleDateFormat.format(date), Double.parseDouble(Amount.get(i)), UserID);

                                                Random random = new Random();
                                                int n = random.nextInt(10000) + 1;
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
                                            conditionRef.child(CardNum).child("total").setValue(DeductedTotal);


                                        } else {

                                            Transaction transaction = new Transaction(typePayment, simpleDateFormat.format(date), getTotalFromMessage, UserID);
                                            Random random = new Random();
                                            int n = random.nextInt(10000) + 1;
                                            conditionRefTransaction.child("T" + n).setValue(transaction);
                                            conditionRef.child(CardNum).child("total").setValue(DeductedTotal);


                                        }
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
                                    Intent intent = new Intent(Pin.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getApplicationContext(), "Canceled payment", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Pin.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });


                    }
                    else
                    {
                        Message="Sorry, please enter 6 pins again!";
                        pinNumber.setText("");
                    }
                    builder.setMessage(Message);
                    AlertDialog alert = builder.create();
                    alert.show();

                }


            });





    }


}
