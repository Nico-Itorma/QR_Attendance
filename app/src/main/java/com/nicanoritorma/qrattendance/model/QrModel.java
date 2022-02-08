package com.nicanoritorma.qrattendance.model;
/**
 * Created by Nicanor Itorma
 */
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "student_table")
public class QrModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String idNum;

    private String college;

    private String qrCode;

    public QrModel(String name, String idNum, String college, String qrCode) {
        this.name = name;
        this.college = college;
        this.idNum = idNum;
        this.qrCode = qrCode;
    }

    //public StudentModel() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
