package com.encentral.ems.attendance.model;


import java.sql.Date;
import java.sql.Time;

public class Attendance {

    private String attendanceId;

    private Date date;

    private Time signInTime;

    private Time signOutTime;


    public Attendance(){

    }

    public String getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(String attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(Time signInTime) {
        this.signInTime = signInTime;
    }

    public Time getSignOutTime() {
        return signOutTime;
    }

    public void setSignOutTime(Time signOutTime) {
        this.signOutTime = signOutTime;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "attendanceId="+ attendanceId +
                ", date=" + date +
                ", signInTime=" + signInTime +
                ", signOutTime=" + signOutTime +
                '}';
    }
}
