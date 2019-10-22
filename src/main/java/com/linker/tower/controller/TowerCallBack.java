package com.linker.tower.controller;

import com.google.gson.Gson;
import com.linker.tower.bean.MarkdownMessage;
import com.linker.tower.bean.TestData;
import com.linker.tower.bean.TestMessage;
import com.linker.tower.config.MsgType;
import com.linker.tower.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author liu.jj
 * @date 2019/09/23
 */
@RestController
@RequestMapping("/api/tower")
public class TowerCallBack {

    @Autowired
    private CacheUtils cacheUtils;

    @Autowired
    private MD5Util mD5Util;

    @PostMapping("/callBackText")
    public void callBack(HttpServletRequest request) throws Exception {
        Map<String, Object> map = formatTransform(request);
        //遍历Map,转为微信API格式
       String textContent =  MapTextUtils.textString(map);
        textSend(textContent);
    }

    @PostMapping("/markdownSend")
    public void markdownSend(HttpServletRequest request) throws Exception {
        Map<String, Object> map = formatTransform(request);
        //遍历Map,转为微信API格式
        String textContent =  MapMarkdownUtils.markdownString(map);
        markdownSend(textContent);
    }


    public Map formatTransform(HttpServletRequest request)throws Exception{
        request.setCharacterEncoding("UTF-8");
        StringBuilder result = new StringBuilder();
        String line;
        BufferedReader reader = request.getReader();
        while (null != (line = reader.readLine())) {
            result.append(line);
        }
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<>();
        map = gson.fromJson(result.toString(), map.getClass());
        return map;
    }


    //文本发送
    public void textSend(String textContent){
        TestData testData = new TestData(textContent);
        TestMessage weixinData = new TestMessage(testData);
        weixinData.setMsgType(MsgType.TEXT.getCode());
        String url = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=160bcd9e-a85e-42f0-90f3-83785ffce672";
        JsonUtils.requestInvoke(url,weixinData);
    }

    //markdown发送
    public synchronized  void markdownSend(String textContent) throws ExecutionException {
        if(textContent.length()>10){
            String  encryption = mD5Util.getMdFive(textContent);
            String Content = cacheUtils.graphsGet();
            if(!encryption.equals(Content)){
                TestData testData = new TestData(textContent);
                MarkdownMessage weixinData = new MarkdownMessage(testData);
                weixinData.setMsgType(MsgType.MARKDOWN.getCode());
                System.out.println(textContent);
            String url = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=160bcd9e-a85e-42f0-90f3-83785ffce672";
            JsonUtils.requestInvoke(url,weixinData);
                cacheUtils.graphsInvalidate();
            }
            cacheUtils.graphsSet(encryption);
        }
    }
}
