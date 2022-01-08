package com.nicanoritorma.qrattendance;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class BaseActivity extends AppCompatActivity {

    private CardView progressBar;
    @Override
    public void setContentView(int layoutResID) {
        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        progressBar = constraintLayout.findViewById(R.id.progressBar);
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.frame_layout);
        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(constraintLayout);
    }

    public static String getDbUrl()
    {
        //TODO: change database address on production
        return "http://192.168.8.100/qr_atten_sys/";
    }

    public void testConnect()
    {
        new testConnect(getDbUrl() + "TestConnect.php").execute();
    }

    class testConnect extends AsyncTask<Void, Void, Integer> {

        String urlString = "";
        String result;
        HttpURLConnection urlConnection = null;
        public testConnect(String url) {
            this.urlString = url;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Void... voids) {
            try {
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                result = urlConnection.getResponseMessage();
            } catch (ConnectException e)
            {
                result = "Failed to connect error (E01)";
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            urlConnection.disconnect();
            if (!result.equals("OK"))
            {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
        }
    }
}