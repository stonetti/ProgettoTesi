package com.example.Controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.UserDemo.MapperDemo;
import com.example.UserDemo.User;

@RestController
@Validated
public class Controller {

	@Autowired
	MapperDemo mapperdemo;
	

	@GetMapping("/user/{Id}")
	public User getPersonWithId(@PathVariable Integer Id) {
		return mapperdemo.selectUser(Id);
	}

	@DeleteMapping("/user/{Id}")
	public boolean deleteUser(@PathVariable Integer Id) {
		return mapperdemo.deleteUser(Id);
	}

	@PostMapping("/user/new")
	public void createUser(@Valid @RequestBody User user) {
		mapperdemo.insertUser(user);
	}
}

