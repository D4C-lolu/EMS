package controllers;


import com.encentral.ems.attendance.api.IAttendance;
import com.encentral.ems.attendance.model.Attendance;
import com.encentral.ems.user.api.IUser;
import com.encentral.scaffold.commons.util.MyObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import play.data.FormFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Transactional
@Api(value = "Attendance")
public class AttendanceController extends Controller {

    @Inject
    IAttendance iAttendance;

    @Inject
    IUser iUser;

    @Inject
    FormFactory formFactory;

    @Inject
    MyObjectMapper objectMapper;

    @ApiOperation(value = "Check In ")
    @ApiResponses(
            value= {@ApiResponse(code = 201, response = Attendance.class, message="Check in successful")}
    )
    public Result checkIn(String userToken) throws JsonProcessingException {

        String errorMessage = iAttendance.checkTime();

        if (errorMessage.length()!=0){
            return badRequest(errorMessage);
        }

        boolean result = iAttendance.checkIn(userToken);

        if(result == false){
            return badRequest("user does not exist");
        }

        return ok(objectMapper.writeValueAsString(result));
    }


    @ApiOperation(value = "Check Out ")
    @ApiResponses(
            value= {@ApiResponse(code = 201, response = Attendance.class, message="Check out Successful")}
    )
    public Result checkOut(String userToken) throws JsonProcessingException {

        String errorMessage = iAttendance.checkTime();

        if (errorMessage.length()!=0){
            return badRequest(errorMessage);
        }

        boolean result = iAttendance.checkOut(userToken);

        if(result == false){
            return badRequest("User has not yet signed in");
        }

        return ok(objectMapper.writeValueAsString(result));
    }

    @ApiOperation(value = "Get daily Attendance")
    @ApiResponses(
            value= {@ApiResponse(code = 200, response = Attendance.class, message="Attendance fetched successfully")}
    )
    public Result getDailyAttendance(String date) throws JsonProcessingException {
        List<Attendance> result ;

        try {
            Date d =new SimpleDateFormat("dd/MM/yyyy").parse(date);
            result = iAttendance.getDailyAttendance(d);
        }
        catch(ParseException e){
            return badRequest(e.getMessage());
        }

        return ok(objectMapper.writeValueAsString(result));
    }

    @ApiOperation(value = "Get daily Attendance")
    @ApiResponses(
            value= {@ApiResponse(code = 200, response = Attendance.class, message="Attendance fetched successfully")}
    )
    public Result getUserAttendance(String userId) throws JsonProcessingException {

        List<Attendance> result  = iAttendance.getUserAttendance(userId);
        return ok(objectMapper.writeValueAsString(result));
    }



}