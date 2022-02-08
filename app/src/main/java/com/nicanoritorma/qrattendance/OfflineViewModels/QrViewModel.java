package com.nicanoritorma.qrattendance.OfflineViewModels;
/**
 * Created by Nicanor Itorma
 */
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nicanoritorma.qrattendance.model.QrModel;
import com.nicanoritorma.qrattendance.OfflineRepository.QrRepository;

import java.util.List;

public class QrViewModel extends AndroidViewModel {

    private QrRepository repository;
    private LiveData<List<QrModel>> allQr;

    public QrViewModel(@NonNull Application application) {
        super(application);
        repository = new QrRepository(application);
        allQr = repository.getAllStudent();
    }

    public void insert(QrModel qrItem)
    {
        repository.insert(qrItem);
    }

    public void update(QrModel qrItem)
    {
        repository.update(qrItem);
    }

    public void delete(QrModel qrItem)
    {
        repository.delete(qrItem);
    }

    public LiveData<List<QrModel>> getAllQr() {
        return allQr;
    }
}
