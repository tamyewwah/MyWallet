package com.gmail.tamyewwah.mywallet;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CardViewDataAdapter extends RecyclerView.Adapter<CardViewDataAdapter.ViewHolder> {
    private ArrayList<MessageBill> ArrayBill;



    // Provide a suitable constructor (depends on the kind of dataset)
    public CardViewDataAdapter(ArrayList<MessageBill> ArrayBill) {
        this.ArrayBill=ArrayBill;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CardViewDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.fragment_message, parent,false);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData

        viewHolder.BillCodeTxt.setText("Bill Code :"+ArrayBill.get(position).getBillCode());
        viewHolder.CompanyTxt.setText("Company :"+ArrayBill.get(position).getCompany());
        viewHolder.DescTxt.setText("Description :"+ArrayBill.get(position).getDescription());
        viewHolder.ExDateTxt.setText("Expire Date :"+ArrayBill.get(position).getExpireDate());
        viewHolder.AmountTxt.setText("Total :"+String.valueOf(ArrayBill.get(position).getAmount()));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return ArrayBill.size();
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView BillCodeTxt;
        private TextView CompanyTxt;
        private TextView DescTxt;
        private TextView ExDateTxt;
        private TextView AmountTxt;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            BillCodeTxt= itemLayoutView.findViewById(R.id.post_billCode);
            CompanyTxt =itemLayoutView.findViewById(R.id.post_company);
            DescTxt = itemLayoutView.findViewById(R.id.post_desc);
            ExDateTxt=itemLayoutView.findViewById(R.id.post_ExDate);
            AmountTxt =itemLayoutView.findViewById(R.id.post_amount);

        }
    }

}
