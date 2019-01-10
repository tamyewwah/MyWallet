package com.gmail.tamyewwah.mywallet;


import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.DateFormat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

public class TransferFragment extends Fragment {
    private String userID;
    private Double total;
    private String userPin;
    private EditText inputbank, inputaccountNo, inputamount;
    View view;

    private DatabaseReference TransactionDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = TransactionDatabase.child("Transaction");

    Button btnTransaction;
    Date date;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        userID = ((MainActivity) getActivity()).USER_ID();
        userPin = ((MainActivity) getActivity()).getUserPin();
        super.onCreate(savedInstanceState);

        inputbank = (EditText) view.findViewById(R.id.EditBank);
        inputaccountNo = (EditText) view.findViewById(R.id.EditAccountNo);
        inputamount = (EditText) view.findViewById(R.id.EditAmount);

        btnTransaction.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(validateInput() == true){
                    startActivity(new Intent(getActivity(), Pin.class));
                }
            }
        });

        return inflater.inflate(R.layout.fragment_transfer, container, false);
    }

    private void addTransaction() {

        String name = inputaccountNo.getText().toString().trim();
        Double total = Double.parseDouble(inputamount.getText().toString().trim());
        String date = DateFormat.getDateInstance().format("DD/MM/YYYY").toString();
        Transaction transaction = new Transaction(name, date, total, userID);

        TransactionDatabase.child(userID).setValue(transaction);
    }

    public boolean validateInput() {
        boolean isValid = false;
        if (TextUtils.isEmpty(inputbank.getText().toString().trim())) {
            Toast.makeText(getContext(), "Enter bank name!", Toast.LENGTH_SHORT).show();
            return isValid;
        }
        if (TextUtils.isEmpty(inputamount.getText().toString().trim())) {
            Toast.makeText(getContext(), "Enter acoount no.!", Toast.LENGTH_SHORT).show();
            return isValid;
        }

        if (TextUtils.isEmpty(inputamount.getText().toString().trim())) {
            Toast.makeText(getContext(), "Enter transaction amount!", Toast.LENGTH_SHORT).show();
            return isValid;
        }
        if (!TextUtils.isEmpty(inputbank.getText().toString().trim()) && !TextUtils.isEmpty(inputamount.getText().toString().trim()) && !TextUtils.isEmpty(inputamount.getText().toString().trim()))
            isValid = true;

        return isValid;
    }

}
