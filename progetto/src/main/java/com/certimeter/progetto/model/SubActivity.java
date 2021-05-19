package com.certimeter.progetto.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubActivity {

	String id = UUID.randomUUID().toString();
	String name;
	String description;
	List<String> users;
	LocalDate expiringDate;
	List<SubActivity> sub_activities;
}
