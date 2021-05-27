package com.certimeter.progetto.controller.report;

import com.certimeter.progetto.filters.MacroFilter;
import com.certimeter.progetto.model.Report;

import java.util.List;

public interface ReportControllerInterface {

    public List<Report> getList(MacroFilter param);

    public Report getReport(String reportId);

    public Report updateReport(Report report);

    public Report createReport(Report report);

    public void deleteReport(String reportId);

}
