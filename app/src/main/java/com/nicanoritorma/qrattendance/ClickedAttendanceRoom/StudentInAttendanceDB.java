package com.nicanoritorma.qrattendance.ClickedAttendanceRoom;
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

import com.nicanoritorma.qrattendance.model.StudentInAttendanceModel;

@Database(entities = {StudentInAttendanceModel.class}, version = 1)
public abstract class StudentInAttendanceDB extends RoomDatabase {

    private static StudentInAttendanceDB instance;

    public abstract StudentInAttendanceDAO studentInAttendanceDAO();

    public static synchronized StudentInAttendanceDB getInstance(Context context)
    {
        if (instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    StudentInAttendanceDB.class, "studentInAttendance_db")
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
        private StudentInAttendanceDAO studentInAttendanceDAO;

        private PopulateDbAsyncTask(StudentInAttendanceDB db)
        {
            studentInAttendanceDAO = db.studentInAttendanceDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
