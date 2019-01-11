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

public class CardViewDataAdapter3 extends RecyclerView.Adapter<CardViewDataAdapter3.ViewHolder> {
    private ArrayList<Blog> ArrayVoucher;
//    private double TotalAmount=0.0;
    private ArrayList<Blog> ArraySelectedBill = new ArrayList<>();
    public CardViewDataAdapter3(ArrayList<Blog> ArrayVoucher) {
        this.ArrayVoucher=ArrayVoucher;
    }

    @Override
    public CardViewDataAdapter3.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.fragment_my_voucher2, parent,false);



        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {


        final Blog blog =ArrayVoucher.get(position);
//        viewHolder.checkboxBill.setOnCheckedChangeListener(null);
//        viewHolder.checkboxBill.setChecked(msgBill.getSelected());

//        viewHolder.checkboxBill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
////                msgBill.setSelected(isChecked);
////                if(viewHolder.checkboxBill.isChecked()) {
////                    ArraySelectedBill.add(msgBill);
////                    TotalAmount += msgBill.getAmount();
////                }
////                else
////                {
////                    ArraySelectedBill.remove(msgBill);
////                    TotalAmount-=msgBill.getAmount();
////                }
////                messageFragment.getFragmentManager().beginTransaction().replace(R.id.container,messageFragment).commit();
//
//
//
//            }
//        });


        viewHolder.CompanyTxt.setText("Company  :"+ArrayVoucher.get(position).getCompany());
        viewHolder.DescriptionTxt.setText("Description :"+ArrayVoucher.get(position).getDescription());
        viewHolder.DiscountTxt.setText("Discount rate:"+ArrayVoucher.get(position).getDiscount_rate());
        viewHolder.EndDateTxt.setText("Expire Date :"+ArrayVoucher.get(position).getEndDate());

    }


    @Override
    public int getItemCount() {
        return ArrayVoucher.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView CompanyTxt;
        private TextView DescriptionTxt;
        private TextView DiscountTxt;
        private TextView EndDateTxt;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            CompanyTxt =itemLayoutView.findViewById(R.id.post_companyV);
            DescriptionTxt = itemLayoutView.findViewById(R.id.post_descriptionV);
            DiscountTxt=itemLayoutView.findViewById(R.id.post_discountV);
            EndDateTxt =itemLayoutView.findViewById(R.id.post_endDateV);

        }
    }
//    public Double result()
//    {
//        return TotalAmount;
//    }
//    public ArrayList<MessageBill> getBill()
//    {
//        return ArraySelectedBill;
//    }

}
