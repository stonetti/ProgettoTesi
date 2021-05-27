
package com.certimeter.progetto.pojo;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class MacroPojo {

	private String id;
	private String name;
	private String description;
	private LocalDate dateOfCreation;
	private LocalDate expiringDate;
	private List<ActivityPojo> activities;
	private List<UserPojo> pm;
	private List<UserInfoPojo> assignedUsers;
	private List<UserInfoPojo> subAssignedUsers;

}
