package com.certimeter.progetto.pojo;

import java.util.List;

import com.certimeter.progetto.model.AccountDetails;

import lombok.Data;

@Data
public class UserPojo {
	private String id;
	private String name;
	private String lastname;
	private List<Integer> businessUnits;
	private List<String> macro;
	private String email;
	private AccountDetails accDetails;
}
