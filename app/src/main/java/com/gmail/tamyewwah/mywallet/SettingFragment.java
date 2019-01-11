package com.gmail.tamyewwah.mywallet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SettingFragment extends Fragment {
    private DatabaseReference Database=FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = Database.child("User");
    private TextView emailText;
    private EditText FullNameEdit;
    private TextView PasswordText;
    private EditText PhoneEdit;
    private EditText PinEdit;
    private EditText UserNameEdit;
    private String UserID;
    private String  email;
    private String FullName;
    private String  Password;
    private String  Phone;
    private String  Pin;
    private String  UserName;
    private Button change;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        UserID=((MainActivity)getActivity()).USER_ID();
        email=((MainActivity)getActivity()).getUserEmail();
        FullName=((MainActivity)getActivity()).getFullName();
        Password=((MainActivity)getActivity()).getPassword();
        Phone=((MainActivity)getActivity()).getPhone();
        Pin=((MainActivity)getActivity()).getUserPin();
        UserName=((MainActivity)getActivity()).getUserName();

        change=view.findViewById(R.id.ButtonSetting);
        emailText=view.findViewById(R.id.emailSetting);
        FullNameEdit=view.findViewById(R.id.FullNameSetting);
        PasswordText=view.findViewById(R.id.PasswordSetting);
        PhoneEdit=view.findViewById(R.id.PhoneSetting);
        PinEdit=view.findViewById(R.id.PinSetting);
        UserNameEdit=view.findViewById(R.id.UserNameSetting);

        emailText.setText(email);
        FullNameEdit.setText(FullName);
        PasswordText.setText(Password);
        PhoneEdit.setText(Phone);
        PinEdit.setText(Pin);
        UserNameEdit.setText(UserName);


        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PhoneEdit.length()==10&&PinEdit.length()==6) {

                        conditionRef.child(UserID).child("full_name").setValue(FullNameEdit.getText().toString());
                        conditionRef.child(UserID).child("phone_no").setValue(PhoneEdit.getText().toString());
                        conditionRef.child(UserID).child("pin").setValue(PinEdit.getText().toString());
                        conditionRef.child(UserID).child("user_name").setValue(UserNameEdit.getText().toString());

                }
                if(PhoneEdit.length()>10||PhoneEdit.length()<10)
                {
                    Toast.makeText(getActivity(),"phone number must be 10 digits", Toast.LENGTH_SHORT).show();
                }
                if(PinEdit.length()>6||PinEdit.length()>6)
                {
                    Toast.makeText(getActivity(),"pin must be 6 digits", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
}
