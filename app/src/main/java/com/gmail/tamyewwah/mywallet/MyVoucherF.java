package com.gmail.tamyewwah.mywallet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyVoucherF extends Fragment {

    private RecyclerView.Adapter adapter;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mconditionRef = mDatabase.child("Promotion");
    private View view;
    private ArrayList<Blog> ArrayVoucher = new ArrayList<>();
    private RecyclerView voucherList;

//    private OnFragmentInteractionListener mListener;

    public MyVoucherF() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MyVoucherF newInstance(String param1, String param2) {
        MyVoucherF fragment = new MyVoucherF();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_recycler, container, false);
        voucherList = view.findViewById(R.id.recyclerBill);
        voucherList.setHasFixedSize(true);
        mconditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String Company;
                String Description;
                Double Discount;
                String EndDate;
                String Name;
                String Picture;
                String StartDate;
                for (DataSnapshot postData : dataSnapshot.getChildren()) {
                    Company = postData.child("Company").getValue().toString();
                    Description = postData.child("Description").getValue().toString();
                    Discount = Double.parseDouble(postData.child("Discount_rate").getValue().toString());
                    EndDate = postData.child("EndDate").getValue().toString();
                    Name = postData.child("Name").getValue().toString();
                    Picture = postData.child("Picture").getValue().toString();
                    StartDate = postData.child("StartDate").getValue().toString();
                    Blog blog = new Blog(Company, Description, Discount, EndDate, Name, Picture, StartDate);
                    ArrayVoucher.add(blog);
                }

                adapter = new CardViewDataAdapter3(ArrayVoucher);
                voucherList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }
}