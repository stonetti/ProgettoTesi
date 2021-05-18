
package com.certimeter.progetto.pojo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MacroPojo {

	private String id;
	private String name;
	private String description;
	private Date dateOfCreation;
	private Date expiringDate;
	private List<ActivityPojo> activities;
	private List<UserPojo> pm;
	private List<UserInfoPojo> assignedUsers;
	private List<UserInfoPojo> subAssignedUsers;

}
