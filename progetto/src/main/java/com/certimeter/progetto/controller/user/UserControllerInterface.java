package com.certimeter.progetto.controller.user;

import com.certimeter.progetto.errorHandling.AuthorizationFailureException;
import com.certimeter.progetto.filters.UserFilter;
import com.certimeter.progetto.model.User;

import java.util.List;

public interface UserControllerInterface {
    public User createUser(User user) throws AuthorizationFailureException;

    public User updateUser(User user, boolean passwordChanged) throws AuthorizationFailureException;

    public void deleteUser(String userId) throws AuthorizationFailureException;

    public User getUser(String userId) throws AuthorizationFailureException;

    public List<User> getList(UserFilter param) throws AuthorizationFailureException;

}