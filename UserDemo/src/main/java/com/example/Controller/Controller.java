package com.example.Controller;


import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Repository.UserRepository;
import com.example.UserDemo.UserPojo;
import com.example.model.UserModel;
import com.example.utilities.Converter;

@RestController
@Validated
public class Controller {
	
	@PostConstruct
	static void init() {
		Function<UserPojo, UserModel> toModel = (pojo)->{
			UserModel model= new UserModel();
			model.setId(pojo.getId());
			model.setName(pojo.getName());
			model.setLastname(pojo.getLastname());
			model.setAge(pojo.getAge());
			model.setAddress(pojo.getAddress());
			model.setUsername(pojo.getUsername());
			model.setEmail(pojo.getEmail());
			model.setPassword(pojo.getPassword());
			model.setGender(pojo.getGender());
			model.setProfilePic(pojo.getProfilePic());
			model.setRegistrationDate(pojo.getRegistrationDate());
			model.setLastLogin(pojo.getLastLogin());
			model.setFriends(pojo.getFriends());
			model.setPosts(pojo.getPosts());
			model.setPublicProfile(pojo.isPublicProfile());
			return model;
		};
		Converter.put(UserPojo.class, UserModel.class, toModel);
	}

	@Autowired
	UserRepository repo;
	

	@GetMapping("/user/{Id}")
	public UserModel getUserById(@PathVariable Integer Id) {
		return Converter.convert(repo.getUser(Id), UserModel.class);
	}

	@DeleteMapping("/user/{Id}")
	public boolean deleteUser(@PathVariable Integer Id) {
		return repo.deleteUser(Id);
	}

	@PostMapping("/user/new")
	public UserModel createUser(@Valid @RequestBody UserPojo user) {
		return Converter.convert(repo.createUser(user), UserModel.class);
	}
}

