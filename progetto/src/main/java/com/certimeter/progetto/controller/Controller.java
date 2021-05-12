package com.certimeter.progetto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.certimeter.progetto.model.User;
import com.certimeter.progetto.repository.CrudRepository;

public class Controller {

	@Autowired
	CrudRepository repo;

	@GetMapping("/{Id}")
	public void getUser(@PathVariable String Id) {
	}

	@DeleteMapping("/{Id}")
	public void deleteUser(@PathVariable String Id) {
	}

	@PostMapping("")
	public void createUser(@RequestBody User user) {
	}

	@PutMapping("/{id}")
	public void updateUser(@RequestBody User user) {
	}

	@PostMapping("/list")
	public void getAll(@RequestBody User user) {
	}

}
