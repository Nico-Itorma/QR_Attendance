package com.nicanoritorma.qrattendance;
/**
 * Created by Nicanor Itorma
 */
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;

import com.nicanoritorma.qrattendance.OfflineViewModels.AttendanceVM;
import com.nicanoritorma.qrattendance.model.AttendanceModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewAttendance extends BaseActivity {

    private EditText et_attendance, et_details;
    private Button btn_createAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_attendance);

        et_attendance = findViewById(R.id.et_attendance);
        et_details = findViewById(R.id.et_details);
        btn_createAttendance = findViewById(R.id.btn_createAttendance);

        initUI();

    }

    private void initUI()
    {
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle("Create Attendance");
        }

        btn_createAttendance.setOnClickListener(view -> AddAttendanceToDb(getData()[0], getData()[1], getData()[2], getData()[3]));
    }

    private String[] getData()
    {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Date mDate = new Date();

        String attendanceName = et_attendance.getText().toString().trim();
        String details = et_details.getText().toString().trim();
        String date = dateFormatter.format(mDate);
        String time = timeFormatter.format(mDate);

        return new String[]{attendanceName, details, date, time};
    }

    private void AddAttendanceToDb(String attendanceName, String details, String date, String time)
    {
        //offline db
        if (attendanceName.length() == 0)
        {
            et_attendance.setError("Required field");
        }
        else
        {
            AttendanceVM attendanceVM = new AttendanceVM(getApplication());
            attendanceVM.insert(new AttendanceModel(attendanceName, details, date, time));
            et_attendance.setText("");
            et_attendance.requestFocus();
            et_details.setText("");
            onBackPressed();
        }


//        //online db
//        AttendanceViewModel attendanceViewModel = new ViewModelProvider(this).get(AttendanceViewModel.class);
//        attendanceViewModel.insert(attendanceName, details, date, time);
//
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