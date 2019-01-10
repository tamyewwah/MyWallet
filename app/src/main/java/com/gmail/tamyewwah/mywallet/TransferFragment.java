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
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class TransferFragment extends Fragment {
    private String userID;
    private String total;
    private String userPin;
    private EditText inputbank, inputaccountNo, inputamount;

    private DatabaseReference TransactionDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = TransactionDatabase.child("Transaction");

    Button btnTrans;
    Date date;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transfer, container, false);
        if (container == null)
            return inflater.inflate(R.layout.fragment_transfer, container, false);
        userID = ((MainActivity) getActivity()).USER_ID();
        userPin = ((MainActivity) getActivity()).getUserPin();
        //super.onCreate(savedInstanceState);

        inputbank = (EditText)view.findViewById(R.id.EditBank);
        inputaccountNo = (EditText)view.findViewById(R.id.EditAccountNo);
        inputamount = (EditText)view.findViewById(R.id.EditAmount);
        btnTrans = (Button)view.findViewById(R.id.btn_transfer);


        btnTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput()) {
                    total=inputamount.getText().toString().trim();
                    Intent intent =new Intent(getActivity(),Pin.class).putExtra("pinNumber",userPin+"-"+total+","+"Transfer");

                                    startActivity(intent);
                    Toast.makeText(getActivity(), "Transaction Successful!", Toast.LENGTH_SHORT).show();
                    addTransaction();
                }
                else
                {
                    Toast.makeText(getActivity(), "Transaction Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void addTransaction() {

        String name = inputaccountNo.getText().toString().trim();
        Double total = Double.parseDouble(inputamount.getText().toString().trim());
//        String date = DateFormat.getDateInstance().format("DD/MM/YYYY").toString();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df =new SimpleDateFormat("dd/mm/yyyy");
        String formattedDate = df.format(c);
        Transaction transaction = new Transaction(name, formattedDate, total, userID);

        Random random = new Random();
        int n = random.nextInt(1000) + 1;
        conditionRef.child("T"+n).setValue(transaction);
    }

    public boolean validateInput() {
        boolean isValid = false;
        if (TextUtils.isEmpty(inputbank.getText().toString().trim())) {
            Toast.makeText(getContext(), "Enter bank name!", Toast.LENGTH_SHORT).show();
            return isValid;
        }
        if (TextUtils.isEmpty(inputaccountNo.getText().toString().trim())) {
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
