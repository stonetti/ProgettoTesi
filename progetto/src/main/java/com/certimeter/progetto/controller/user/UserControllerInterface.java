package com.certimeter.progetto.controller.user;

import com.certimeter.progetto.filters.MacroFilter;
import com.certimeter.progetto.model.User;

import java.util.List;

public interface UserControllerInterface {
    public User createUser(User user);

    public User updateUser(User user);

    public void deleteUser(String userId);

    public User getUser(String userId);

    public List<User> getList(MacroFilter param);

}