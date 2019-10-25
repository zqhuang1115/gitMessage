package com.linker.tower.config;

import com.linker.tower.admin.utils.AdminUtils;
import com.linker.tower.service.UserService;
import com.linker.tower.common.Constant;
import com.linker.tower.domain.User;
import com.linker.tower.web.rest.dto.UserRegisterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class InitDatabase implements CommandLineRunner {


    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        createAdminUser();

        //创建机器人使用用户
        createLinkerUser();

    }


    private void createAdminUser() {
        Optional<User> optionalUser = userService.findByUsername(AdminUtils.ADMIN_NAME);
        if (optionalUser.isPresent()) {
            return;
        }

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        //userRegisterDTO.setChannel("init");
        userRegisterDTO.setPassword("linkerABC001");
        userRegisterDTO.setPhone(AdminUtils.ADMIN_PHONE);
        userRegisterDTO.setUsername(AdminUtils.ADMIN_NAME);
        userService.register(userRegisterDTO);
    }


    private void createLinkerUser() {
        Optional<User> optionalUser = userService.findByUsername(Constant.LINKER_USERNAME);
        if (optionalUser.isPresent()) {
            return;
        }

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        //userRegisterDTO.setChannel("init");
        userRegisterDTO.setPassword("linker123");
        userRegisterDTO.setPhone("12345678911");
        userRegisterDTO.setUsername(Constant.LINKER_USERNAME);
        userService.register(userRegisterDTO);
    }



}
