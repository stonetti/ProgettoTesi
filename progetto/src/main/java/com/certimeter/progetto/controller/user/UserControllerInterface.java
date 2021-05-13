package com.certimeter.progetto.controller.user;

import java.util.List;

import com.certimeter.progetto.model.User;
import com.certimeter.progetto.repository.dao.UserDao;

public interface UserControllerInterface {

	public UserDao getUser(String user_Id);

	public void deleteUser(String userId);

	public UserDao createUser(User user);

	public UserDao updateUser(User user);

	public List<UserDao> getAllUser();

	public List<UserDao> getAllUserByActivity(String activityId);

	public List<UserDao> getAllUserBySubActivity(String subActivityId);

	public List<UserDao> getAllUserByMacro(String macroId);
}
