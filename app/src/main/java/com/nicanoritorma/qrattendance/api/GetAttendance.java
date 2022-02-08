package com.nicanoritorma.qrattendance.api;
/**
 * Created by Nicanor Itorma
 */
import static com.nicanoritorma.qrattendance.BaseActivity.getDbUrl;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nicanoritorma.qrattendance.model.AttendanceModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetAttendance {

    private MutableLiveData<List<AttendanceModel>> attendanceList;
    private RequestQueueSingleton requestQueueSingleton;

    public GetAttendance(Application application)
    {
        attendanceList = new MutableLiveData<>();
        requestQueueSingleton = RequestQueueSingleton.getInstance(application);
    }

    public LiveData<List<AttendanceModel>> getAttendanceList() {
        getAttendance();
        return attendanceList;
    }

    private void getAttendance()
    {
        List<AttendanceModel> list = new ArrayList<>();
        String URL = getDbUrl() + "GetAttendanceList.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("attendanceList");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject data = array.getJSONObject(i);

                        String attendanceName = data.getString("attendanceName");
                        String details = data.getString("attendanceDetails");
                        String date = data.getString("attendanceDate");
                        String time = data.getString("attendanceTime");
                        list.add(new AttendanceModel(attendanceName, details, date, time));
                    }
                    attendanceList.postValue(list);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueueSingleton.getRequestQueue().add(request);
    }
}
