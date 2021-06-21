package com.certimeter.progetto.dao;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "reports")
public class ReportDao {
    private String id;
    private List<String> idPath;
    private int amount;
    private LocalDate date;
    private String note;
    private String user;
    private List<String> pm;
}
