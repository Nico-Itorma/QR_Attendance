package com.nicanoritorma.qrattendance.OnlineRepository;
/**
 * Created by Nicanor Itorma
 */
import android.app.Application;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.nicanoritorma.qrattendance.BaseActivity;
import com.nicanoritorma.qrattendance.api.GetStudentOnline;
import com.nicanoritorma.qrattendance.api.PutData;
import com.nicanoritorma.qrattendance.model.QrModel;

import java.util.List;

public class StudentRepo extends BaseActivity {

    private Application application;
    private GetStudentOnline studentList;

    public StudentRepo(Application application) {
        this.application = application;
        studentList = new GetStudentOnline(application);
    }

    public void insert(QrModel student) {
        new InsertStudentToDb(application, student.getName(), student.getIdNum(), student.getCollege(), student.getQrCode()).execute();
    }

    static class InsertStudentToDb extends AsyncTask<Void, Void, Void> {
        String url = getDbUrl() + "AddQrToDb.php";
        String fullname, idNumber, dept, qrCode;
        String result;
        Application application;

        public InsertStudentToDb(Application application, String fullname, String idNumber, String dept, String qrCode) {
            this.application = application;
            this.fullname = fullname;
            this.idNumber = idNumber;
            this.dept = dept;
            this.qrCode = qrCode;
        }

        @Override
        protected void onPreExecute() {
            showProgressBar(true);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String[] field = new String[4];
            field[0] = "fullname";
            field[1] = "idNumber";
            field[2] = "department";
            field[3] = "qrCode";

            String[] data = new String[4];
            data[0] = fullname;
            data[1] = idNumber;
            data[2] = dept;
            data[3] = qrCode;

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

    public LiveData<List<QrModel>> getStudentList() {
        return studentList.getStudentList();
    }
}
