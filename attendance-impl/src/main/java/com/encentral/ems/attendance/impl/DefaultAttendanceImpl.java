package com.encentral.ems.attendance.impl;


import com.encentral.ems.attendance.api.IAttendance;
import com.encentral.ems.attendance.model.Attendance;
import com.encentral.ems.attendance.model.AttendanceMapper;
import com.encentral.entities.JpaAttendance;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.Query;
import java.util.*;

public class DefaultAttendanceImpl implements IAttendance {


    private final JPAApi jpaApi;

    @Inject
    public DefaultAttendanceImpl(JPAApi jpaApi) {
        this.jpaApi = jpaApi;
    }



    @Override
    public String checkTime(){
        /* 09:00 AM */
        final int OPEN_HOUR = 9;
        final int OPEN_MINUTE = 0;
        final int OPEN_SECOND = 0;

        /* 05:00 PM */
        final int CLOSED_HOUR = 17;
        final int CLOSED_MINUTE = 0;
        final int CLOSED_SECOND = 0;

        Calendar openHour = Calendar.getInstance();
        openHour.set(Calendar.HOUR_OF_DAY, OPEN_HOUR);
        openHour.set(Calendar.MINUTE, OPEN_MINUTE);
        openHour.set(Calendar.SECOND, OPEN_SECOND);

        Calendar closedHour = Calendar.getInstance();
        closedHour.set(Calendar.HOUR_OF_DAY, CLOSED_HOUR);
        closedHour.set(Calendar.MINUTE, CLOSED_MINUTE);
        closedHour.set(Calendar.SECOND, CLOSED_SECOND);

        Calendar now = Calendar.getInstance();

        int day = now.get(Calendar.DAY_OF_WEEK);
        //Check if day is on a weekend
        if(day==1||day==7){
            return "Today is not a work day!";
        }
        //Before 09:00 pm
        if (!now.after(openHour)){
            return "It is currently too early to sign in";
        }
        if (!now.before(closedHour)){

            return "It is currently too late to sign out";
        }
        return "";

    }

    @Override
    public boolean checkIn(String userToken) {


        jpaApi.withTransaction(em->{
            var date = new java.util.Date().getTime();
            //Check if already signed in
            Query query = jpaApi.em().
                    createNamedQuery("JpaAttendance.checkAlreadySignedIn",JpaAttendance.class);
            query.setParameter("currentDate",date);
            query.setParameter("currentUserToken",userToken);
            JpaAttendance j = (JpaAttendance) query.getSingleResult();
            if (j !=null){
                return false;
            }
            JpaAttendance jpaAttendance = new JpaAttendance();
            jpaAttendance.setAttendanceId(UUID.randomUUID().toString());
            jpaAttendance.setDate(new java.sql.Date(date));
            //Mark Sign in Time
            long now = System.currentTimeMillis();
            jpaAttendance.setSignInTime(new java.sql.Time(now));
            jpaApi.em().persist(jpaAttendance);
            return true;
        });
        return false;
    }

    @Override
    public boolean checkOut(String userToken) {


        Long date = new java.util.Date().getTime();
        jpaApi.withTransaction(em->{
            //Check if already signed in
            Query query = jpaApi.em().
                    createNamedQuery("JpaAttendance.checkAlreadySignedIn",JpaAttendance.class);
            query.setParameter("currentDate",date);
            query.setParameter("currentUserToken",userToken);
            JpaAttendance jpaAttendance = (JpaAttendance) query.getSingleResult();
            if (jpaAttendance==null){
                return false;
            }
            //Mark Sign Out Time
            long now =System.currentTimeMillis();
            jpaAttendance.setSignOutTime(new java.sql.Time(now));
            jpaApi.em().merge(jpaAttendance);
            return true;
        });
        return false;
    }

    @Override
    public List<Attendance> getDailyAttendance(java.util.Date date) {
        Query query = jpaApi.em()
                .createNamedQuery("JpaAttendance.getDaily",JpaAttendance.class);
        query.setParameter("currentDate",date);
        List<JpaAttendance> jpaAttendanceList = query.getResultList();
        List<Attendance> attendanceList = new ArrayList<>();
        for(JpaAttendance jpaAttendance :jpaAttendanceList){
            attendanceList.add(AttendanceMapper.jpaAttendanceToAttendance(jpaAttendance));
        }
        return attendanceList;
    }

    @Override
    public List<Attendance> getUserAttendance(String userId) {
        Query query = jpaApi.em()
                .createNamedQuery("JpaAttendance.getUserMonthly",JpaAttendance.class);
        query.setParameter("userId",userId);
        List<JpaAttendance> jpaAttendanceList = query.getResultList();
        List<Attendance> attendanceList = new ArrayList<>();
        for(JpaAttendance jpaAttendance :jpaAttendanceList){
            attendanceList.add(AttendanceMapper.jpaAttendanceToAttendance(jpaAttendance));
        }
        return attendanceList;
    }

}
