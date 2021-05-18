package com.certimeter.progetto.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "reports")
public class ReportDao {
	private String id;
	private List<String> idPath;
	private int amount;
	private Date date;
	private String note;
	private String user;
}
