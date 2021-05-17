package com.certimeter.progetto.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Report {
	private String id;
	private List<String> idPath;
	private int amount;
	private Date date;
	private String note;
	private List<String> users;
}
