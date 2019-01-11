package com.gmail.tamyewwah.mywallet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class BalanceFragment extends Fragment {

    private String userID;
    private Double Balance=0.0;
    private Integer NumOfCard=0;
    private TextView TextBa;
    private TextView TextCard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        userID=((MainActivity)getActivity()).USER_ID();
        Balance+=((MainActivity) getActivity()).getBalance();
        NumOfCard+=((MainActivity) getActivity()).getNumOfCard();
        View view =inflater.inflate(R.layout.fragment_balance, container, false);

        TextBa=view.findViewById(R.id.BalanceText);
        TextCard=view.findViewById(R.id.CardText);


        TextCard.setText("Number Of Card :"+NumOfCard);
        TextBa.setText("RM"+Balance);

        return view;
    }
}
