package com.nicanoritorma.qrattendance;
/**
 * Created by Nicanor Itorma
 */
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.File;

public abstract class BaseActivity extends AppCompatActivity {

    private static CardView progressBar;
    private static File dir;
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
        return "http://192.168.8.101/qr_atten_sys/";
    }

    public static void showProgressBar(boolean visibility)
    {
        progressBar.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    public void showToast(final String toast)
    {
        Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
    }

//    public void testConnect()
//    {
//        new testConnect(getDbUrl() + "TestConnect.php").execute();
//    }
//
//    class testConnect extends AsyncTask<Void, Void, Void> {
//
//        String urlString;
//        String result;
//        HttpURLConnection urlConnection = null;
//        public testConnect(String url) {
//            this.urlString = url;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            showProgressBar(true);
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            try {
//                URL url = new URL(urlString);
//                urlConnection = (HttpURLConnection) url.openConnection();
//                result = urlConnection.getResponseMessage();
//            } catch (ConnectException e)
//            {
//                result = "Failed to connect error (E01)";
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void unused) {
//            urlConnection.disconnect();
//            if (!result.equals("OK"))
//            {
//                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
//            }
//            showProgressBar(false);
//        }
//    }
}
