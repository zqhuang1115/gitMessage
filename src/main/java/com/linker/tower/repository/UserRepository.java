package com.linker.tower.repository;

import com.linker.tower.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;


/**
 * @author huang.ziqing
 * @date 2019/10/24
 */


public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * 根据用户名或手机号码查询
     *
     * @param username
     * @param phone
     * @return
     */
    Optional<User> findByUsernameOrPhone(String username, String phone);

    Optional<User> findByPhone(String phone);

    Optional<User> findByUsername(String username);


    int countByUsername(String username);

    Optional<User> findByEmail(String email);


    @Query(nativeQuery = true , value = "select count(*) from user ")
    int findByUser();

    @Query("select phone from User  where phone is not null")
    List<String> findByPhoneList();

    @Query("select email from User  where email is not null")
    List<String> findByEmail();

    @Query("select userId from User")
    List<Long> findByUsersId();

    @Query(" from User  where email is not null")
    List<User> findByUsers();
}
