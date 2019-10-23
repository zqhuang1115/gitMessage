package com.linker.tower.gmessage.controller;

import com.google.gson.Gson;
import com.linker.tower.gmessage.bean.TestData;
import com.linker.tower.gmessage.bean.TestMessage;
import com.linker.tower.gmessage.config.MsgType;
import com.linker.tower.gmessage.util.JsonUtils;
import com.linker.tower.gmessage.util.MapTextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Map;

//import com.linker.tower.gmessage.util.MapMarkdownUtils;

/**
 * @author huang.ziqing
 * @date 2019/10/12
 */
@Slf4j
@RestController
@RequestMapping("/api/git")
public class GitCallBack {

    @PostMapping("/callBackText")
    public void callBack(HttpServletRequest request) throws Exception {

        String content = IOUtils.toString(request.getInputStream(), request.getCharacterEncoding());

        //log.info("content={}", content);

        if (StringUtils.isEmpty(content)) {
            return;
        }

        Map<String, Object> map = formatTransform(content);
        //log.info("map" + map);
        Map<String, Object> map2 = formatTransform(request);
        log.info("request: "+request);

        log.info("map2: "+map2);
        //遍历Map,转为微信API格式
        String textContent = MapTextUtils.textString(map);
        textSend(textContent);
    }


    public Map formatTransform(String content) throws Exception {
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(content, Map.class);
        return map;
    }


    //文本发送
    public void textSend(String textContent) {

        // 空文本不用发送
        if (StringUtils.isEmpty(textContent)) {
            return;
        }
        System.out.println(textContent);

        TestData testData = new TestData(textContent);
        TestMessage weixinData = new TestMessage(testData);
        weixinData.setMsgType(MsgType.TEXT.getCode());
        //String url = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=410c3682-f48f-4930-bc3d-4ffa18048d09";
        //JsonUtils.requestInvoke(url, weixinData);
    }


}
