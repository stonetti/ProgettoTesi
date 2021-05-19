package com.certimeter.progetto.pojo;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class ReportPojo {
	private String id;
	private List<String> idPath;
	private int amount;
	private LocalDate date;
	private String note;
	private String user;
}
