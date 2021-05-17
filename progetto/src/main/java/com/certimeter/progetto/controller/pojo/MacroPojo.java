
package com.certimeter.progetto.controller.pojo;

import java.util.List;

import com.certimeter.progetto.model.Activity;
import com.certimeter.progetto.model.MacroUsers;
import com.certimeter.progetto.model.User;

import lombok.Data;

@Data
public class MacroPojo {

	private String id;
	private String name;
	private String descrption;
	private int yearOfCreation;
	private List<Activity> activities;
	private List<User> pm;
	private List<MacroUsers> users;
}
