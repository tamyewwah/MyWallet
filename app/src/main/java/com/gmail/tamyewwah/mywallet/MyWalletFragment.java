package com.gmail.tamyewwah.mywallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyWalletFragment extends Fragment {

    private String userID;
    private RecyclerView CardList;
    private RecyclerView.Adapter adapter;
    private DatabaseReference CardDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = CardDatabase.child("Card");

    private View view;

    private ArrayList<Card> CardDetails = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        userID=((MainActivity)getActivity()).USER_ID();
        view = inflater.inflate(R.layout.fragment_mywallet,container, false);
        CardList = view.findViewById(R.id.recycler_view_2);
        CardList.setHasFixedSize(true);
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager)LayoutManager).setOrientation(LinearLayout.VERTICAL);
        CardList.setLayoutManager(LayoutManager);
        CardList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String CVCNum, CardName, GoodThru, Total, CardType, UserID;

                for(DataSnapshot postData : dataSnapshot.getChildren()){
                    CVCNum = postData.child("cvc").getValue().toString();
                    CardName = postData.child("cardName").getValue().toString();
                    GoodThru = postData.child("goodThru").getValue().toString();
                    Total = postData.child("total").getValue().toString();
                    CardType = postData.child("type").getValue().toString();
                    UserID = postData.child("user").getValue().toString();
                    if(UserID.matches(userID)){
                        Card cardDetails = new Card(CVCNum,CardName,GoodThru,Total,CardType,UserID);
                        CardDetails.add(cardDetails);
                    }
                }
                adapter = new CardViewDataAdapter2(CardDetails);
                CardList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button button = view.findViewById(R.id.buttonAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddCardDetails.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
