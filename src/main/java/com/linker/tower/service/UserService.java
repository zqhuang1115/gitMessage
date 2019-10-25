package com.linker.tower.service;

import com.linker.tower.domain.User;
import com.linker.tower.web.rest.dto.UserBaseInfo;
import com.linker.tower.web.rest.dto.UserRegisterDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author huang.ziqing
 * @date 2019/10/24
 */
public interface UserService {

    /**
     * 注册
     *
     * @param userRegisterDTO
     * @return
     */
    User register(UserRegisterDTO userRegisterDTO);


    /**
     * 查询用户
     *
     * @param username
     * @return
     */
    Optional<User> findByUsername(String username);

    /**
     * 查询用户基本信息 在该接口数据满足数据要求的情况下优先使用该接口
     *
     * @param userId
     * @return
     */
    UserBaseInfo getUserBaseInfo(Long userId);

    /**
     * 查询当前登录用户基本信息 在该接口数据满足数据要求的情况下优先使用该接口
     *
     * @return
     */
    UserBaseInfo getCurrentUserBaseInfo();

    /**
     * 查询用户基本信息 在该接口数据满足数据要求的情况下优先使用该接口
     *
     * @param username
     * @return
     */

}
