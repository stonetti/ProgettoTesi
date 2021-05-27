package com.certimeter.progetto.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Activity {
	String id = UUID.randomUUID().toString();
	String name;
	String description;
	List<UserInfo> users;
	LocalDate expiringDate;
	List<SubActivity> sub_activities;
}