package com.certimeter.progetto.controller.report;

import com.certimeter.progetto.errorHandling.AuthorizationFailureException;
import com.certimeter.progetto.filters.ReportFilter;
import com.certimeter.progetto.model.Report;

import java.util.List;

public interface ReportControllerInterface {

    public List<Report> getList(ReportFilter param, String jwt) throws AuthorizationFailureException;

    public Report getReport(String reportId, String jwt) throws AuthorizationFailureException;

    public Report updateReport(Report report, String jwt) throws AuthorizationFailureException;

    public Report createReport(Report report, String jwt) throws AuthorizationFailureException;

    public void deleteReport(String reportId, String jwt) throws AuthorizationFailureException;

}
