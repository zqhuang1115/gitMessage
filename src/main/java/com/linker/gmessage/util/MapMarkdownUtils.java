package com.linker.gmessage.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.Map;

/**
 * @author huang.ziqing
 * @date 2019/10/12
 */
public class MapMarkdownUtils {
    public static String markdownString(Map<String, Object> map) {
        Gson gson = new Gson();
        StringBuffer paramStr = new StringBuffer();
        final String[] returnString = new String[1];
        String action = String.valueOf(map.get("action"));

        map.forEach((k, v) -> {
            if (k.equals("data")) {
                Map<String, Object> mapdata;
                Map<String, Object> mapproject = null;
                try {
                    mapdata = gson.fromJson((new ObjectMapper()).writeValueAsString(v), map.getClass());
                    if (mapdata.containsKey("project")) {
                        mapproject = gson.fromJson(new ObjectMapper().writeValueAsString(mapdata.get("project")), map.getClass());
                        paramStr.append("<font color=\"info\">【" + mapproject.get("name") + "】</font>");
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        });
        return returnString[0];
    }
}
