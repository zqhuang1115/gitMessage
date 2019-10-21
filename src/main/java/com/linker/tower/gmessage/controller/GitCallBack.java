package com.linker.tower.gmessage.controller;

import com.google.gson.Gson;
import com.linker.tower.gmessage.bean.TestData;
import com.linker.tower.gmessage.bean.TestMessage;
import com.linker.tower.gmessage.config.MsgType;
import com.linker.tower.gmessage.util.JsonUtils;
import com.linker.tower.gmessage.util.MapTextUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

//import com.linker.tower.gmessage.util.MapMarkdownUtils;

/**
 * @author huang.ziqing
 * @date 2019/10/12
 */

@RestController
@RequestMapping("/api/git")
public class GitCallBack {

    @PostMapping("/callBackText")
    public void callBack(HttpServletRequest request) throws Exception {
        Map<String, Object> map = formatTransform(request);
        //遍历Map,转为微信API格式
        String textContent =  MapTextUtils.textString(map);
        textSend(textContent);
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
        System.out.println(textContent);

        TestData testData = new TestData(textContent);
        TestMessage weixinData = new TestMessage(testData);
        weixinData.setMsgType(MsgType.TEXT.getCode());
        //String url = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=410c3682-f48f-4930-bc3d-4ffa18048d09";
        //JsonUtils.requestInvoke(url,weixinData);
    }



}