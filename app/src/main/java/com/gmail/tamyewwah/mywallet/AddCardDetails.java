package com.gmail.tamyewwah.mywallet;

import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddCardDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

     private EditText inputCardName, inputCvcNumber, inputValidThru;
     private Button btnAddCard;
     private FirebaseAuth auth;
     private DatabaseReference databaseUser;

     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_add_card);

         //Get Firebase auth instance
         auth = FirebaseAuth.getInstance();
         databaseUser = FirebaseDatabase.getInstance().getReference("users");

         inputCardName = (EditText) findViewById(R.id.input_cardName);
         inputCvcNumber = (EditText) findViewById(R.id.input_cvcNumber);
         inputValidThru = (EditText) findViewById(R.id.input_validThru);
         btnAddCard =  (Button) findViewById(R.id.buttonAddCard);

         btnAddCard.setOnClickListener(new View.OnClickListener(){
             public void onClick(View v) {
                 String CardName = inputCardName.getText().toString().trim();
                 String CvcNumber = inputCvcNumber.getText().toString().trim();
                 String ValidThru = inputValidThru.getText().toString().trim();

                 if (TextUtils.isEmpty(CardName)) {
                     Toast.makeText(getApplicationContext(), "Enter Name on Card!", Toast.LENGTH_SHORT).show();
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
             }
         });

         Bundle bundle = getIntent().getExtras();
         if(bundle !=null){
             if(bundle.getString("some") != null){
                 Toast.makeText(getApplicationContext(),"data" +
                 bundle.getString("some"),Toast.LENGTH_SHORT).show();
             }
         }

         Spinner spinner = findViewById(R.id.spinnerCardType);
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
             String spinnerLabel = adapterView.getItemAtPosition(i).toString();
             displayToast(spinnerLabel);
         }
         @Override
         public void onNothingSelected (AdapterView <?> adapterView){

         }
}
