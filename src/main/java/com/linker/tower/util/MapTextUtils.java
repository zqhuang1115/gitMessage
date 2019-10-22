package com.linker.tower.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * @author liu.jj
 * @date 2019/09/24
 */
public class MapTextUtils {

    public static  String textString(Map<String,Object> map) {
        Gson gson = new Gson();
        StringBuffer paramStr = new StringBuffer();
        final String[] returnString = new String[1];
        String action = String.valueOf(map.get("action"));
        map.forEach((k, v) ->{
                if(k.equals("data")){
                    Map<String, Object> mapdata ;
                    try {
                        mapdata = gson.fromJson((new ObjectMapper()).writeValueAsString(v), map.getClass());
                        if(mapdata.containsKey("project")){
                            Map<String, Object> mapproject = gson.fromJson(new ObjectMapper().writeValueAsString(mapdata.get("project")), map.getClass());
                            paramStr.append("["+mapproject.get("name")+"]");
                        }

                        if(mapdata.containsKey("todos::checkitem")){
                            Map<String, Object> todosCheckitem = gson.fromJson(new ObjectMapper().writeValueAsString(mapdata.get("todos::checkitem")), map.getClass());
                            returnString[0] = taskCheck(todosCheckitem, paramStr, action, map);
                        }else if(mapdata.containsKey("todo")){
                            Map<String, Object> maptodo = gson.fromJson(new ObjectMapper().writeValueAsString(mapdata.get("todo")), map.getClass());
                            returnString[0] = task(maptodo, paramStr, action, map);
                        }
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
        });
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


    public static  String task(Map<String, Object> maptodo,StringBuffer paramStr,String action,Map<String,Object> map) throws JsonProcessingException, ParseException {
        Gson gson = new Gson();

        if(maptodo.containsKey("handler")){
            Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
            paramStr.append(maphandler.get("nickname"));
        }
        if(action.equals("created")){
            if(maptodo.containsKey("assignee")){
                Map<String, Object> mapassignee = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("assignee")), map.getClass());
                paramStr.append("为"+mapassignee.get("nickname")+"创建了任务"+maptodo.get("title")+",完成时间设为");
                if(maptodo.get("due_at") == null){
                    paramStr.append("没有截止日期");
                }else{
                    paramStr.append(times(maptodo.get("due_at")));
                }
            }else{
                paramStr.append("创建了任务"+maptodo.get("title")+"，完成时间设为");
                if(maptodo.get("due_at") == null){
                    paramStr.append("没有截止日期");
                }else{
                    paramStr.append(times(maptodo.get("due_at")));
                }
            }
        }
        if(action.equals("assigned")){
            if(maptodo.containsKey("assignee")){
                Map<String, Object> mapassignee = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("assignee")), map.getClass());
                paramStr.append("把任务"+maptodo.get("title")+"指派给了"+mapassignee.get("nickname")+",完成截止时间为");
            }
            if(maptodo.get("due_at") == null){
                paramStr.append("没有截止日期");
            }else{
                paramStr.append(times(maptodo.get("due_at")));
            }
        }
        if(action.equals("unassigned")){
            if(maptodo.containsKey("assignee")){
                paramStr.append("取消了任务"+maptodo.get("title")+"的指派");
            }
        }
        if(action.equals("deadline_changed")){
            paramStr.append("将"+maptodo.get("title")+"的截止时间设为");
            if(maptodo.get("due_at") == null){
                paramStr.append("没有截止日期");
            }else{
                paramStr.append(times(maptodo.get("due_at")));
            }
        }
        if(action.equals("updated")){
            paramStr.append("编辑了任务"+maptodo.get("title")+"");
        }
        if(action.equals("completed")){
            paramStr.append("完成了任务"+maptodo.get("title")+"");
        }
        if(action.equals("started")){
            paramStr.append("开始处理任务"+maptodo.get("title")+"");
        }
        if(action.equals("paused")){
            paramStr.append("暂停处理任务"+maptodo.get("title")+"");
        }
        if(action.equals("deleted")){
            paramStr.append("删除了任务"+maptodo.get("title")+"");
        }
        if(action.equals("reopened")){
            paramStr.append("重新打开了任务"+maptodo.get("title")+"");
        }

        return  paramStr.toString();
    }

    private static String taskCheck(Map<String, Object> maptodo,StringBuffer paramStr,String action,Map<String,Object> map) throws JsonProcessingException, ParseException {
        Gson gson = new Gson();

        if(maptodo.containsKey("handler")){
            Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
            paramStr.append(maphandler.get("nickname"));
        }
        if(action.equals("created")){
            if(maptodo.containsKey("assignee")){
                Map<String, Object> mapassignee = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("assignee")), map.getClass());
                paramStr.append("为"+mapassignee.get("nickname")+"创建了检查项"+maptodo.get("title")+",完成时间设为");
                if(maptodo.get("due_at") == null){
                    paramStr.append("没有截止日期");
                }else{
                    paramStr.append(times(maptodo.get("due_at")));
                }
            }else{
                paramStr.append("创建了检查项"+maptodo.get("title")+"，完成时间设为");
                if(maptodo.get("due_at") == null){
                    paramStr.append("没有截止日期");
                }else{
                    paramStr.append(times(maptodo.get("due_at")));
                }
            }
        }
        if(action.equals("assigned")){
            if(maptodo.containsKey("assignee")){
                Map<String, Object> mapassignee = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("assignee")), map.getClass());
                paramStr.append("把检查项"+maptodo.get("title")+"指派给了"+mapassignee.get("nickname")+",完成截止时间为");
            }
            if(maptodo.get("due_at") == null){
                paramStr.append("没有截止日期");
            }else{
                paramStr.append(times(maptodo.get("due_at")));
            }
        }
        if(action.equals("unassigned")){
            if(maptodo.containsKey("assignee")){
                paramStr.append("取消了检查项"+maptodo.get("title")+"的指派");
            }
        }
        if(action.equals("deadline_changed")){
            paramStr.append("将"+maptodo.get("title")+"的截止时间设为");
            if(maptodo.get("due_at") == null){
                paramStr.append("没有截止日期");
            }else{
                paramStr.append(times(maptodo.get("due_at")));
            }
        }
        if(action.equals("updated")){
            paramStr.append("编辑了检查项"+maptodo.get("title")+"");
        }
        if(action.equals("completed")){
            paramStr.append("完成了检查项"+maptodo.get("title")+"");
        }
        if(action.equals("started")){
            paramStr.append("开始处理检查项"+maptodo.get("title")+"");
        }
        if(action.equals("paused")){
            paramStr.append("暂停处理检查项"+maptodo.get("title")+"");
        }
        if(action.equals("deleted")){
            paramStr.append("删除了检查项"+maptodo.get("title")+"");
        }
        if(action.equals("reopened")){
            paramStr.append("重新打开了检查项"+maptodo.get("title")+"");
        }

        return  paramStr.toString();
    }

}
