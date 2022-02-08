package com.nicanoritorma.qrattendance;
/**
 * Created by Nicanor Itorma
 */
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nicanoritorma.qrattendance.OfflineViewModels.AttendanceVM;
import com.nicanoritorma.qrattendance.OfflineViewModels.StudentInAttendanceVM;
import com.nicanoritorma.qrattendance.model.AttendanceModel;
import com.nicanoritorma.qrattendance.model.StudentInAttendanceModel;
import com.nicanoritorma.qrattendance.ui.adapter.StudentInAttendanceAdapter;
import com.nicanoritorma.qrattendance.utils.QrScanner;
import com.nicanoritorma.qrattendance.utils.RecyclerViewItemClickSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ClickedAttendance extends BaseActivity {

    public static final String EXTRA_ATTENDANCE_NAME = "com.nicanoritorma.qrattendance.EXTRA_ATTENDANCE_NAME";
    public static final String EXTRA_DETAILS = "com.nicanoritorma.qrattendance.EXTRA_DETAILS";
    public static final String EXTRA_DATE = "com.nicanoritorma.qrattendance.EXTRA_DATE";
    public static final String EXTRA_TIME = "com.nicanoritorma.qrattendance.EXTRA_TIME";
    private RecyclerView rv_studentsAdded;
    private FloatingActionButton fab_add;
    private StudentInAttendanceAdapter studentInAttendanceAdapter;
    private FragmentManager mFragmentManager;
    private Intent intent;
    private static String newDate;
    private static int itemId;
    private static Application application;
    private static ActionBar ab;
    private final List<StudentInAttendanceModel> selectedStudent = new ArrayList<>();
    private ActionMode actionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_attendance);

        ClickedAttendance.application = getApplication();

        rv_studentsAdded = findViewById(R.id.rv_studentsInAttendance);
        fab_add = findViewById(R.id.fab_addStudent);

        intent = getIntent();
        itemId = intent.getIntExtra("ITEM_ID", 0);
        mFragmentManager = getSupportFragmentManager();
        initUI();

        fab_add.setOnClickListener(view -> {
            fab_add.setVisibility(View.GONE);

            Bundle bundle = new Bundle();
            bundle.putInt("EXTRA_ID", itemId);
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            ft.addToBackStack("Scanner");
            ft.add(R.id.scannerFragment, QrScanner.class, bundle);
            ft.commit();
        });
    }

    private void initUI() {
        fab_add.setVisibility(View.VISIBLE);
        ab = getSupportActionBar();

        if (ab != null) {
            if (intent.hasExtra(EXTRA_ATTENDANCE_NAME)) {
                ab.setTitle(intent.getStringExtra(EXTRA_ATTENDANCE_NAME));
                ab.setSubtitle(intent.getStringExtra(EXTRA_DATE) + " " + intent.getStringExtra(EXTRA_TIME));
            }
        }

        rv_studentsAdded.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rv_studentsAdded.setHasFixedSize(true);
        studentInAttendanceAdapter = new StudentInAttendanceAdapter();
        rv_studentsAdded.setAdapter(studentInAttendanceAdapter);

        StudentInAttendanceVM studentVM = new StudentInAttendanceVM(getApplication());
        studentVM.getStudentList(intent.getIntExtra("ITEM_ID", 0)).observe(this, new Observer<List<StudentInAttendanceModel>>() {
            @Override
            public void onChanged(List<StudentInAttendanceModel> studentInAttendanceModels) {
                studentInAttendanceAdapter.setList(studentInAttendanceModels);

                RecyclerViewItemClickSupport.addTo(rv_studentsAdded)
                        .setOnItemClickListener((recyclerView, position, view) -> {
                            if (actionMode == null) {
                                return;
                            }
                            // If in CAB mode
                            toggleListViewItem(view, position);
                            setCABTitle();
                        }).setOnItemLongClickListener((recyclerView, position, view) -> {
                            if (actionMode != null) {
                                return false;
                            }
                    // Start the CAB using the ActionMode.Callback defined below
                    ClickedAttendance.this.startActionMode(new ModeCallback());
                    toggleListViewItem(view, position);
                    setCABTitle();
                    return true;
                });

            }
        });
    }

    private List<StudentInAttendanceModel> getSelectedStudent()
    {
        return selectedStudent;
    }

    public void toggleListViewItem(View view, int position)
    {
        StudentInAttendanceModel student = studentInAttendanceAdapter.getItem(position);
        RelativeLayout item = view.findViewById(R.id.item_relative_layout);

        if (!getSelectedStudent().contains(student)) {
            getSelectedStudent().add(student);
            studentInAttendanceAdapter.addSelectedItem(position);
            item.setBackgroundResource(R.drawable.selected_item_border);
        } else {
            getSelectedStudent().remove(student);
            studentInAttendanceAdapter.removeSelectedItem(position);
            studentInAttendanceAdapter.restoreDrawable(item);
        }
        prepareActionModeMenu();

        if (selectedStudent.isEmpty()) {
            finishActionMode();
        }

        setCABTitle();
    }

    private void prepareActionModeMenu()
    {
        Menu menu = actionMode.getMenu();

        if (studentInAttendanceAdapter.getItemCount() > 1)
        {
            menu.findItem(R.id.menuActionSelectAll).setVisible(true);
        }
        menu.findItem(R.id.menuActionDelete).setVisible(true);
    }

    private void setCABTitle() {
        if (actionMode != null) {
            int title = selectedStudent.size();
            actionMode.setTitle(String.valueOf(title));
        }
    }

    private final class ModeCallback implements ActionMode.Callback {


        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            actionMode = mode;
            inflater.inflate(R.menu.app_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            prepareActionModeMenu();
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

            switch (item.getItemId()) {
                case R.id.menuActionSelectAll:
                    selectAllStudents();
                    return true;
                case R.id.menuActionDelete:
                    for (int i = 0; i < selectedStudent.size(); i++) {
                        StudentInAttendanceModel student1 = selectedStudent.get(i);
                        deleteItem(student1);
                    }
                    selectedStudent.clear();
                    finishActionMode();
                    return true;
            }
            mode.finish();
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            selectedStudent.clear();
            studentInAttendanceAdapter.clearSelectedItems();
            studentInAttendanceAdapter.notifyDataSetChanged();
            actionMode = null;
        }
    }

    public void finishActionMode() {
        if (actionMode != null) {
            actionMode.finish();
        }
    }

    private void selectAllStudents() {
        for (int i = 0; i < rv_studentsAdded.getChildCount(); i++) {
            RelativeLayout item = rv_studentsAdded.getChildAt(i).findViewById(R.id.item_relative_layout);
            item.setBackgroundResource(R.drawable.selected_item_border);
        }
        selectedStudent.clear();
        for (int i = 0; i < studentInAttendanceAdapter.getItemCount(); i++) {
            selectedStudent.add(studentInAttendanceAdapter.getItem(i));
            studentInAttendanceAdapter.addSelectedItem(i);
        }
        prepareActionModeMenu();
        setCABTitle();
    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem changeDT = menu.findItem(R.id.menu_ChangeDT);
        changeDT.setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ChangeDT:
                DialogFragment dateFragment = new DatePickerFragment();
                dateFragment.show(getSupportFragmentManager(), "datePicker");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deleteItem(StudentInAttendanceModel student) {
        StudentInAttendanceVM studentInAttendanceVM = new StudentInAttendanceVM(application);
        studentInAttendanceVM.delete(student);
    }

    //Date picker
    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            newDate = String.valueOf(day) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(year);

            //start time picker
            DialogFragment timeFragment = new TimePickerFragment();
            timeFragment.show(requireActivity().getSupportFragmentManager(), "timePicker");
        }
    }

    //time picker
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            String newTime;
            if (minute >= 10) {
                newTime = hour + ":" + minute;
            } else {
                newTime = hour + ":0" + minute;
            }

            //Update the date and time in repository via attendance view model
            AttendanceVM attendanceVM = new AttendanceVM(application);
            attendanceVM.updateTime(itemId, newDate, newTime);
            attendanceVM.getAttendanceDT(itemId).observe(this, new Observer<AttendanceModel>() {
                @Override
                public void onChanged(AttendanceModel attendanceModel) {
                    ab.setSubtitle(attendanceModel.getDate() + " " + attendanceModel.getTime());
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (mFragmentManager.getBackStackEntryCount() > 0) {
            mFragmentManager.popBackStackImmediate();
            initUI();
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finishActionMode();
        onBackPressed();
        return true;
    }
}