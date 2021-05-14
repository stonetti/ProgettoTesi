package com.certimeter.progetto.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class SubActivity {

	String id;
	String name;
	String description;
	List<String> users;
	Date expiringDate;
	Activity parentActivity;
	List<SubActivity> sub_activities;
}