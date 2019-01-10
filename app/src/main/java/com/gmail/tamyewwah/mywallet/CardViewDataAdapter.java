package com.gmail.tamyewwah.mywallet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CardViewDataAdapter extends RecyclerView.Adapter<CardViewDataAdapter.ViewHolder> {
    private ArrayList<MessageBill> ArrayBill;
    private double TotalAmount=0.0;
    private ArrayList<MessageBill> ArraySelectedBill = new ArrayList<>();
    public CardViewDataAdapter(ArrayList<MessageBill> ArrayBill) {
        this.ArrayBill=ArrayBill;
    }

    @Override
    public CardViewDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.fragment_message, parent,false);



        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {


        final MessageBill msgBill =ArrayBill.get(position);
        viewHolder.checkboxBill.setOnCheckedChangeListener(null);
        viewHolder.checkboxBill.setChecked(msgBill.getSelected());

        viewHolder.checkboxBill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                msgBill.setSelected(isChecked);
                if(viewHolder.checkboxBill.isChecked()) {
                    ArraySelectedBill.add(msgBill);
                    TotalAmount += msgBill.getAmount();
                }
                else
                {
                    ArraySelectedBill.remove(msgBill);
                    TotalAmount-=msgBill.getAmount();
                }
//                messageFragment.getFragmentManager().beginTransaction().replace(R.id.container,messageFragment).commit();



            }
        });


        viewHolder.BillCodeTxt.setText("Bill Code :"+ArrayBill.get(position).getBillCode());
        viewHolder.CompanyTxt.setText("Company :"+ArrayBill.get(position).getCompany());
        viewHolder.DescTxt.setText("Description :"+ArrayBill.get(position).getDescription());
        viewHolder.ExDateTxt.setText("Expire Date :"+ArrayBill.get(position).getExpireDate());
        viewHolder.AmountTxt.setText("Total :"+String.valueOf(ArrayBill.get(position).getAmount()));

    }


    @Override
    public int getItemCount() {
        return ArrayBill.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView BillCodeTxt;
        private TextView CompanyTxt;
        private TextView DescTxt;
        private TextView ExDateTxt;
        private TextView AmountTxt;
        private CheckBox checkboxBill;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            checkboxBill = itemLayoutView.findViewById(R.id.CheckboxBill);
            BillCodeTxt= itemLayoutView.findViewById(R.id.post_billCode);
            CompanyTxt =itemLayoutView.findViewById(R.id.post_company);
            DescTxt = itemLayoutView.findViewById(R.id.post_desc);
            ExDateTxt=itemLayoutView.findViewById(R.id.post_ExDate);
            AmountTxt =itemLayoutView.findViewById(R.id.post_amount);

        }
    }
    public Double result()
    {
        return TotalAmount;
    }
    public ArrayList<MessageBill> getBill()
    {
        return ArraySelectedBill;
    }

}
