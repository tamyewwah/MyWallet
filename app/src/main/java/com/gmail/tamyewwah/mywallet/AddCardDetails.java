package com.gmail.tamyewwah.mywallet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddCardDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

     private EditText inputCardName, inputCardNum, inputCvcNumber, inputValidThru;
     private DatabaseReference BankDatabase=FirebaseDatabase.getInstance().getReference();
     DatabaseReference conditionRef = BankDatabase.child("bankAccount");
     private Spinner spinner;
     private Button btnAddCard;
     private String spinnerLabel;
     private FirebaseAuth auth;
     private DatabaseReference databaseUser;
     private String CardNum;
     private String CardTotal;

     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_add_card);

         //Get Firebase auth instance
         auth = FirebaseAuth.getInstance();
         databaseUser = FirebaseDatabase.getInstance().getReference("Card");

         inputCardName = (EditText) findViewById(R.id.rEditCardName);
         inputCardNum = (EditText) findViewById(R.id.rEditCardNum);
         inputCvcNumber = (EditText) findViewById(R.id.rEditCVC);
         inputValidThru = (EditText) findViewById(R.id.rEditValidThru);
         btnAddCard = (Button) findViewById(R.id.buttonAddCard);

         btnAddCard.setOnClickListener(new View.OnClickListener(){
             public void onClick(View v) {
                 CardNum = inputCardNum.getText().toString().trim();
                 String CvcNumber = inputCvcNumber.getText().toString().trim();
                 String CardName = inputCardName.getText().toString().trim();
                 String ValidThru = inputValidThru.getText().toString().trim();

                 if (TextUtils.isEmpty(CardName)) {
                     Toast.makeText(getApplicationContext(), "Enter Name on Card!", Toast.LENGTH_SHORT).show();
                     return;
                 }
                 if (TextUtils.isEmpty(CardNum)) {
                     Toast.makeText(getApplicationContext(), "Enter Card Number!", Toast.LENGTH_SHORT).show();
                     return;
                 }
                 if (TextUtils.isEmpty(CvcNumber)) {
                     Toast.makeText(getApplicationContext(), "Enter CVC Number!", Toast.LENGTH_SHORT).show();
                     return;
                 }
                 if (TextUtils.isEmpty(ValidThru)) {
                     Toast.makeText(getApplicationContext(), "Enter Valid Till Date!", Toast.LENGTH_SHORT).show();
                     return;
                 }
                 if (CvcNumber.length() > 3 ) {
                     Toast.makeText(getApplicationContext(), "CVC Number cannot more than 3 digits", Toast.LENGTH_SHORT).show();
                     return;
                 }
                 if (CardNum.length() != 16) {
                     Toast.makeText(getApplicationContext(), "Card Number must be 16 digits!", Toast.LENGTH_SHORT).show();
                     return;
                 }
                 conditionRef.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         String getCardNumber,getBalance;

                         for(DataSnapshot postData : dataSnapshot.getChildren()) {
                             getCardNumber = postData.child("card").getValue().toString();
                             getBalance = postData.child("balance").getValue().toString();
                             if(getCardNumber.matches(CardNum)){
                                 CardTotal = getBalance;
                                 addCard();
                             }
                         }
                     }
                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 });
             }
         });

         Bundle bundle = getIntent().getExtras();

         spinner = findViewById(R.id.spinnerCardType);
         if (spinner != null) {
             spinner.setOnItemSelectedListener(this);
         }
         ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cardType_array, android.R.layout.simple_spinner_item);
         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         // Apply the adapter to the spinner.
         if (spinner != null) {
             spinner.setAdapter(adapter);
         }
     }

         public void displayToast (String message){
             Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
         }
         @Override
         public void onItemSelected (AdapterView <?> adapterView, View view,int i, long l){
             spinnerLabel = adapterView.getItemAtPosition(i).toString();
             displayToast(spinnerLabel);
         }
         @Override
         public void onNothingSelected (AdapterView <?> adapterView){

         }

         private void addCard() {

            String cardnum = inputCardNum.getText().toString();
            String CVC = inputCvcNumber.getText().toString();
            String CardName = inputCardName.getText().toString();
            String GoodThru  = inputValidThru.getText().toString();
            String Total = CardTotal;
            String Type = spinnerLabel;
            String User = auth.getCurrentUser().getUid();
            Card card = new Card(CVC,CardName,GoodThru,Total,Type,User);

            databaseUser.child(cardnum).setValue(card);
        }

}
