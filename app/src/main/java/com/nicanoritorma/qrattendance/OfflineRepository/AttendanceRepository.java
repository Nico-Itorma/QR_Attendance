package com.nicanoritorma.qrattendance.OfflineRepository;
/**
 * Created by Nicanor Itorma
 */
import android.app.Application;
import android.database.Cursor;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import com.nicanoritorma.qrattendance.AttendanceRoom.AttendanceDAO;
import com.nicanoritorma.qrattendance.AttendanceRoom.AttendanceDB;
import com.nicanoritorma.qrattendance.ClickedAttendanceRoom.StudentInAttendanceDAO;
import com.nicanoritorma.qrattendance.model.AttendanceModel;
import com.nicanoritorma.qrattendance.model.StudentInAttendanceModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AttendanceRepository {

    private AttendanceDAO attendanceDAO;
    private LiveData<List<AttendanceModel>> attendanceList;

    public AttendanceRepository(Application application)
    {
        AttendanceDB database = AttendanceDB.getInstance(application);
        attendanceDAO = database.attendanceDAO();
        attendanceList = attendanceDAO.getAllAttendance();
    }

    public void insert(AttendanceModel attendance)
    {
        Insert(attendanceDAO, attendance);
    }

    public void update(AttendanceModel attendance)
    {
        Update(attendanceDAO, attendance);
    }

    public void updateTime(int id, String date, String time)
    {
        new UpdateTimeAsyncTask(attendanceDAO, id, date, time).execute();
    }

    public LiveData<AttendanceModel> getAttendanceDT(int id)
    {
        return attendanceDAO.getAttendanceDT(id);
    }

    public void delete(AttendanceModel attendance)
    {
        Delete(attendanceDAO, attendance);
    }

    public void deleteAllAttendance()
    {
        DeleteAll(attendanceDAO);
    }

    public LiveData<List<AttendanceModel>> getAttendanceList() {
        return attendanceList;
    }

    //Insert new Attendance
    private void Insert(AttendanceDAO attendanceDAO, AttendanceModel attendance) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                attendanceDAO.insert(attendance);
            }
        });
    }

    private static class UpdateTimeAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private AttendanceDAO attendanceDAO;
        private int itemId;
        private String date, time;

        private UpdateTimeAsyncTask(AttendanceDAO attendanceDAO, int itemId, String date, String time)
        {
            this.attendanceDAO = attendanceDAO;
            this.itemId = itemId;
            this.date = date;
            this.time = time;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            attendanceDAO.updateTime(itemId, date, time);
            return null;
        }
    }

    private void Update(AttendanceDAO attendanceDAO, AttendanceModel attendance) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                attendanceDAO.update(attendance);
            }
        });
    }

    private void Delete(AttendanceDAO attendanceDAO, AttendanceModel attendance) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                attendanceDAO.delete(attendance);
            }
        });
    }

    private void DeleteAll(AttendanceDAO attendanceDAO) {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                attendanceDAO.deleteAllAttendance();
            }
        });
    }
}
