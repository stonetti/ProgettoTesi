package com.certimeter.progetto.repository.dao;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.certimeter.progetto.model.Activity;
import com.certimeter.progetto.model.User;

import lombok.Data;

@Document(collection = "macro")
@Data
public class MacroDao {

	String id;
	String name;
	String descrption;
	int yearOfCreation;
	List<Activity> activities;
	List<User> pm;
	List<String> users;

}