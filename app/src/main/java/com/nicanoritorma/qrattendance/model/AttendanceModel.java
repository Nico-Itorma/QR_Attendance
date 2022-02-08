package com.nicanoritorma.qrattendance.model;
/**
 * Created by Nicanor Itorma
 */
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "attendance_table")
public class AttendanceModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "attendanceName")
    private String attendanceName;

    @ColumnInfo(name = "details")
    private String details;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "time")
    private String time;

    public AttendanceModel() {
    }

    public AttendanceModel(int id, String attendanceName, String details, String date, String time) {
        this.id = id;
        this.attendanceName = attendanceName;
        this.details = details;
        this.date = date;
        this.time = time;
    }

    public AttendanceModel(String attendanceName, String details, String date, String time) {
        this.attendanceName = attendanceName;
        this.date = date;
        this.details = details;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAttendanceName() {
        return attendanceName;
    }

    public void setAttendanceName(String attendanceName) {
        this.attendanceName = attendanceName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
