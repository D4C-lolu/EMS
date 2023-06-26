package com.encentral.scaffold.commons.util;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class MyObjectMapper extends ObjectMapper {
    public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");


    public MyObjectMapper(){
        super();
        this.setDateFormat(DATE_FORMAT);
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

    }


    @Override
    public String writeValueAsString(Object value) throws JsonProcessingException {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.setDateFormat(DATE_FORMAT);
        return super.writeValueAsString(value);
    }

    public String toJSONString(Object value){
        try{
            return this.writeValueAsString(value);
        }
        catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }



    @Override
    public SimpleDateFormat getDateFormat(){
        return DATE_FORMAT;
    }
}