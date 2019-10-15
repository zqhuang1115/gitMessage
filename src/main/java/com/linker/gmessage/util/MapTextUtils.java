
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
        // String action = String.valueOf(map.get("action"));
        //String action = String.valueOf(map);
//        System.out.println("this is k"+map.keySet());
//        System.out.println(map);
//        System.out.println(map.get("pusher"));
        map.forEach((k, v) -> {
            if (k.equals("pusher")) {
                //System.out.println(name);
                Map<String, Object> mapdata;
                try {
                    mapdata = gson.fromJson((new ObjectMapper()).writeValueAsString(v), map.getClass());
                    if (mapdata.containsKey("repository")) {
                        Map<String, Object> maprepo = gson.fromJson(new ObjectMapper().writeValueAsString(mapdata.get("repository")), map.getClass());
                        paramStr.append("[" + maprepo.get("full_name") + "]");
                    }
                    if (mapdata.containsKey("commits")) {
                        Map<String, Object> mapcommits = gson.fromJson(new ObjectMapper().writeValueAsString(mapdata.get("commits")), map.getClass());
                        paramStr.append("Commiter" + mapcommits.get("commiter"));
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        });
//        map.forEach((k, v) ->{
//            if(k.equals("pusher")) {
////                System.out.println(k);
////                Map<String, Object> mapdata;
////                try {
////                    mapdata = gson.fromJson((new ObjectMapper()).writeValueAsString(k), map.getClass());
////                    if (mapdata.containsKey("repository")) {
////                        Map<String, Object> maprepo = gson.fromJson(new ObjectMapper().writeValueAsString(mapdata.get("repository")), map.getClass());
////                        paramStr.append("[" + maprepo.get("full_name") + "]");
////                    }
//////                    if(mapdata.containsKey("commits")){
//////                        Map<String, Object> mapcommits = gson.fromJson(new ObjectMapper().writeValueAsString(mapdata.get("commits")), map.getClass());
//////                        paramStr.append("Commiter"+mapcommits.get("commiter")+"]");
//////                    }
////                } catch (JsonProcessingException e) {
////                    e.printStackTrace();
////                }
//            }
//        });
        System.out.println("this is para"+paramStr);
        return returnString[0];

    }

    public static  String times(Object obj) throws ParseException {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        date = sdf.parse(String.valueOf(obj));
        System.out.println("UTC时间: " + date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
        //calendar.getTime() 返回的是Date类型，也可以使用calendar.getTimeInMillis()获取时间戳
        return (new SimpleDateFormat("yyyy-MM-dd")).format(calendar.getTime());
    }

//    public static  String task(Map<String, Object> mapcommits,StringBuffer paramStr,String action,Map<String,Object> map) throws JsonProcessingException, ParseException {
//        Gson gson = new Gson();
//
//        if(mapcommits.containsKey("commiter")){
//            paramStr.append("操作者：").append(mapcommits.get("commiter"));
//            if(mapcommits.get("message") == null){
//                paramStr.append("没有信息");
//            }else{
//                paramStr.append(times(mapcommits.get("message")));
//            }
//        }
//        return  paramStr.toString();
//    }
}