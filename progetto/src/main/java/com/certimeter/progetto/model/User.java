package com.certimeter.progetto.model;

import java.util.List;

import lombok.Data;

@Data
public class User {
	String id;
	String name;
	List<Integer> businessUnits;
	List<String> macro;
	List<String> activities;
	String email;
	AccountDetails accDetails;
}
