package com.nicanoritorma.qrattendance.OfflineRepository;
/**
 * Created by Nicanor Itorma
 */
import android.app.Application;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.nicanoritorma.qrattendance.ClickedAttendanceRoom.StudentInAttendanceDAO;
import com.nicanoritorma.qrattendance.ClickedAttendanceRoom.StudentInAttendanceDB;
import com.nicanoritorma.qrattendance.model.StudentInAttendanceModel;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class StudentInAttendanceRepository {

    private StudentInAttendanceDAO studentInAttendanceDAO;

    public StudentInAttendanceRepository(Application application) {

        StudentInAttendanceDB database = StudentInAttendanceDB.getInstance(application);
        studentInAttendanceDAO = database.studentInAttendanceDAO();
    }

    public void insert(StudentInAttendanceModel student)
    {
        Insert(studentInAttendanceDAO, student);
    }

    public void update(StudentInAttendanceModel student)
    {
        Update(studentInAttendanceDAO, student);
    }

    public void delete(StudentInAttendanceModel student)
    {
        Delete(studentInAttendanceDAO, student);
    }

    public void deleteAllStudentInAttendance()
    {
        DeleteAll(studentInAttendanceDAO);
    }

    public LiveData<List<StudentInAttendanceModel>> getStudentsInAttendance(int id)
    {
        return studentInAttendanceDAO.getStudents(id);
    }

    private void Insert(StudentInAttendanceDAO studentDAO, StudentInAttendanceModel student) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                studentDAO.insert(student);
            }
        });
    }

    private void Update(StudentInAttendanceDAO studentDAO, StudentInAttendanceModel student) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                studentDAO.update(student);
            }
        });
    }

    private void Delete(StudentInAttendanceDAO studentDAO, StudentInAttendanceModel student) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                studentDAO.delete(student);
            }
        });
    }

    private void DeleteAll(StudentInAttendanceDAO studentDAO) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                studentDAO.deleteAllStudent();
            }
        });
    }
}
