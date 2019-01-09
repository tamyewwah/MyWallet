package com.gmail.tamyewwah.mywallet;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CardViewDataAdapter1 extends RecyclerView.Adapter<CardViewDataAdapter1.ViewHolder> {
    private ArrayList<TransactionRecord> ArrayTransaction;


    public CardViewDataAdapter1(ArrayList<TransactionRecord> ArrayTransaction) {
        this.ArrayTransaction = ArrayTransaction;
    }

    @Override
    public CardViewDataAdapter1.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.fragment_transaction_record, parent, false);


        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {


        //final TransactionRecord Transaction =ArrayTransaction.get(position);

        viewHolder.NameTxt.setText("Name :" + ArrayTransaction.get(position).getName());
        viewHolder.DateTxt.setText("Transaction Date :" + ArrayTransaction.get(position).getDate());
        viewHolder.AmountTxt.setText("Total Amount:" + String.valueOf(ArrayTransaction.get(position).getAmount()));

    }


    @Override
    public int getItemCount() {
        return ArrayTransaction.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView NameTxt;
        private TextView DateTxt;
        private TextView AmountTxt;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            NameTxt = itemLayoutView.findViewById(R.id.txtName);
            DateTxt = itemLayoutView.findViewById(R.id.txtDate);
            AmountTxt = itemLayoutView.findViewById(R.id.txtAmount);

        }
    }


}
