package com.encentral.ems.user.model;

import com.encentral.entities.JpaUser;

public class UserMapper {
    public static JpaUser userToJpaUser(User user){
        if (user ==null){
            return null;
        }
        JpaUser jpaUser = new JpaUser();
        jpaUser.setUserId(user.getUserId());
        jpaUser.setEmail(user.getEmail());
        jpaUser.setUserToken(user.getUserToken());
        jpaUser.setFirstName(user.getFirstName());
        jpaUser.setLastName(user.getLastName());
        jpaUser.setRole(user.getRole());
        jpaUser.setPassword(user.getPassword());
        return jpaUser;
    }

    public static User jpaUserToUser(JpaUser jpaUser){
        if (jpaUser ==null){
            return null;
        }
        User user = new User();
        user.setUserId(jpaUser.getUserId());
        user.setEmail(jpaUser.getEmail());
        user.setFirstName(jpaUser.getFirstName());
        user.setLastName(jpaUser.getLastName());
        user.setRole(jpaUser.getRole());
        user.setUserToken(jpaUser.getUserToken());
        user.setPassword(jpaUser.getPassword());
        return user;
    }
}
