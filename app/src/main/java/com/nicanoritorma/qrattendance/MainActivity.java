package com.nicanoritorma.qrattendance;
/**
 * Created by Nicanor Itorma
 */
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import android.animation.LayoutTransition;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends BaseActivity {

    private GridLayout drop_down;
    private final int REQUEST_PERMISSION_STORAGE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drop_down = findViewById(R.id.drop_down_gridView);
        drop_down.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_STORAGE) {
            for (int p = 0; p < permissions.length; p++) {
                if (WRITE_EXTERNAL_STORAGE.equals(permissions[p])) {
                    if (grantResults[p] == PackageManager.PERMISSION_GRANTED) {
                        Log.d("onRequestPermissionsResult: ", "Permission Granted");
                    }
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void expandDropDown(View view) {
        int drop = (drop_down.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;
        TransitionManager.beginDelayedTransition(drop_down, new AutoTransition());
        drop_down.setVisibility(drop);
    }

    public void btn_createQr(View view) {
        startActivity(new Intent(this, CreateQr.class));
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void btn_attendanceList(View view) {
        startActivity(new Intent(this, AttendanceList.class));
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void btn_generatedQr(View view) {
        startActivity(new Intent(this, GeneratedQr.class));
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void newAttendance(View view) {
        startActivity(new Intent(this, NewAttendance.class));
        this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}