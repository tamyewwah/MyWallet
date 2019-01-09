package com.gmail.tamyewwah.mywallet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TransactionRecordFragment extends Fragment {
    private String userID;
    //    private Double total;
    private RecyclerView TransactionList;
    private RecyclerView.Adapter adapter;
    private DatabaseReference TransactionDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = TransactionDatabase.child("Transaction");
    private View view;
    private ArrayList<TransactionRecord> ArrayTransaction = new ArrayList<>();

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        userID = ((MainActivity) getActivity()).USER_ID();
        view = inflater.inflate(R.layout.fragment_recycler1, container, false);
        TransactionList = view.findViewById(R.id.recyclerTransaction);
        TransactionList.setHasFixedSize(true);
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) LayoutManager).setOrientation(LinearLayout.VERTICAL);
        TransactionList.setLayoutManager(LayoutManager);
        TransactionList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String Name;
                String Date;
                Double Amount;
                String UserID;

                for (DataSnapshot postData : dataSnapshot.getChildren()) {

                    Name = postData.child("Name").getValue().toString();
                    Date = postData.child("Pay_Date").getValue().toString();
                    Amount = Double.parseDouble(postData.child("Total").getValue().toString());
                    UserID = postData.child("User").getValue().toString();
                    if (UserID.matches(userID)) {
                        TransactionRecord Transaction = new TransactionRecord(Name, Date, Amount, UserID);
                        ArrayTransaction.add(Transaction);
                    }
                }

                adapter = new CardViewDataAdapter1(ArrayTransaction);
                TransactionList.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return view;
    }
}
