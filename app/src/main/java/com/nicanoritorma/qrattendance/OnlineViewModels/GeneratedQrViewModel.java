package com.nicanoritorma.qrattendance.OnlineViewModels;
/**
 * Created by Nicanor Itorma
 */
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nicanoritorma.qrattendance.model.QrModel;
import com.nicanoritorma.qrattendance.OnlineRepository.StudentRepo;

import java.util.List;

public class GeneratedQrViewModel extends AndroidViewModel {

    private StudentRepo onlineStudentRepo;

    public GeneratedQrViewModel(Application application) {
        super(application);
        onlineStudentRepo = new StudentRepo(application);
    }

    public void insert(String fullname, String idNum, String dept, String qrCode) {
        onlineStudentRepo.insert(new QrModel(fullname, idNum, dept, qrCode));
    }

    public LiveData<List<QrModel>> getStudentList() {
        return onlineStudentRepo.getStudentList();
    }
}
