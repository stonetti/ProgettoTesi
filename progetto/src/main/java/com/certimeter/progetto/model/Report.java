package com.certimeter.progetto.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Report {
	private String id;
	private List<String> idPath;
	private int amount;
	private LocalDate date;
	private String note;
	private String user;
}
