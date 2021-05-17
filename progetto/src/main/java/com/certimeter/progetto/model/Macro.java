
package com.certimeter.progetto.model;

import java.util.List;

import lombok.Data;

@Data
public class Macro {

	private String id;
	private String name;
	private String descrption;
	private int yearOfCreation;
	private List<Activity> activities;
	private List<User> pm;
	private List<MacroUsers> users;
}