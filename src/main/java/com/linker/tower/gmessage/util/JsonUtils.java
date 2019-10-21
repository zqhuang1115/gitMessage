package com.linker.tower.gmessage.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author huang.ziqing
 * @date 2019/10/12
 */
@Slf4j
public class JsonUtils {

    public static final ObjectMapper objectMapper = new ObjectMapper();



    private <T> T toBean(String json, Class<T> cls) {
        try {
            return objectMapper.readValue(json, cls);
        } catch (IOException e) {
            log.error("json:{} 转换类型失败: {} ", json, cls);
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * http 请求service
     *
     * @param url
     * @param params
     * @return
     */
    public static String requestInvoke(String url, Object params) {
        String json = null;
        try {
            json = HttpClientUtil.sendJsonData(url, JsonUtils.obj2Str(params));
            log.info("request url {}, the params is: {}", url, objectMapper.writeValueAsString(params));
            log.info("request result is {}", json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 将对象转换为字符转
     *
     * @param object
     * @return
     */
    public static String obj2Str(Object object) {
        String str = "";
        try {
            str = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return str;
    }
}
