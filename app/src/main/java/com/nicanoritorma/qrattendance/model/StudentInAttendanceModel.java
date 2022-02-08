package com.nicanoritorma.qrattendance.model;
/**
 * Created by Nicanor Itorma
 */
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "studentAdded_table")
public class StudentInAttendanceModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String fullname;
    private String idNum;
    private int parentId;

    public StudentInAttendanceModel() {}

    public StudentInAttendanceModel(String fullname, String idNum, int parentId) {
        this.fullname = fullname;
        this.idNum = idNum;
        this.parentId = parentId;
    }

    public StudentInAttendanceModel(String fullname, String idNum) {
        this.fullname = fullname;
        this.idNum = idNum;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
