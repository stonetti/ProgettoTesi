package com.certimeter.progetto.pojo;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ReportPojo {
    private String id;
    private List<String> idPath;
    private int amount;
    private String date;
    private String note;
    private String user;
    private List<String> pm;
}
