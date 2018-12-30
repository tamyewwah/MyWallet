package com.gmail.tamyewwah.mywallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FeedbackFragment extends Fragment {

    private EditText editTextSubject;
    private EditText editTextMessage;
    private Button buttonSend;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback,container,false);

        editTextMessage=view.findViewById(R.id.edit_text_subject);
        editTextSubject=view.findViewById(R.id.edit_text_message);
        editTextMessage.addTextChangedListener(EmailTextWatcher);
        editTextSubject.addTextChangedListener(EmailTextWatcher);
        buttonSend = view.findViewById(R.id.button_send);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
        return view;
    }

    private TextWatcher EmailTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String subject =editTextSubject.getText().toString().trim();
            String message = editTextMessage.getText().toString().trim();
            buttonSend.setEnabled(!subject.isEmpty()&&!message.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private void sendMail(){
        String recipient = "tamyewwah@gmail.com";
        String[] recipients = recipient.split(",");
        String subject = editTextSubject.getText().toString();
        String message =editTextMessage.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,message);

        intent.setType("message/rfc822");
        startActivityForResult(Intent.createChooser(intent,"Choose an email client"),0);



    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode  == 0) {
            Toast.makeText(getActivity(), "Your message has been sent successfully.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(),MainActivity.class);
            startActivity(intent);

        }

    }
}