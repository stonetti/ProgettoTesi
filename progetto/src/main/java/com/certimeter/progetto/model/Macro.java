
package com.certimeter.progetto.model;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Macro {

	private String id;
	private String name;
	private String description;
	private Date dateOfCreation;
	private Date expiringDate;
	private List<Activity> activities;
	private List<User> pm;
	private List<UserInfo> assignedUsers;
	private List<UserInfo> subAssignedUsers;
}