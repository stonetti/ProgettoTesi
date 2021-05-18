package com.certimeter.progetto.pojo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ReportPojo {
	private String id;
	private List<String> idPath;
	private int amount;
	private Date date;
	private String note;
	private String user;
}
