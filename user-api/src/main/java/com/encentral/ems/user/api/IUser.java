package com.encentral.ems.user.api;




import com.encentral.ems.user.model.User;

import java.util.List;
import java.util.Optional;

public interface IUser {

    Optional<String> addUser(User user);

    boolean updateUser(String userToken, User user);

    Optional<User> getUser(String userId);

    boolean deleteUser(String userId);

    Optional<String> signUserIn(User user);

    List<User> getAllUsers();

    boolean isAdmin(String userToken);

}
