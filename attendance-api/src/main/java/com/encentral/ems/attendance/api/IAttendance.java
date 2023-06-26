package com.encentral.ems.attendance.api;


import java.util.Date;
import java.util.List;
import com.encentral.ems.attendance.model.Attendance;


public interface IAttendance {


    String checkTime();

    boolean checkIn(String userToken);

    boolean checkOut(String userToken);

    List<Attendance> getDailyAttendance(Date date);

    List<Attendance> getUserAttendance(String userId);

}
