package com.gmail.tamyewwah.mywallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class PromotionFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_promotion, container, false);
//        Button btnaddvoucher = view.findViewById(R.id.btn_AddVoucher);
//        Button btnmyvoucher = view.findViewById(R.id.btn_MyVoucher);
        Button btnnews = view.findViewById(R.id.btn_News);


//        btnaddvoucher.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in = new Intent(getActivity(),AddVoucherActivity.class);
//                in.putExtra("some","some data");
//                startActivity(in);
//            }
//        });
//        btnmyvoucher.setOnClickListener(new View.OnClickListener(){
//                                            @Override
//                                            public void onClick(View v) {
//                                                Intent in = new Intent(getActivity(),MyVoucherActivity.class);
//                                                in.putExtra("some","some data");
//                                                startActivity(in);
//                                            }
//                                        }
//        );

        btnnews.setOnClickListener(new View.OnClickListener() {

                                       @Override
                                       public void onClick(View v) {
                                           Intent in = new Intent(getActivity(),NewsActivity.class);
                                           in.putExtra("some","some data");
                                           startActivity(in);
                                       }
                                   }
        );
        return view;
    }



}
