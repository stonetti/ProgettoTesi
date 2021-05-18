package com.certimeter.progetto.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class SubActivity {

	String id = UUID.randomUUID().toString();
	String name;
	String description;
	List<String> users;
	Date expiringDate;
	List<SubActivity> sub_activities;
}
