package com.gmail.tamyewwah.mywallet;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.sip.SipSession;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.Result;

import org.w3c.dom.Text;

import java.io.IOException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission_group.CAMERA;


public class PaymentFragment extends Fragment implements ZXingScannerView.ResultHandler {
    SurfaceView surfaceView;
    TextView textview;
    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        scannerView = new ZXingScannerView(getActivity());
        getActivity().setContentView(scannerView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkPermission()) {
                Toast.makeText(getActivity(), "Permission is granted", Toast.LENGTH_LONG).show();
            } else {
                requestPermission();
            }
        }

//        surfaceView = view.findViewById(R.id.cameraPreview);
//        textview = view.findViewById(R.id.txtResult);
//
//        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(getActivity())
//                .setBarcodeFormats(Barcode.QR_CODE).build();
//
//
//
//        final CameraSource cameraSource = new CameraSource.Builder(getActivity(),barcodeDetector)
//                .setRequestedPreviewSize(640,480).build();
//
//        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
//            @Override
//            public void surfaceCreated(SurfaceHolder holder) {
//                if(ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED)
//                {
//                    return;
//                }
//                try
//                {
//
//                    cameraSource.start(holder);
//                }catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//
//
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {
//
//                cameraSource.stop();
//            }
//        });
//
//         barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
//             @Override
//             public void release() {
//
//             }
//
//             @Override
//             public void receiveDetections(Detector.Detections<Barcode> detections) {
//                 final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
//                 if(qrcodes.size()!=0)
//                 {
//                     textview.post(new Runnable() {
//                         @Override
//                         public void run() {
//                             Vibrator vibrator = (Vibrator) getActivity().getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
//                             vibrator.vibrate(1000);
//                             textview.setText(qrcodes.valueAt(0).displayValue );
//
//                         }
//                     });
//                 }
//             }
//         });
        return view;
    }

    private boolean checkPermission() {

        return (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA}, REQUEST_CAMERA);
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
                    scannerView=new ZXingScannerView(getActivity());
                    getActivity().setContentView(scannerView);
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
    public void onDestroy(){
        super.onDestroy();
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Scan Result");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(PaymentFragment.this);
            }
        });
        builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(scanResult));
            }
        });
    }
}