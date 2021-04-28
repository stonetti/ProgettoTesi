package com.example.UserDemo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MapperDemo {

	/* Operazioni base */
	
	@Result(property = "id", column = "idusers")
	@Select("SELECT idusers, name, lastname, age from users WHERE idusers = #{id}")
	User selectUser(int id);

	@Insert("INSERT into users(name,lastname,username,email,password,registrationDate, lastLogin, publicProfile) "
			+ "VALUES(#{name}, #{lastname},#{username},#{email},#{password},#{registrationDate}, #{lastLogin}, #{publicProfile})")
	void insertUser(User user);

	@Delete("DELETE from users where idusers = #{id}")
	boolean deleteUser(int id);
	
	
	 @Select("SELECT count(username) FROM users where username =  #{username}")
	 boolean usernameExist(String username);

	/* Operazioni che richiedono join */
	 
	@Select("Select p.content, p.date, u.name, u.lastname from posts p join users u on "
			+ "p.id_owner = u.idusers where u.idusers = #{id}")
	List<Map<String, String>> showPosts(User user);

	@Select("Select distinct u2.name, u2.lastname from friendships f join users u on"
			+ "(f.usr1 = u.idusers or f.usr2 = u.idusers) join users u2 on"
			+ "(u2.idusers = f.usr1 or u2.idusers = f.usr2) where u.idusers = #{id} and u.idusers <> u2.idusers")
	List<Map<String, String>> showFriends(User user);

	@Delete("Delete from friendships f \r\n"
			+ "where f.usr1 = #{usr1.id}  and f.usr2 = #{usr2.id} or f.usr1 = #{usr2.id} and f.usr2 = #{usr1.id}")
	boolean deleteFriend(User usr1, User usr2);


}

