package com.nicanoritorma.qrattendance.utils;
/**
 * Created by Nicanor Itorma
 */
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;
import com.nicanoritorma.qrattendance.OfflineViewModels.StudentInAttendanceVM;
import com.nicanoritorma.qrattendance.R;
import com.nicanoritorma.qrattendance.model.StudentInAttendanceModel;

public class QrScanner extends Fragment {

    private static final int REQUEST_PERMISSION_CAMERA = 100;
    private CodeScanner mCodeScanner;
    private Toast toast;

    public QrScanner() {
        super(R.layout.activity_qr_scanner);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Activity activity = requireActivity();
        int EXTRA_ID = requireArguments().getInt("EXTRA_ID");

        View view = inflater.inflate(R.layout.activity_qr_scanner, container, false);
        CodeScannerView scannerView = view.findViewById(R.id.scannerView);
        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {

                //add to offline list of students in clicked attendance
                String resultText = result.getText();
                String decryptedData = EncryptorAndDecryptor.decrypt(resultText);
                String[] arrOfStr = decryptedData != null ? decryptedData.split("&") : resultText.split("&");

                StudentInAttendanceVM student = new StudentInAttendanceVM(activity.getApplication());
                try {
                    student.insert(new StudentInAttendanceModel(arrOfStr[0], arrOfStr[1], EXTRA_ID));
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast = Toast.makeText(activity, arrOfStr[0] + " is successfully added.", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                }catch (Exception e)
                {
                    e.printStackTrace();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast = Toast.makeText(activity, "QR cannot be read, try again", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });

                }
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toast != null) {
                    toast.cancel();
                }
                mCodeScanner.startPreview();
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(this.requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            mCodeScanner.startPreview();
        } else {
            this.requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            for (int p = 0; p < permissions.length; p++) {
                if (Manifest.permission.CAMERA.equals(permissions[p])) {
                    if (grantResults[p] == PackageManager.PERMISSION_GRANTED) {
                        mCodeScanner.startPreview();
                    }
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}