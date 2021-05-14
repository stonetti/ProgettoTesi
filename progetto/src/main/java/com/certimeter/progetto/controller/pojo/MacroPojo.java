package com.certimeter.progetto.controller.pojo;

import java.util.List;

import com.certimeter.progetto.model.Activity;
import com.certimeter.progetto.model.User;

import lombok.Data;

@Data
public class MacroPojo {

	String id;
	String name;
	String descrption;
	int yearOfCreation;
	List<Activity> activities;
	List<User> pm;
	List<String> users;
}
