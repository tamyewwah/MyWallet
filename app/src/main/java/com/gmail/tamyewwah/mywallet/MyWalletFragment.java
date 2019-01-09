package com.gmail.tamyewwah.mywallet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MyWalletFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mywallet,container, false);

        Button button = view.findViewById(R.id.buttonAdd);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddCardDetails.class);
                intent.putExtra("some","some data");
                startActivity(intent);
            }
        });

        return view;
    }
}
