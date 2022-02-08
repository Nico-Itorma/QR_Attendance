package com.nicanoritorma.qrattendance;
/**
 * Created by Nicanor Itorma
 */
import androidx.appcompat.app.ActionBar;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.nicanoritorma.qrattendance.model.QrModel;
import com.nicanoritorma.qrattendance.OfflineViewModels.QrViewModel;
import com.nicanoritorma.qrattendance.utils.EncryptorAndDecryptor;

import java.io.ByteArrayOutputStream;
import java.util.Base64;

public class CreateQr extends BaseActivity {

    private MaterialButton btn_generate, btn_saveQr;
    private EditText et_fullname, et_idNum, et_dept;
    private ImageView iv_qr;
    private final String BLANK_FIELD_ERROR = "Required Field";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createqr);

        btn_generate = findViewById(R.id.generate);
        btn_saveQr = findViewById(R.id.btn_saveQr);
        et_fullname = findViewById(R.id.et_name);
        et_idNum = findViewById(R.id.et_idNum);
        et_dept = findViewById(R.id.et_dept);
        iv_qr = findViewById(R.id.iv_qr);
        initUI();
    }

    private void initUI() {
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Create QR Code");
        btn_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getData()[0].isEmpty())
                {
                    et_fullname.setError(BLANK_FIELD_ERROR);
                }
                else if (getData()[1].isEmpty())
                {
                    et_idNum.setError(BLANK_FIELD_ERROR);
                }
                else
                {
                    generateQr(CreateQr.this.getData()[0], CreateQr.this.getData()[1]);
                }
            }
        });
        btn_saveQr.setOnClickListener(view -> {
            iv_qr.setDrawingCacheEnabled(true);
            Bitmap bitmap = iv_qr.getDrawingCache();
            saveQr(getData()[0], getData()[1], getData()[2], bitmap);
        });

    }

    //get data from edit texts
    private String[] getData() {
        String fullname = et_fullname.getText().toString().trim();
        String idNum = et_idNum.getText().toString().trim();
        String department = et_dept.getText().toString().trim();

        return new String[]{fullname, idNum, department};
    }


    //generate qr code
    private void generateQr(String fullname, String idNum) {
        BitMatrix bitMatrix;
        Bitmap bitmap;
        String encryptedData = EncryptorAndDecryptor.encrypt(fullname+"&"+idNum);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        try {
            bitMatrix = qrCodeWriter.encode(encryptedData != null ? encryptedData : fullname+"&"+idNum, BarcodeFormat.QR_CODE, 250, 250);
            bitmap = Bitmap.createBitmap(250, 250, Bitmap.Config.RGB_565);

            for (int x = 0; x < 250; x++) {
                for (int y = 0; y < 250; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            iv_qr.setVisibility(View.VISIBLE);
            iv_qr.setImageBitmap(bitmap); //set the generated qr to the image view

            //set visibility of save btn
            btn_saveQr.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error: Try again", Toast.LENGTH_SHORT).show();
        }
    }

    //save qr to local db if no internet or to online db if internet is available
    private void saveQr(String fullname, String idNum, String dept, Bitmap bitmap) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 99, bos);
        byte[] qr = bos.toByteArray();
        String qrCode = Base64.getEncoder().encodeToString(qr);
        String encryptedData = EncryptorAndDecryptor.encrypt(qrCode);

        //offline db
        QrViewModel qrViewModel = new QrViewModel(getApplication());
        qrViewModel.insert(new QrModel(fullname, idNum, dept, encryptedData));

        et_fullname.setText("");
        et_idNum.setText("");
        et_dept.setText("");
        iv_qr.setVisibility(View.GONE);
        btn_saveQr.setVisibility(View.GONE);
        et_fullname.requestFocus();

//        if (isConnectedFast) //save to online db
//        {
//            GeneratedQrViewModel onlineViewModel = new GeneratedQrViewModel(getApplication());
//            onlineViewModel.insert(fullname, idNum, dept, qrCode);
//
//            //if success clear the field
//            et_fullname.setText("");
//            et_idNum.setText("");
//            et_dept.setText("");
//            iv_qr.setVisibility(View.GONE);
//            btn_saveQr.setVisibility(View.GONE);
//            et_fullname.requestFocus();
//        } else {
//            Toast.makeText(getApplicationContext(), SAVE_ERROR, Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}