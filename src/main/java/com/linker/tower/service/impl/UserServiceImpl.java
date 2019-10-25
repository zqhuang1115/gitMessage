package com.linker.tower.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.linker.tower.common.LinkerException;
import com.linker.tower.domain.User;
import com.linker.tower.security.LinkerAuthenticationDetail;
import com.linker.tower.repository.UserRepository;
import com.linker.tower.security.SecurityUtils;

import com.linker.tower.service.UserService;
import com.linker.tower.web.rest.dto.UserBaseInfo;
import com.linker.tower.web.rest.dto.UserRegisterDTO;
import com.linker.tower.web.rest.util.NumberUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import utils.UserUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


/**
 * @author wang.gs
 * @date 2019-05-18
 */
@Slf4j
@Component("userDetailsService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    /**
     * 本地缓存用户ID/用户名/用户手机号/用户头像/融云token
     */
    private LoadingCache<String, UserBaseInfo> userBaseInfoCache = CacheBuilder.newBuilder()
            .expireAfterWrite(48, TimeUnit.HOURS).maximumSize(10000)
            .build(new CacheLoader<String, UserBaseInfo>() {

                @Override
                public UserBaseInfo load(final String userIdOrUsername) {
                    Optional<User> optionalUser;
                    if (NumberUtil.isNumeric(userIdOrUsername)) {
                        optionalUser = userRepository.findById(Long.valueOf(userIdOrUsername));
                    } else {
                        optionalUser = userRepository.findByUsername(userIdOrUsername);
                    }

                    if (optionalUser.isPresent()) {
                        User user = optionalUser.get();
                        UserBaseInfo userBaseInfo = new UserBaseInfo();
                        userBaseInfo.setPhone(user.getPhone());
                        userBaseInfo.setPhoto(user.getPhoto());
                        userBaseInfo.setUserId(user.getUserId());
                        userBaseInfo.setUsername(user.getUsername());
                        //userBaseInfo.setRcToken(user.getRcToken());
                        //userBaseInfo.setIsPro(user.getIsPro());
                        return userBaseInfo;
                    }
                    return null;
                }
            });

    @Override
    public User register(UserRegisterDTO userRegisterDTO) {
        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO, user);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        //user = userRepository.save(user);

        if (StringUtils.isEmpty(userRegisterDTO.getPhoto())) {
            user.setPhoto(UserUtils.getRandomPhoto("男"));
        }

        user = userRepository.save(user);

        return user;
    }
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    public UserBaseInfo getUserBaseInfo(Long userId) {
        try {
            return userBaseInfoCache.get(String.valueOf(userId));
        } catch (ExecutionException e) {
            throw new LinkerException("该用户不不存在");
        }
    }

    @Override
    public UserBaseInfo getCurrentUserBaseInfo() {
        return getUserBaseInfo(SecurityUtils.getAuthenticationDetail().getUserId());
    }
//
//    @Override
//    public List<String> findByPhoneList() {
//        return userRepository.findByPhoneList();
//    }
//
//    @Override
//    public List<String> findByEmail() {
//        return userRepository.findByEmail();
//    }
//
//    @Override
//    public List<Long> findByUsersId() {
//        return userRepository.findByUsersId();
//    }
//
//    @Override
//    public List<User> findByUsers() {
//        return userRepository.findByUsers();
//    }
//    @Override
//    public User register(UserRegisterDTO userRegisterDTO) {
//        User user = new User();
//        BeanUtils.copyProperties(userRegisterDTO, user);
//        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
//
//        //user = userRepository.save(user);
//
////        if (StringUtils.isEmpty(userRegisterDTO.getPhoto())) {
////            user.setPhoto(UserUtils.getRandomPhoto("男"));
////        }
////        if (StringUtils.isEmpty(userRegisterDTO.getNickname())) {
////            user.setNickname(UserUtils.getRandomNickName());
////        }
////
//        user = userRepository.save(user);
//
//        return user;
//    }
//    @Override
//    public boolean isUsernameRegister(String username) {
//        return userRepository.countByUsername(username) > 0;
//    }
//
//    @Override
//    public boolean isPhoneRegister(String phone) {
//        Optional<User> userOptional = userRepository.findByPhone(phone);
//        return userOptional.isPresent();
//    }
//
//
//    @Override
//    public User getUserInfo(Long userId) {
//        Optional<User> optionalUser = userRepository.findById(userId);
//        if (optionalUser.isPresent()) {
//            return optionalUser.get();
//        }
//        return null;
//    }
//
//    @Override
//    public void save(User user) {
//        userRepository.save(user);
//    }
//
//    @Override
//    public boolean insertPhone(String phone, Long userId) {
//        try {
//            User user = userRepository.findById(userId).get();
//            user.setPhone(phone);
//            //绑定手机积分通知 emailRegister(user);
//            userRepository.save(user);
//        } catch (Exception e) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public User findByPhone(String phone) {
//        Optional<User> optional = userRepository.findByPhone(phone);
//        if (optional.isPresent()) {
//            return userRepository.findByPhone(phone).get();
//        }
//        return null;
//    }
//
//    @Override
//    public User getUserInfo(Long userId) {
//        Optional<User> optionalUser = userRepository.findById(userId);
//        if (optionalUser.isPresent()) {
//            return optionalUser.get();
//        }
//        return null;
//    }
}
