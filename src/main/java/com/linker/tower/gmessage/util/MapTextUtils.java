
package com.linker.tower.gmessage.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author huang.ziqing
 * @date 2019/10/12
 */
@Slf4j
public class MapTextUtils {

    public static  String textString(Map<String,Object> map) throws ParseException {
        Gson gson = new Gson();
        StringBuffer paramStr = new StringBuffer();
        Map<String, Object> mappusher = null;
        Map<String, Object> mapmessage = null;
        Map<String, Object> maprepo = null;
        // final String[] returnString = new String[1];
        // String action = String.valueOf(map.get("action"));
        log.error("map.entry"+map.entrySet());
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            //System.out.println("entry" + entry);

            String k = entry.getKey();
            Object v = entry.getValue();
            if (k.equals("pusher")) {
                try {
                    mappusher = gson.fromJson((new ObjectMapper()).writeValueAsString(v), map.getClass());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            if (k.equals("head_commit")) {
                try {
                    mapmessage = gson.fromJson((new ObjectMapper()).writeValueAsString(v), map.getClass());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            if (k.equals("repository")) {
                try {
                    maprepo = gson.fromJson((new ObjectMapper()).writeValueAsString(v), map.getClass());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
        paramStr.append(mappusher.get("name") + "在" +
                maprepo.get("name") + "项目中提交新代码\n" +
                "提交信息：" + mapmessage.get("message")+"\n" +
                "提交时间：" + times(mapmessage.get("timestamp")) );

        return paramStr.toString();

    }

    public static String times(Object obj) throws ParseException {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
        date = sdf.parse(String.valueOf(obj));
        //System.out.println("UTC时间: " + date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR));
        //calendar.getTime() 返回的是Date类型，也可以使用calendar.getTimeInMillis()获取时间戳
        return (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm")).format(calendar.getTime());
    }

}//timestamp=2019-10-22T10:31:03+08:00