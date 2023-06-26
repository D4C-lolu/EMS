package com.encentral.ems.user.impl;


import com.encentral.ems.user.api.IUser;
import com.encentral.ems.user.model.User;
import com.encentral.ems.user.model.UserMapper;
import com.encentral.entities.JpaUser;
import com.encentral.entities.Role;
import play.db.jpa.JPAApi;

import javax.inject.Inject;
import javax.persistence.Query;
import java.sql.Date;
import java.util.*;

public class DefaultUserImpl implements IUser {
    private final JPAApi jpaApi;

    @Inject
    public DefaultUserImpl(JPAApi jpaApi) {
        this.jpaApi = jpaApi;

        //Add Admin
        //email: admin@encentral.com and password: admin
//        this.jpaApi.withTransaction(em->{
//            String id =
//            JpaUser jpaUser = new JpaUser()
//        });

    }

    @Override
    public Optional<String> addUser(User user) {

        jpaApi.withTransaction(em -> {
            //Check if User already exists
            Query query = jpaApi.em().
                    createNamedQuery("JpaUser.findByEmail", JpaUser.class);
            query.setParameter("email", user.getEmail());
            JpaUser j = (JpaUser) query.getSingleResult();
            if (j != null) {
                return Optional.empty();
            }

            JpaUser jpaUser = UserMapper.userToJpaUser(user);
            jpaUser.setUserId(UUID.randomUUID().toString());

            //Get Current Date
            Date date = new java.sql.Date(new java.util.Date().getTime());
            jpaUser.setDateAdded(date);

            //Generate random 4 digit pin
            Random random = new Random();
            String password = String.format("%04d", random.nextInt(10000));
            jpaUser.setPassword(password);

            //Generate Random Token
            String userToken = UUID.randomUUID().toString();
            jpaUser.setUserToken(userToken);

            jpaUser.setJpaAttendanceList(new ArrayList<>());
            jpaApi.em().persist(jpaUser);
            return Optional.ofNullable(password);
        });

        return Optional.empty();
    }

    @Override
    public boolean updateUser(String userToken, User user) {
        jpaApi.withTransaction(em -> {
            Query query = em.createNamedQuery("JpaUser.findByToken", JpaUser.class);
            query.setParameter("token", userToken);
            JpaUser u = (JpaUser) query.getSingleResult();
            if (u == null) {
                return false;
            }
            u.setRole(user.getRole());
            u.setPassword(user.getPassword());
            u.setLastName(user.getLastName());
            u.setFirstName(user.getFirstName());
            u.setUserToken(user.getUserToken());
            em.merge(u);
            return true;
        });
        return false;
    }

    @Override
    public Optional<User> getUser(String userId) {
        JpaUser jpaUser = jpaApi.em().find(JpaUser.class, userId);
        User user = UserMapper.jpaUserToUser(jpaUser);
        return Optional.ofNullable(user);
    }

    @Override
    public boolean deleteUser(String userId) {
        jpaApi.withTransaction(em -> {
            JpaUser jpaUser = em.find(JpaUser.class, userId);
            if (jpaUser == null) {
                return false;
            }
            em.remove(jpaUser);
            return true;
        });
        return false;
    }

    @Override
    public Optional<String> signUserIn(User user) {
        Query query = jpaApi.em().
                createNamedQuery("JpaUser.findByEmail", JpaUser.class);
        query.setParameter("email", user.getEmail());
        JpaUser jpaUser = (JpaUser) query.getSingleResult();
        User u = UserMapper.jpaUserToUser(jpaUser);
        if (!user.getPassword().equals(u.getPassword())) {
            return Optional.empty();
        }
        //Refresh User Token
        String oldToken = user.getUserToken();
        user.setUserToken(UUID.randomUUID().toString());
        if (updateUser(oldToken, user)) {
            return Optional.ofNullable(user.getUserToken());
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAllUsers() {
        List<JpaUser> jpaUserList = jpaApi.em()
                .createNamedQuery("JpaUser.findAll", JpaUser.class)
                .getResultList();
        List<User> userList = new ArrayList<>();
        for (JpaUser jpaUser : jpaUserList) {
            userList.add(UserMapper.jpaUserToUser(jpaUser));
        }
        return userList;
    }

    public boolean isAdmin(String userToken) {
        jpaApi.withTransaction(em -> {
            Query query = em.createNamedQuery("JpaUser.findByToken", JpaUser.class);
            query.setParameter("token", userToken);
            JpaUser u = (JpaUser) query.getSingleResult();
            if (u == null) {
                return false;
            }
            return u.getRole().equals(Role.ADMIN);
        });
        return false;
    }
}
