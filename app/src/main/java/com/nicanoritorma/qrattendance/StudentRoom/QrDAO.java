package com.nicanoritorma.qrattendance.StudentRoom;
/**
 * Created by Nicanor Itorma
 */
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.nicanoritorma.qrattendance.model.QrModel;

import java.util.List;

@Dao
public interface QrDAO
{
    @Insert
    void insert(QrModel student);

    @Update
    void update(QrModel student);

    @Delete
    void delete(QrModel student);

    @Query("DELETE FROM student_table")
    void deleteAllStudent();

    @Query("SELECT * FROM student_table ORDER BY id ASC")
    LiveData<List<QrModel>> getAllStudent();
}
