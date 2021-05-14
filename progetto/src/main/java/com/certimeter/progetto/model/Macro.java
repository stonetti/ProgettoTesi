
package com.certimeter.progetto.model;

import java.util.List;

import lombok.Data;

@Data
public class Macro {

	String id;
	String name;
	String descrption;
	int yearOfCreation;
	List<Activity> activities;
	List<User> pm;
	List<String> users;
}