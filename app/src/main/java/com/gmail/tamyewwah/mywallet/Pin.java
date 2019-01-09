package com.gmail.tamyewwah.mywallet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Pin extends AppCompatActivity {
    private String UserID;
    private EditText pinNumber;
    private String getPin;
    private String getPinFromUser;
    private Button buttonPin;
    private FirebaseUser currentUser =FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        pinNumber = findViewById(R.id.editTextPin);
        buttonPin = findViewById(R.id.buttonPin);

        getPinFromUser = getIntent().getStringExtra("pinNumber");
        Toast.makeText(getApplicationContext(),getPinFromUser, Toast.LENGTH_SHORT).show();

        buttonPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPin=pinNumber.getText().toString();
                if(getPin.matches(getPinFromUser))
                {
                        UserID = currentUser.getUid();
                }
            }
        });



    }

}
