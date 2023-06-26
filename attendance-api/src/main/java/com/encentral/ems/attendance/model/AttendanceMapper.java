package com.encentral.ems.attendance.model;


import com.encentral.entities.JpaAttendance;

public class AttendanceMapper {

    public static JpaAttendance attendanceToJPAAttendance(Attendance attendance){
        if(attendance == null){
            return null;
        }
        JpaAttendance jpaAttendance = new JpaAttendance();
        jpaAttendance.setAttendanceId(attendance.getAttendanceId());
        jpaAttendance.setDate(attendance.getDate());
        jpaAttendance.setSignInTime(attendance.getSignInTime());
        jpaAttendance.setSignOutTime(attendance.getSignOutTime());

        return jpaAttendance;
    }

    public static Attendance jpaAttendanceToAttendance(JpaAttendance jpaAttendance){
        if( jpaAttendance ==null){
            return null;
        }
        Attendance attendance = new Attendance();
        attendance.setAttendanceId(jpaAttendance.getAttendanceId());
        attendance.setDate(jpaAttendance.getDate());
        attendance.setSignInTime(jpaAttendance.getSignInTime());
        attendance.setSignOutTime(jpaAttendance.getSignOutTime());

        return attendance;
    }
}
