package com.nicanoritorma.qrattendance.OfflineRepository;
/**
 * Created by Nicanor Itorma
 */
import android.app.Application;

import androidx.lifecycle.LiveData;

import com.nicanoritorma.qrattendance.StudentRoom.QrDAO;
import com.nicanoritorma.qrattendance.model.QrModel;
import com.nicanoritorma.qrattendance.StudentRoom.QrDB;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class QrRepository {

    private QrDAO studentDAO;
    private LiveData<List<QrModel>> allStudent;

    public QrRepository(Application application)
    {
        QrDB database = QrDB.getInstance(application);
        studentDAO = database.studentDAO();
        allStudent = studentDAO.getAllStudent();
    }

    public void insert(QrModel student)
    {
        Insert(studentDAO, student);
    }

    public void update(QrModel student)
    {
        Update(studentDAO, student);
    }

    public void delete(QrModel student)
    {
        Delete(studentDAO, student);
    }

    public void deleteAllStudent()
    {
        DeleteAll(studentDAO);
    }

    public LiveData<List<QrModel>> getAllStudent() {
        return allStudent;
    }

    private void Insert(QrDAO studentDAO, QrModel student) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                studentDAO.insert(student);
            }
        });
    }

    private void Update(QrDAO studentDAO, QrModel student) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                studentDAO.update(student);
            }
        });
    }

    private void Delete(QrDAO studentDAO, QrModel student) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                studentDAO.delete(student);
            }
        });
    }

    private void DeleteAll(QrDAO studentDAO) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                studentDAO.deleteAllStudent();
            }
        });
    }
}
