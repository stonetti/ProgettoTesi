package com.certimeter.progetto.controller.user;

import java.util.List;

import com.certimeter.progetto.model.User;
import com.certimeter.progetto.repository.dao.UserDao;

public class UserController implements UserControllerInterface {

	@Override
	public UserDao getUser(String user_Id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public UserDao createUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDao updateUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDao> getAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDao> getAllUserByActivity(String activityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDao> getAllUserBySubActivity(String subActivityId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDao> getAllUserByMacro(String macroId) {
		// TODO Auto-generated method stub
		return null;
	}

}
