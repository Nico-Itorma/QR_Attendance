package com.nicanoritorma.qrattendance.OfflineViewModels;
/**
 * Created by Nicanor Itorma
 */
import android.app.Application;
import android.database.Cursor;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.nicanoritorma.qrattendance.OfflineRepository.StudentInAttendanceRepository;
import com.nicanoritorma.qrattendance.model.AttendanceModel;
import com.nicanoritorma.qrattendance.model.StudentInAttendanceModel;

import java.util.ArrayList;
import java.util.List;

public class StudentInAttendanceVM extends AndroidViewModel {

    private StudentInAttendanceRepository repository;

    public StudentInAttendanceVM(@NonNull Application application) {
        super(application);

        repository = new StudentInAttendanceRepository(application);
        //studentList = repository.getStudentInAttendanceList();
    }

    public void insert(StudentInAttendanceModel student) {
        repository.insert(student);
    }

    public void update(StudentInAttendanceModel student) {
        repository.update(student);
    }

    public void delete(StudentInAttendanceModel student) {
        repository.delete(student);
    }

    public void deleteAllStudentInAttendance() {
        repository.deleteAllStudentInAttendance();
    }

    public LiveData<List<StudentInAttendanceModel>> getStudentList(int parentId)
    {
        return repository.getStudentsInAttendance(parentId);
    }
}
