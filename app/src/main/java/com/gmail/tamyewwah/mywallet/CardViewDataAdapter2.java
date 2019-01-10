package com.gmail.tamyewwah.mywallet;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class CardViewDataAdapter2 extends RecyclerView.Adapter<CardViewDataAdapter2.ViewHolder> {
    private ArrayList<Card> ArrayCardDetails;
    private ArrayList<Card> ArraySelectedCard = new ArrayList<>();

    public CardViewDataAdapter2(ArrayList<Card> ArrayCardDetails) {
            this.ArrayCardDetails=ArrayCardDetails;
        }

        @Override
        public CardViewDataAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.fragment_carddetails, parent,false);

            ViewHolder viewHolder = new ViewHolder(itemLayoutView);
            return viewHolder;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

            final Card msgCard =ArrayCardDetails.get(position);
            viewHolder.checkboxCard.setOnCheckedChangeListener(null);
            viewHolder.checkboxCard.setChecked(msgCard.isSelected());

            viewHolder.checkboxCard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    msgCard.setSelected(isChecked);
                    if(viewHolder.checkboxCard.isChecked()) {
                        ArraySelectedCard.add(msgCard);
                    }
                    else
                    {
                        ArraySelectedCard.remove(msgCard);
                    }
                }
            });
            viewHolder.CVCNumTxt.setText("CVC Number :"+ArrayCardDetails.get(position).getCVC());
            viewHolder.CardNameTxt.setText("Name on Card :"+ArrayCardDetails.get(position).getCardName());
            viewHolder.CardTypeTxt.setText("Card Type :"+ArrayCardDetails.get(position).getType());
        }


        @Override
        public int getItemCount() {
            return ArrayCardDetails.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            private TextView CardNameTxt;
            private TextView CVCNumTxt;
            private TextView CardTypeTxt;
            private CheckBox checkboxCard;

            public ViewHolder(View itemLayoutView) {
                super(itemLayoutView);
                checkboxCard = itemLayoutView.findViewById(R.id.checkboxCard);
                CVCNumTxt= itemLayoutView.findViewById(R.id.post_cvcNum);
                CardNameTxt =itemLayoutView.findViewById(R.id.post_cardName);
                CardTypeTxt = itemLayoutView.findViewById(R.id.post_cardType);
            }
        }

        public ArrayList<Card> getCard()
        {
            return ArraySelectedCard;
        }

    }

