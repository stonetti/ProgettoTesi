package com.certimeter.progetto.controller.report;

import java.util.List;

import com.certimeter.progetto.model.Report;

public interface ReportControllerInterface {

	public List<Report> getList(String path);

	public Report getReport(String reportId);

	public Report updateReport(Report report);

	public Report createReport(Report report);

	public void deleteReport(String reportId);

}
