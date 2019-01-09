package com.gmail.tamyewwah.mywallet;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageFragment extends Fragment {

    private String userID;
    private Double total;
    private String userPin;
    private RecyclerView billList;
    private RecyclerView.Adapter adapter;
    private DatabaseReference BillDatabase=FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRef = BillDatabase.child("Message");
    private View view;
    private ArrayList<MessageBill> ArrayBill = new ArrayList<>();
    private ProgressBar progressBar;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Bundle bundle=getArguments();
//        if (bundle != null)
//        {
//            total = getArguments().getString("Total_Amount");
//        }
//
//    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        userID=((MainActivity)getActivity()).USER_ID();
        userPin=((MainActivity)getActivity()).getUserPin();
        view = inflater.inflate(R.layout.fragment_recycler,container, false);
        progressBar=view.findViewById(R.id.progressbarMessage);
        billList=view.findViewById(R.id.recyclerBill);
        final FloatingActionButton buttonPay = view.findViewById(R.id.buttonFloatBill);
        billList.setHasFixedSize(true);
        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) LayoutManager).setOrientation(LinearLayout.VERTICAL);
        billList.setLayoutManager(LayoutManager);
        billList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && buttonPay.getVisibility() == View.VISIBLE) {
                    buttonPay.hide();
                } else if (dy < 0 &&buttonPay.getVisibility() != View.VISIBLE) {
                    buttonPay.show();
                }
            }
        });
        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String BillCode;
                String Company;
                String Description;
                String ExpireDate;
                Double Amount;
                String UserID;

                for(DataSnapshot postData : dataSnapshot.getChildren()) {


                    BillCode=postData.child("Bill_Code").getValue().toString();
                    Company=postData.child("Company").getValue().toString();
                    Description=postData.child("Description").getValue().toString();
                    ExpireDate=postData.child("ExpireDate").getValue().toString();
                    Amount=Double.parseDouble(postData.child("Total_Amount").getValue().toString());
                    UserID=postData.child("User").getValue().toString();
                    if(UserID.matches(userID)) {
                        MessageBill Bill = new MessageBill(BillCode, Company, Description, ExpireDate, Amount, UserID);
                        ArrayBill.add(Bill);
                    }
                }

                adapter = new CardViewDataAdapter(ArrayBill);
                billList.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final FloatingActionButton floatingActionButton = view.findViewById(R.id.buttonFloatBill);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {


                total = ((CardViewDataAdapter)billList.getAdapter()).result();
                if(total!=0.0) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Total Amount Bills");
                    alertDialog.setMessage("Total amount :RM" + total);
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent =new Intent(getActivity(),Pin.class).putExtra("pinNumber",userPin);
                                    startActivity(intent);
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    alertDialog.show();
                }
                else
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setTitle("Total Amount Bills");
                    alertDialog.setMessage("Please select at least one bills to pay");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    alertDialog.show();
                }

            }
        });



        return view;
    }


}
