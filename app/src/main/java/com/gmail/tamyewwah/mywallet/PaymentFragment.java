package com.gmail.tamyewwah.mywallet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.Result;


import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission_group.CAMERA;


public class PaymentFragment extends Fragment implements ZXingScannerView.ResultHandler {
    private DatabaseReference Database=FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRefPromotion = Database.child("Promotion");
    TextView textview;
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private LinearLayout qrCamera;
    private  String userID;
    private String userPin;
    private String CompanyPay;
    private String PromotionID;
    private String DiscountRate;
    private String CompanyName;
    private Double TotalPay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        MainActivity mainActivity =(MainActivity)getActivity();
        userID = mainActivity.USER_ID();
        userPin=((MainActivity)getActivity()).getUserPin();
        qrCamera = view.findViewById(R.id.qrcodecamera);
        scannerView = new ZXingScannerView(getActivity().getApplicationContext());
        scannerView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        qrCamera.addView(scannerView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(getActivity(), "Permission is granted", Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }
        }
        conditionRefPromotion.getRoot();
        conditionRefPromotion.keepSynced(true);
        return view;
    }

    private boolean checkPermission() {

        return (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA))
                            {
                                displayAlertMessage("You need to allow access for both permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }
                break;
        }




    }
    @Override
    public void onResume()
    {
        super.onResume();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            if(checkPermission())
            {
                if (scannerView==null)
                {
                    scannerView = new ZXingScannerView(getActivity().getApplicationContext());
                    qrCamera.addView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }
            else
            {
                requestPermission();
            }
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        scannerView.stopCamera();
    }

    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener)
    {
        new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("OK",listener)
                .setNegativeButton("Cancel",null)
                .create()
                .show();
    }

    @Override
    public void handleResult(Result result) {
        final String scanResult =result.getText();
        String FinalResult="";
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Scan Result");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(PaymentFragment.this);
            }
        });
        if(scanResult.contains("http://")||scanResult.contains("www")||scanResult.contains(".com")) {
            builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(scanResult));
                    startActivity(intent);


                }
            });

        }
        else if(scanResult.contains("-"))
        {

            CompanyPay = scanResult.substring(0,scanResult.indexOf("-"));
                conditionRefPromotion.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            for (DataSnapshot companySnapshot : dataSnapshot.getChildren()) {

                                CompanyName = companySnapshot.child("company").getValue().toString();
                                if (CompanyName.matches(CompanyPay)) {
                                    PromotionID = companySnapshot.getKey();
                                    DiscountRate = companySnapshot.child("discount_rate").getValue().toString();
                                }

                            }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            FinalResult="Pay To :"+scanResult.substring(0,scanResult.indexOf("-"))+"\n"+"RM :"+ scanResult.substring(scanResult.indexOf("-")+1,scanResult.length());

            builder.setNeutralButton("Pay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try{
                        TotalPay =Double.parseDouble(scanResult.substring(scanResult.indexOf("-")+1,scanResult.length()))- Double.parseDouble(scanResult.substring(scanResult.indexOf("-")+1,scanResult.length()))*Double.parseDouble(DiscountRate)/100;

                        Toast.makeText(getActivity(),"Your Bill ", Toast.LENGTH_SHORT).show();
                    }catch (Exception e)
                    {
                        TotalPay=Double.parseDouble(scanResult.substring(scanResult.indexOf("-")+1,scanResult.length()));
                    }


                    Intent intent =new Intent(getActivity(),Pin.class).putExtra("pinNumber",userPin+"-"+TotalPay.toString()+","+scanResult.substring(0,scanResult.indexOf("-")));

                    startActivity(intent);


                }
            });



        }
        else
        {
            FinalResult=scanResult;

        }

        builder.setMessage(FinalResult);
        AlertDialog alert = builder.create();
        alert.show();


    }
}