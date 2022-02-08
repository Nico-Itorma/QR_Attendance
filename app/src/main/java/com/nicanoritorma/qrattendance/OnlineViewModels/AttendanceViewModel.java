package com.nicanoritorma.qrattendance.OnlineViewModels;
/**
 * Created by Nicanor Itorma
 */
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nicanoritorma.qrattendance.model.AttendanceModel;
import com.nicanoritorma.qrattendance.OnlineRepository.AttendanceRepo;

import java.util.List;

public class AttendanceViewModel extends AndroidViewModel {

    private AttendanceRepo attendanceRepo;

    public AttendanceViewModel(@NonNull Application application) {
        super(application);
        attendanceRepo = new AttendanceRepo(application);
    }

    public void insert(String attendanceName, String details, String date, String time)
    {
        attendanceRepo.insert(new AttendanceModel(attendanceName, details, date, time));
    }

    public LiveData<List<AttendanceModel>> getAttendanceList() {
        return attendanceRepo.getAttendanceList();
    }
}
