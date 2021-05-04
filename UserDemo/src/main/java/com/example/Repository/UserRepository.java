package com.example.Repository;

import java.util.List;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.UserDemo.DbQueries;
import com.example.UserDemo.Friendship;
import com.example.UserDemo.Post;
import com.example.UserDemo.UserPojo;
import com.example.utilities.Converter;


@Repository
public class UserRepository {
	
//	public static void main(String[] args) {
//		init();
//		UserPojo user = new UserPojo();
//		user.setId(1526448);
//		user.setName("Name");
//		user.setAge(15);
//		user.setLastname("Lastname");
//		UserDao userDao = Converter.convert(user, UserDao.class);
//		System.out.println(userDao.getId());
//	}
	
	@PostConstruct
	static void init() {
		Function<UserPojo, UserDao> toDao = (pojo)->{
			UserDao dao= new UserDao();
			dao.setId(pojo.getId());
			dao.setName(pojo.getName());
			dao.setLastname(pojo.getLastname());
			dao.setAge(pojo.getAge());
			dao.setAddress(pojo.getAddress());
			dao.setUsername(pojo.getUsername());
			dao.setEmail(pojo.getEmail());
			dao.setPassword(pojo.getPassword());
			dao.setGender(pojo.getGender());
			dao.setProfilePic(pojo.getProfilePic());
			dao.setRegistrationDate(pojo.getRegistrationDate());
			dao.setLastLogin(pojo.getLastLogin());
			dao.setFriends(pojo.getFriends());
			dao.setPosts(pojo.getPosts());
			dao.setPublicProfile(pojo.isPublicProfile());
			return dao;
		};
		Converter.put(UserPojo.class, UserDao.class, toDao);
		
		Function<UserDao, UserPojo> toPojo = (dao)->{
			UserPojo pojo= new UserPojo();
			pojo.setId(dao.getId());
			pojo.setName(dao.getName());
			pojo.setLastname(dao.getLastname());
			pojo.setAge(dao.getAge());
			pojo.setAddress(dao.getAddress());
			pojo.setUsername(dao.getUsername());
			pojo.setEmail(dao.getEmail());
			pojo.setPassword(dao.getPassword());
			pojo.setGender(dao.getGender());
			pojo.setProfilePic(dao.getProfilePic());
			pojo.setRegistrationDate(dao.getRegistrationDate());
			pojo.setLastLogin(dao.getLastLogin());
			pojo.setFriends(dao.getFriends());
			pojo.setPosts(dao.getPosts());
			pojo.setPublicProfile(dao.isPublicProfile());
			return pojo;
		};
		
		Converter.put(UserDao.class, UserPojo.class, toDao);
	}

	@Autowired 
	DbQueries db;
		
	public UserPojo getUser(int id) {
		return Converter.convert(db.getUser(id), UserPojo.class);
	}
	

	public UserPojo createUser(UserPojo user) {
		UserDao userdao = Converter.convert(user, UserDao.class);
		db.createUser(userdao);
		return Converter.convert(db.getUserByUsername(user.getUsername()), UserPojo.class);
	}

	public boolean deleteUser(int id){
		return db.deleteUser(id);
	}
	
	public boolean usernameExist(String username){
		return db.usernameExist(username);
	}
	 
	public List<Post> getAllPosts(UserPojo user){
		 UserDao userdao = Converter.convert(user, UserDao.class);
		int id = userdao.getId();
		return db.getAllPosts(id);
	}

	public List<Friendship> getAllFriends(UserPojo user){
		UserDao userdao = Converter.convert(user, UserDao.class);
		return db.getAllFriends(userdao);
		
	}
	
	public boolean deleteFriend(UserPojo usr1, UserPojo usr2){
		UserDao userdao1 = Converter.convert(usr1, UserDao.class);
		UserDao userdao2 = Converter.convert(usr2, UserDao.class);
		return db.deleteFriend(userdao1, userdao2);
	}

	public List<UserPojo> getAll(){
		return Converter.convert(db.getAll(), Converter.functions.get(UserDao.class).get(UserPojo.class));
	}
	
}
