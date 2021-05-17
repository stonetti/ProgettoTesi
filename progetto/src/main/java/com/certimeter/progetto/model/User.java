
package com.certimeter.progetto.model;

import java.util.List;

import lombok.Data;

@Data
public class User {
	private String id;
	private String name;
	private String lastname;
	private List<Integer> businessUnits;
	private List<String> macro;
	private String email;
	private AccountDetails accDetails;
}