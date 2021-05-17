
package com.certimeter.progetto.controller.user;

import java.util.List;

import com.certimeter.progetto.model.User;

public interface UserControllerInterface {
	public User createUser(User user);

	public User updateUser(User user);

	public void deleteUser(String userId);

	public User getUser(String userId);

	public List<User> getList();

}