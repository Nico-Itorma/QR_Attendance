package com.nicanoritorma.qrattendance.AttendanceRoom;
/**
 * Created by Nicanor Itorma
 */
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.nicanoritorma.qrattendance.model.AttendanceModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Database(entities = {AttendanceModel.class}, version = 1)
public abstract class AttendanceDB extends RoomDatabase {

    private static AttendanceDB instance;

    public abstract AttendanceDAO attendanceDAO();

    public static synchronized AttendanceDB getInstance(Context context)
    {
        if (instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AttendanceDB.class, "attendance_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private AttendanceDAO attendanceDAO;

        private PopulateDbAsyncTask(AttendanceDB db)
        {
            attendanceDAO = db.attendanceDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
