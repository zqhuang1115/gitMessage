package com.linker.tower.admin.utils;

import com.linker.tower.service.UserService;
import com.linker.tower.web.rest.dto.UserBaseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author wang.gs
 * @date 2019/08/06
 */
@Component
public class AdminUtils {

    public static String ADMIN_NAME = "admin";
    public static String ADMIN_PHONE = "15900000000";



    @Autowired
    private UserService userService;


    /**
     * 是否是管理员用户
     *
     * @return
     */
    public boolean isAdmin() {
        UserBaseInfo userBaseInfo = userService.getCurrentUserBaseInfo();
        return ADMIN_NAME.equals(userBaseInfo.getUsername())
                && ADMIN_PHONE.equals(userBaseInfo.getPhone());
    }



}
