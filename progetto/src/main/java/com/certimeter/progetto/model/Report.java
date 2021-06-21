package com.certimeter.progetto.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Report {
    private String id;
    private List<String> idPath;
    private int amount;
    private LocalDate date;
    private String note;
    private String user;
    private List<String> pm;
}
