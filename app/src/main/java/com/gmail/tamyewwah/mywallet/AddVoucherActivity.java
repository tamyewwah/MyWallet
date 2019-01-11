package com.gmail.tamyewwah.mywallet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Picture;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.EventLogTags;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;

import java.util.Random;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission_group.CAMERA;


public class AddVoucherActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private FirebaseUser currentUser =FirebaseAuth.getInstance().getCurrentUser();
    TextView textview;
    private DatabaseReference Database=FirebaseDatabase.getInstance().getReference();
    DatabaseReference conditionRefPromotion = Database.child("Promotion");
    DatabaseReference conditionRefUser = Database.child("User");
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;
    private LinearLayout qrCamera;
    private String Company ;
    private String Discount;
    private String StartDate ;
    private String EndDate ;
    private String Description;
    private String Name;
    private String Picture;
    private String UserID;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_voucher);
        UserID = currentUser.getUid();
        qrCamera = findViewById(R.id.qrcodecameraV);
        scannerView = new ZXingScannerView(getApplicationContext());
        scannerView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        qrCamera.addView(scannerView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(this, "Permission is granted", Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }
        }
    }

    @Nullable


    private boolean checkPermission() {

        return (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
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
                    scannerView = new ZXingScannerView(getApplicationContext());
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
        new AlertDialog.Builder(this)
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(AddVoucherActivity.this);
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
        else if(scanResult.contains("-")&&scanResult.contains(",")&&scanResult.contains("/"))
        {
            Company = scanResult.substring(0,scanResult.indexOf("-"));
            Discount =scanResult.substring(scanResult.indexOf("-")+1,scanResult.indexOf(","));
            StartDate =scanResult.substring(scanResult.indexOf(",")+1,scanResult.indexOf("/"));
            EndDate = scanResult.substring(scanResult.indexOf("/")+1,scanResult.length());
            FinalResult="Company :"+Company +"\n"+"Discount rate:"+Discount+"\n"+"Start Date"+StartDate+"\n"+"End Date"+EndDate;

            Description="";
            Name="";
            Picture="";
            builder.setNeutralButton("Confirm", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Blog blog = new Blog(Company, Description, Double.parseDouble(Discount), EndDate, Name, Picture, StartDate);
                    Random random = new Random();
                    int n =random.nextInt(10000)+1;
                    conditionRefPromotion.child("P"+n).setValue(blog);

                    conditionRefUser.child(UserID).child("Promotion").child("ID").child("P"+n).setValue("P"+n);



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