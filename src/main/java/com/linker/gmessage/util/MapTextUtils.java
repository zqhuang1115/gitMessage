package com.linker.gmessage.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author huang.ziqing
 * @date 2019/10/12
 */
public class MapTextUtils {
    public static  String textString(Map<String,Object> map) {
        Gson gson = new Gson();
        StringBuffer paramStr = new StringBuffer();
        final String[] returnString = new String[1];
        String action = String.valueOf(map.get(""));
        map.forEach((k, v) ->{
            if(k.equals("data")){
                Map<String, Object> mapdata ;
                try {
                    mapdata = gson.fromJson((new ObjectMapper()).writeValueAsString(v), map.getClass());
                    if(mapdata.containsKey("")){
                        Map<String, Object> mapproject = gson.fromJson(new ObjectMapper().writeValueAsString(mapdata.get("project")), map.getClass());
                        paramStr.append("["+mapproject.get("name")+"]");
                    }

                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        });
        return returnString[0];

    }
}
