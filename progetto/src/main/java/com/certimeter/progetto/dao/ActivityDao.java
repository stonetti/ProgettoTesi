package com.certimeter.progetto.dao;

import java.time.LocalDate;
import java.util.List;

import com.certimeter.progetto.model.SubActivity;
import com.certimeter.progetto.model.UserInfo;

import lombok.Data;

@Data
public class ActivityDao {
	String id;
	String name;
	String description;
	List<UserInfo> users;
	LocalDate expiringDate;
	List<SubActivity> sub_activities;
}
