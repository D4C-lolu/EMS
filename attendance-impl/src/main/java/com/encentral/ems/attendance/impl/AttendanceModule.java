package com.encentral.ems.attendance.impl;

import com.encentral.ems.attendance.api.IAttendance;
import com.google.inject.AbstractModule;

public class AttendanceModule extends AbstractModule {

    @Override
    protected void configure(){
        bind(IAttendance.class).to(DefaultAttendanceImpl.class);
    }
}
