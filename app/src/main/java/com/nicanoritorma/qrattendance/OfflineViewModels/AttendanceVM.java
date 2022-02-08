package com.nicanoritorma.qrattendance.OfflineViewModels;
/**
 * Created by Nicanor Itorma
 */
import android.app.Application;
import android.database.Cursor;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.nicanoritorma.qrattendance.OfflineRepository.AttendanceRepository;
import com.nicanoritorma.qrattendance.model.AttendanceModel;

import java.util.List;

public class AttendanceVM extends AndroidViewModel {

    private AttendanceRepository repository;
    private LiveData<List<AttendanceModel>> attendanceList;

    public AttendanceVM(@NonNull Application application) {
        super(application);
        repository = new AttendanceRepository(application);
        attendanceList = repository.getAttendanceList();
    }

    public void insert(AttendanceModel attendance) {
        repository.insert(attendance);
    }

    public void update(AttendanceModel attendance) {
        repository.update(attendance);
    }

    public void updateTime(int id, String date, String time) {
        repository.updateTime(id, date, time);
    }

    public LiveData<AttendanceModel> getAttendanceDT(int id) {
        return repository.getAttendanceDT(id);
    }

    public void delete(AttendanceModel attendance) {
        repository.delete(attendance);
    }

    public void deleteAllAttendance() {
        repository.deleteAllAttendance();
    }

    //get all attendance content to be displayed
    public LiveData<List<AttendanceModel>> getAllAttendance() {
        return attendanceList;
    }
}
