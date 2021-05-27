package com.certimeter.progetto.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "macro")
public class MacroDao {
	private String id;
	private String name;
	private String description;
	private LocalDate dateOfCreation;
	private LocalDate expiringDate;
	private List<ActivityDao> activities;
	private List<UserDao> pm;
	private List<UserInfoDao> assignedUsers;
	private List<UserInfoDao> subAssignedUsers;
}
