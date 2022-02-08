package com.nicanoritorma.qrattendance.StudentRoom;
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

import com.nicanoritorma.qrattendance.model.QrModel;

@Database(entities = {QrModel.class}, version = 1)
public abstract class QrDB extends RoomDatabase {

    private static QrDB instance;

    public abstract QrDAO studentDAO();

    public static synchronized QrDB getInstance(Context context)
    {
        if (instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    QrDB.class, "student_db")
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
        private QrDAO studentDAO;

        private PopulateDbAsyncTask(QrDB db)
        {
            studentDAO = db.studentDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}

