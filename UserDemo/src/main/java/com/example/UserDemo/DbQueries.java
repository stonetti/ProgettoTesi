package com.example.UserDemo;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

import com.example.Repository.UserDao;

@Mapper
public interface DbQueries {

	
	@Result(property = "id", column = "idusers")
	@Select("SELECT idusers, name, lastname, age from users WHERE idusers = #{id}")
	UserDao getUser(int id);

	@Insert("INSERT into users(name,lastname,username,email,password,registrationDate, lastLogin, publicProfile) "
			+ "VALUES(#{name}, #{lastname},#{username},#{email},#{password},#{registrationDate}, #{lastLogin}, #{publicProfile})")
	void createUser(UserDao user);

	@Delete("DELETE from users where idusers = #{id}")
	boolean deleteUser(int id);
	
	@Select("SELECT count(username) FROM users where username =  #{username}")
	boolean usernameExist(String username);

	@Select("Select p.content, p.date from posts p join users u on "
			+ "p.id_owner = u.idusers where u.idusers = #{id}")
	List<Post> getAllPosts(int id);

	@Select("Select distinct u2.name, u2.lastname from friendships f join users u on"
			+ "(f.usr1 = u.idusers or f.usr2 = u.idusers) join users u2 on"
			+ "(u2.idusers = f.usr1 or u2.idusers = f.usr2) where u.idusers = #{id} and u.idusers <> u2.idusers")
	List<Friendship> getAllFriends(UserDao user);

	@Delete("Delete from friendships f \r\n"
			+ "where f.usr1 = #{usr1.id}  and f.usr2 = #{usr2.id} or f.usr1 = #{usr2.id} and f.usr2 = #{usr1.id}")
	boolean deleteFriend(UserDao usr1, UserDao usr2);

	@Select("select * from users")
	List<UserDao> getAll();
	
	@Result(property = "id", column = "idusers")
	@Select("SELECT * from users WHERE username = #{username}")
	UserDao getUserByUsername(String username);
	
}

