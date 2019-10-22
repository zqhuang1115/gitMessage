package com.linker.tower.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.text.ParseException;
import java.util.Map;

/**
 * @author liu.jj
 * @date 2019/09/25
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
                    if(String.valueOf(map.get("action")).equals("commented")){
                        returnString[0] = commented(mapdata, paramStr, map);
                    }
                    if (mapdata.containsKey("todos::checkitem")) {
                        Map<String, Object> todosCheckitem = gson.fromJson(new ObjectMapper().writeValueAsString(mapdata.get("todos::checkitem")), map.getClass());
                        returnString[0] = taskCheck(todosCheckitem,  paramStr, action, map);
                    } else if (mapdata.containsKey("todo")) {
                        Map<String, Object> maptodo = gson.fromJson(new ObjectMapper().writeValueAsString(mapdata.get("todo")), map.getClass());
                        returnString[0] = task(maptodo,mapproject, paramStr, action, map);
                    } else if (mapdata.containsKey("todolist")) {
                        Map<String, Object> todolists = gson.fromJson(new ObjectMapper().writeValueAsString(mapdata.get("todolist")), map.getClass());
                        returnString[0] = taskInventory(todolists, paramStr, action, map);
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

    private static String commented(Map<String, Object> mapdata, StringBuffer paramStr,Map<String, Object> map) throws JsonProcessingException {
        Gson gson = new Gson();
        if (mapdata.containsKey("todolist")) {
            if(mapdata.containsKey("todo")){
                Map<String, Object> todolists = gson.fromJson((new ObjectMapper()).writeValueAsString(mapdata.get("todo")), map.getClass());
                if (todolists.containsKey("handler")) {
                    Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(todolists.get("handler")), map.getClass());
                    paramStr.append("\n><font color=\"comment\">" + maphandler.get("nickname") + "评论了任务"+todolists.get("title")+"</font>");
                }
            }else{
                Map<String, Object> todolists = gson.fromJson((new ObjectMapper()).writeValueAsString(mapdata.get("todolist")), map.getClass());
                if (todolists.containsKey("handler")) {
                    Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(todolists.get("handler")), map.getClass());
                    paramStr.append("\n><font color=\"comment\">" + maphandler.get("nickname") + "评论了任务清单"+todolists.get("title")+"</font>");
                }
            }
        }else if(mapdata.containsKey("topic")){
            Map<String, Object> todolists = gson.fromJson((new ObjectMapper()).writeValueAsString(mapdata.get("topic")), map.getClass());
            if (todolists.containsKey("handler")) {
                Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(todolists.get("handler")), map.getClass());
                paramStr.append("\n><font color=\"comment\">" + maphandler.get("nickname") + "评论了讨论"+todolists.get("title")+"</font>");
            }
        }
        else if(mapdata.containsKey("document")){
            Map<String, Object> todolists = gson.fromJson((new ObjectMapper()).writeValueAsString(mapdata.get("document")), map.getClass());
            if (todolists.containsKey("handler")) {
                Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(todolists.get("handler")), map.getClass());
                paramStr.append("\n><font color=\"comment\">" + maphandler.get("nickname") + "评论了文档"+todolists.get("title")+"</font>");
            }
        }
        else if(mapdata.containsKey("attachment")){
            Map<String, Object> todolists = gson.fromJson((new ObjectMapper()).writeValueAsString(mapdata.get("attachment")), map.getClass());
            if (todolists.containsKey("handler")) {
                Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(todolists.get("handler")), map.getClass());
                paramStr.append("\n><font color=\"comment\">" + maphandler.get("nickname") + "评论了文件"+todolists.get("title")+"</font>");
            }
        }
        return paramStr.toString();
    }

    public static String task(Map<String, Object> maptodo,Map<String, Object> mapproject,StringBuffer paramStr, String action, Map<String, Object> map) throws JsonProcessingException, ParseException {
        Gson gson = new Gson();

        if (action.equals("created")) {
            if (maptodo.containsKey("assignee")) {
                Map<String, Object> mapassignee = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("assignee")), map.getClass());
                paramStr.append("创建任务[" + maptodo.get("title") + "](https://tower.im/projects/"+mapproject.get("guid")+"/todos/"+maptodo.get("guid")+")\n");
                if (maptodo.containsKey("handler")) {
                    Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                    paramStr.append(">操作者：<font color=\"comment\">" + maphandler.get("nickname") + "</font>\n");
                    paramStr.append(">指派人：<font color=\"comment\">" + mapassignee.get("nickname") + "</font>\n");
                }
                if (maptodo.get("due_at") == null) {
                    paramStr.append(">完成时间：<font color=\"comment\">没有截止日期</font>");
                } else {
                    paramStr.append(">完成时间：<font color=\"comment\">" + MapTextUtils.times(maptodo.get("due_at")) + "</font>");
                }
            } else {
                paramStr.append("创建任务[" + maptodo.get("title") + "](https://tower.im/projects/"+mapproject.get("guid")+"/todos/"+maptodo.get("guid")+")\n");
                if (maptodo.containsKey("handler")) {
                    Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                    paramStr.append(">操作者：<font color=\"comment\">" + maphandler.get("nickname") + "</font>\n");
                    paramStr.append(">指派人：<font color=\"comment\">无</font>\n");
                }
                if (maptodo.get("due_at") == null) {
                    paramStr.append(">完成时间：<font color=\"comment\">没有截止日期</font>");
                } else {
                    paramStr.append(">完成时间：<font color=\"comment\">" + MapTextUtils.times(maptodo.get("due_at")) + "</font>");
                }
            }
        }
        if (action.equals("assigned")) {
            if (maptodo.containsKey("assignee")) {
                Map<String, Object> mapassignee = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("assignee")), map.getClass());
                paramStr.append("指派任务[" + maptodo.get("title") + "](https://tower.im/projects/"+mapproject.get("guid")+"/todos/"+maptodo.get("guid")+")\n");
                if (maptodo.containsKey("handler")) {
                    Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                    paramStr.append(">操作者：<font color=\"comment\">" + maphandler.get("nickname") + "</font>\n");
                    paramStr.append(">指派人：<font color=\"comment\">" + mapassignee.get("nickname") + "</font>\n");
                }
            }
            if (maptodo.get("due_at") == null) {
                paramStr.append(">完成时间：<font color=\"comment\">没有截止日期</font>");
            } else {
                paramStr.append(">完成时间：<font color=\"comment\">" + MapTextUtils.times(maptodo.get("due_at")) + "</font>");
            }
        }
        if (action.equals("unassigned")) {
            if (!maptodo.containsKey("assignee")) {
                paramStr.append("取消任务<font color=\"warning\">" + maptodo.get("title") + "</font>的指派\n");
                if (maptodo.containsKey("handler")) {
                    Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                    paramStr.append(">操作者：<font color=\"comment\">" + maphandler.get("nickname") + "</font>\n");
                }
            }
        }
        if (action.equals("deadline_changed")) {
            paramStr.append("设置任务<font color=\"warning\">" + maptodo.get("title") + "</font>的完成时间\n");
            if (maptodo.containsKey("handler")) {
                Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                paramStr.append(">操作者：<font color=\"comment\">" + maphandler.get("nickname") + "</font>\n");
            }
            if (maptodo.get("due_at") == null) {
                paramStr.append(">完成时间：没有截止日期");
            } else {
                paramStr.append(">完成时间：<font color=\"comment\">" + MapTextUtils.times(maptodo.get("due_at")) + "</font>");
            }
        }
        if (action.equals("updated")) {
            if (maptodo.containsKey("handler")) {
                Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                paramStr.append("<font color=\"comment\">" + maphandler.get("nickname") + "</font>");
            }
            paramStr.append("编辑了任务<font color=\"warning\">" + maptodo.get("title") + "</font>\n");
        }
        if (action.equals("completed")) {
            if (maptodo.containsKey("handler")) {
                Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                paramStr.append("<font color=\"comment\">" + maphandler.get("nickname") + "</font>");
            }
            paramStr.append("完成了任务<font color=\"warning\">" + maptodo.get("title") + "</font>\n");
        }
        if (action.equals("started")) {
            if (maptodo.containsKey("handler")) {
                Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                paramStr.append("<font color=\"comment\">" + maphandler.get("nickname") + "</font>");
            }
            paramStr.append("开始处理任务<font color=\"warning\">" + maptodo.get("title") + "</font>\n");
        }
        if (action.equals("paused")) {
            if (maptodo.containsKey("handler")) {
                Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                paramStr.append("<font color=\"comment\">" + maphandler.get("nickname") + "</font>");
            }
            paramStr.append("暂停处理任务<font color=\"warning\">" + maptodo.get("title") + "</font>\n");
        }
        if (action.equals("deleted")) {
            if (maptodo.containsKey("handler")) {
                Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                paramStr.append("<font color=\"comment\">" + maphandler.get("nickname") + "</font>");
            }
            paramStr.append("删除了任务<font color=\"warning\">" + maptodo.get("title") + "</font>\n");
        }
        if (action.equals("reopened")) {
            if (maptodo.containsKey("handler")) {
                Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                paramStr.append("<font color=\"comment\">" + maphandler.get("nickname") + "</font>");
            }
            paramStr.append("重新打开了任务<font color=\"warning\">" + maptodo.get("title") + "</font>\n");
        }

        return paramStr.toString();
    }

    private static String taskCheck(Map<String, Object> maptodo, StringBuffer paramStr, String action, Map<String, Object> map) throws JsonProcessingException, ParseException {
        Gson gson = new Gson();

        if (action.equals("created")) {
            if (maptodo.containsKey("assignee")) {
                Map<String, Object> mapassignee = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("assignee")), map.getClass());
                paramStr.append("创建检查项<font color=\"warning\">" + maptodo.get("title") + "</font>\n");
                if (maptodo.containsKey("handler")) {
                    Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                    paramStr.append(">操作者：<font color=\"comment\">" + maphandler.get("nickname") + "</font>\n");
                    paramStr.append(">指派人：<font color=\"comment\">" + mapassignee.get("nickname") + "</font>\n");
                }
                if (maptodo.get("due_at") == null) {
                    paramStr.append(">完成时间：<font color=\"comment\">没有截止日期</font>");
                } else {
                    paramStr.append(">完成时间：<font color=\"comment\">" + MapTextUtils.times(maptodo.get("due_at")) + "</font>");
                }
            } else {
                paramStr.append("创建检查项<font color=\"warning\">" + maptodo.get("title") + "</font>\n");
                if (maptodo.containsKey("handler")) {
                    Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                    paramStr.append(">操作者：<font color=\"comment\">" + maphandler.get("nickname") + "</font>\n");
                    paramStr.append(">指派人：<font color=\"comment\">无</font>\n");
                }
                if (maptodo.get("due_at") == null) {
                    paramStr.append(">完成时间：<font color=\"comment\">没有截止日期</font>");
                } else {
                    paramStr.append(">完成时间：<font color=\"comment\">" + MapTextUtils.times(maptodo.get("due_at")) + "</font>");
                }
            }
        }
        if (action.equals("assigned")) {
            if (maptodo.containsKey("assignee")) {
                Map<String, Object> mapassignee = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("assignee")), map.getClass());
                paramStr.append("指派检查项<font color=\"warning\">" + maptodo.get("title") + "</font>\n");
                if (maptodo.containsKey("handler")) {
                    Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                    paramStr.append(">操作者：<font color=\"comment\">" + maphandler.get("nickname") + "</font>\n");
                    paramStr.append(">指派人：<font color=\"comment\">" + mapassignee.get("nickname") + "</font>\n");
                }
            }
            if (maptodo.get("due_at") == null) {
                paramStr.append(">完成时间：<font color=\"comment\">没有截止日期</font>");
            } else {
                paramStr.append(">完成时间：<font color=\"comment\">" + MapTextUtils.times(maptodo.get("due_at")) + "</font>");
            }
        }
        if (action.equals("unassigned")) {
            if (maptodo.containsKey("assignee")) {
                paramStr.append("取消检查项<font color=\"warning\">" + maptodo.get("title") + "</font>的指派\n");
                if (maptodo.containsKey("handler")) {
                    Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                    paramStr.append(">操作者：<font color=\"comment\">" + maphandler.get("nickname") + "</font>\n");
                }
            }
        }
        if (action.equals("deadline_changed")) {
            paramStr.append("设置检查项<font color=\"warning\">" + maptodo.get("title") + "</font>的完成时间");
            if (maptodo.containsKey("handler")) {
                Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                paramStr.append(">操作者：<font color=\"comment\">" + maphandler.get("nickname") + "</font>\n");
            }
            if (maptodo.get("due_at") == null) {
                paramStr.append(">完成时间：没有截止日期");
            } else {
                paramStr.append(">完成时间：<font color=\"comment\">" + MapTextUtils.times(maptodo.get("due_at")) + "</font>");
            }
        }
        if (action.equals("updated")) {
            if (maptodo.containsKey("handler")) {
                Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                paramStr.append("<font color=\"comment\">" + maphandler.get("nickname") + "</font>");
            }
            paramStr.append("编辑了检查项<font color=\"warning\">" + maptodo.get("title") + "</font>\n");
        }
        if (action.equals("completed")) {
            if (maptodo.containsKey("handler")) {
                Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                paramStr.append("<font color=\"comment\">" + maphandler.get("nickname") + "</font>");
            }
            paramStr.append("完成了检查项<font color=\"warning\">" + maptodo.get("title") + "</font>\n");
        }
        if (action.equals("started")) {
            if (maptodo.containsKey("handler")) {
                Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                paramStr.append("<font color=\"comment\">" + maphandler.get("nickname") + "</font>");
            }
            paramStr.append("开始处理检查项<font color=\"warning\">" + maptodo.get("title") + "</font>\n");
        }
        if (action.equals("paused")) {
            if (maptodo.containsKey("handler")) {
                Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                paramStr.append("<font color=\"comment\">" + maphandler.get("nickname") + "</font>");
            }
            paramStr.append("暂停处理检查项<font color=\"warning\">" + maptodo.get("title") + "</font>\n");
        }
        if (action.equals("deleted")) {
            if (maptodo.containsKey("handler")) {
                Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                paramStr.append("<font color=\"comment\">" + maphandler.get("nickname") + "</font>");
            }
            paramStr.append("删除了检查项<font color=\"warning\">" + maptodo.get("title") + "</font>\n");
        }
        if (action.equals("reopened")) {
            if (maptodo.containsKey("handler")) {
                Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                paramStr.append("<font color=\"comment\">" + maphandler.get("nickname") + "</font>");
            }
            paramStr.append("重新打开了检查项<font color=\"warning\">" + maptodo.get("title") + "</font>\n");
        }

        return paramStr.toString();
    }


    private static String taskInventory(Map<String, Object> maptodo, StringBuffer paramStr, String action, Map<String, Object> map) throws JsonProcessingException, ParseException {
        Gson gson = new Gson();

        if (action.equals("created")) {
            paramStr.append("创建了任务清单<font color=\"warning\">" + maptodo.get("title") + "</font>\n");
            if (maptodo.containsKey("handler")) {
                Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                paramStr.append(">操作者：<font color=\"comment\">" + maphandler.get("nickname") + "</font>\n");
            }
         }
        if (action.equals("updated")) {
            paramStr.append("更新了任务清单<font color=\"warning\">" + maptodo.get("title") + "</font>\n");
            if (maptodo.containsKey("handler")) {
                Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                paramStr.append(">操作者：<font color=\"comment\">" + maphandler.get("nickname") + "</font>\n");
            }
        }
        if (action.equals("unarchived")) {
            paramStr.append("重新激活了任务清单<font color=\"warning\">" + maptodo.get("title") + "</font>\n");
            if (maptodo.containsKey("handler")) {
                Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                paramStr.append(">操作者：<font color=\"comment\">" + maphandler.get("nickname") + "</font>\n");
            }
        }
        if (action.equals("deleted")) {
            paramStr.append("删除了任务清单<font color=\"warning\">" + maptodo.get("title") + "</font>\n");
            if (maptodo.containsKey("handler")) {
                Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                paramStr.append(">操作者：<font color=\"comment\">" + maphandler.get("nickname") + "</font>\n");
            }
        }
        if (action.equals("archived")) {
            paramStr.append("归档了任务清单<font color=\"warning\">" + maptodo.get("title") + "</font>\n");
            if (maptodo.containsKey("handler")) {
                Map<String, Object> maphandler = gson.fromJson(new ObjectMapper().writeValueAsString(maptodo.get("handler")), map.getClass());
                paramStr.append(">操作者：<font color=\"comment\">" + maphandler.get("nickname") + "</font>\n");
            }
        }

        return paramStr.toString();
    }
}
