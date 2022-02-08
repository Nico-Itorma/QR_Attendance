package com.nicanoritorma.qrattendance.OnlineRepository;
/**
 * Created by Nicanor Itorma
 */
import static com.nicanoritorma.qrattendance.BaseActivity.getDbUrl;
import static com.nicanoritorma.qrattendance.BaseActivity.showProgressBar;

import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.nicanoritorma.qrattendance.api.GetAttendance;
import com.nicanoritorma.qrattendance.api.PutData;
import com.nicanoritorma.qrattendance.model.AttendanceModel;

import java.util.List;

public class AttendanceRepo {

    private Application application;
    private GetAttendance getAttendance;

    public AttendanceRepo(Application application) {
        this.application = application;
        getAttendance = new GetAttendance(application);
    }

    public LiveData<List<AttendanceModel>> getAttendanceList() {
        return getAttendance.getAttendanceList();
    }

    public void insert(AttendanceModel attendanceModel)
    {
        new InsertAttendanceToDb(application
                , attendanceModel.getAttendanceName()
                , attendanceModel.getDetails()
                , attendanceModel.getDate()
                , attendanceModel.getTime()).execute();
    }

    static class InsertAttendanceToDb extends AsyncTask<Void, Void, Void>
    {
        String url = getDbUrl() + "AddAttendance.php";
        String attendanceName, details, date, time;
        String result;
        Application application;

        public InsertAttendanceToDb(Application application, String attendanceName, String details, String date, String time) {
            this.attendanceName = attendanceName;
            this.details = details;
            this.date = date;
            this.time = time;
            this.application = application;
        }

        @Override
        protected void onPreExecute() {
            showProgressBar(true);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String[] field = new String[4];
            field[0] = "attendanceName";
            field[1] = "attendanceDetails";
            field[2] = "attendanceDate";
            field[3] = "attendanceTime";

            String[] data = new String[4];
            data[0] = attendanceName;
            data[1] = details;
            data[2] = date;
            data[3] = time;

            //TODO: change URL address
            PutData putData = new PutData(url, "POST", field, data);

            if (putData.startPut()) {
                if (putData.onComplete()) {
                    result = putData.getResult();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            showProgressBar(false);
            Toast.makeText(application, result, Toast.LENGTH_SHORT).show();
        }
    }
}
