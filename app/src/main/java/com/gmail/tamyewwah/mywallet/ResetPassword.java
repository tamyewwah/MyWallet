package com.gmail.tamyewwah.mywallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;

public class ResetPassword extends AppCompatActivity {
    private EditText inputrfullname, inputrusername, inputrphoneno ,inputEmail, inputrpassword, inputrpin;
    private Button btnReset, btnBack;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private DatabaseReference databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        auth = FirebaseAuth.getInstance();
        databaseUser = FirebaseDatabase.getInstance().getReference("users");

        inputrfullname = (EditText) findViewById(R.id.rEditname);
        inputrusername = (EditText) findViewById(R.id.rEditusername);
        inputrphoneno = (EditText) findViewById(R.id.rEditphoneno);
        inputEmail = (EditText) findViewById(R.id.EditEmail);
        inputrpassword = (EditText) findViewById(R.id.rEditPassword);
        inputrpin = (EditText) findViewById(R.id.rEditPin);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnBack = (Button) findViewById(R.id.btn_back);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResetPassword.this, Login.class));
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!email.contains("@")) {
                    Toast.makeText(getApplicationContext(), "Enter valid email address! (must include '@')", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!email.contains(".com"))  {
                    Toast.makeText(getApplicationContext(), "Enter valid email address! (must include '.com')", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetPassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ResetPassword.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }

                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

    private void resetpassword(){


        String full_name = inputrfullname.getText().toString().trim();
        String user_name = inputrusername.getText().toString().trim();
        String phone_no = inputrphoneno.getText().toString().trim();
        String email = inputEmail.getText().toString().trim();
        String password = inputrpassword.getText().toString().trim();
        String pin = inputrpin.getText().toString().trim();
        String id = auth.getCurrentUser().getUid();
        User user = new User(id, full_name, user_name, phone_no, email, password, pin);

        databaseUser.child(id).setValue(user);
    }
}
