package com.certimeter.progetto.model;

import java.util.List;

import lombok.Data;

@Data
public class ProjectManager {
	String id;
	String name;
	List<Integer> businessUnits;
	List<String> macro;
	String email;
	AccountDetails accDetails;

}
