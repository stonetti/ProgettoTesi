package com.certimeter.progetto.service;

import com.certimeter.progetto.errorHandling.AuthorizationFailureException;
import com.certimeter.progetto.filters.ReportFilter;
import com.certimeter.progetto.model.HoursSum;
import com.certimeter.progetto.model.Report;
import com.certimeter.progetto.model.User;
import com.certimeter.progetto.pojo.ReportPojo;
import com.certimeter.progetto.repository.ReportMapperRepository;
import com.certimeter.progetto.utilities.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    ReportMapperRepository reportMapperRepository;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    JwtService jwtService;

    public List<Report> getList(ReportFilter param, String token) throws AuthorizationFailureException {

        if (authorizationService.isAdmin(token))
            return Converter.convert(reportMapperRepository.getList(param.toParam()), Report.class);
        else if (authorizationService.isPm(token)) {
            String pm = jwtService.getUserFromToken(token).getId();
            return Converter.convert(reportMapperRepository.getListByPm(pm, param.toParam()), Report.class);
        } else if (authorizationService.isUser(token)) {
            String user = jwtService.getUserFromToken(token).getId();
            return Converter.convert(reportMapperRepository.getListByUser(user, param.toParam()), Report.class);
        } else
            throw new AuthorizationFailureException();
    }


    public Report getReport(String reportId, String token) throws AuthorizationFailureException {
        if (authorizationService.isAdmin(token))
            return Converter.convert(reportMapperRepository.getReportById(reportId), Report.class);
        else if (authorizationService.isPm(token))
            return Converter.convert(reportMapperRepository.getReportByIdAndPm(reportId, jwtService.getUserFromToken(token).getId()), Report.class);
        else if (authorizationService.isUser(token)) {
            Report report = Converter.convert(reportMapperRepository.getReportById(reportId), Report.class);
            User user = jwtService.getUserFromToken(token);
            if (report.getUser().equals(user.getId()))
                return report;
            else
                throw new AuthorizationFailureException();
        } else
            throw new AuthorizationFailureException();
    }

    public Report createReport(Report report, String token) throws AuthorizationFailureException {
        if (authorizationService.isUser(token) || authorizationService.isAdmin(token)) {
            ReportPojo reportpojo = Converter.convert(report, ReportPojo.class);
            if (authorizationService.isUser(token))
                reportpojo.setUser(jwtService.getUserFromToken(token).getId());
            return Converter.convert(reportMapperRepository.createReport(reportpojo), Report.class);
        } else
            throw new AuthorizationFailureException();
    }

    public Report updateReport(Report report, String token) throws AuthorizationFailureException {
        User user = jwtService.getUserFromToken(token);
        if (authorizationService.isUser(token) && report.getUser().equals(user.getId()) || authorizationService.isAdmin(token)) {
            ReportPojo reportpojo = Converter.convert(report, ReportPojo.class);
            return Converter.convert(reportMapperRepository.updateReport(reportpojo), Report.class);
        } else
            throw new AuthorizationFailureException();
    }

    public void deleteReport(String reportId, String token) throws AuthorizationFailureException {
        Report report = Converter.convert(reportMapperRepository.getReportById(reportId), Report.class);
        if (authorizationService.isAdmin(token)) {
            reportMapperRepository.deleteReport(report.getId());
        } else if (authorizationService.isUser(token)) {
            User user = jwtService.getUserFromToken(token);
            if (report.getUser().equals(user.getId())) {
                reportMapperRepository.deleteReport(report.getId());
            } else
                throw new AuthorizationFailureException();
        } else
            throw new AuthorizationFailureException();
    }

    public List<HoursSum> workingMinutesAmount(String macroId, String userId, String token) throws AuthorizationFailureException {
        if (authorizationService.isAdmin(token)) {
            return reportMapperRepository.workingMinutesAmount(macroId, userId);
        } else if (authorizationService.isPm(token))//DEBUG
            throw new AuthorizationFailureException();//TODO
        else if (authorizationService.isUser(token))
            throw new AuthorizationFailureException();
        else
            throw new AuthorizationFailureException();
    }

    public List<HoursSum> totalMacroAmount(String macroId, Date from, Date to, String token) throws AuthorizationFailureException {
        System.out.println(macroId);
        if (authorizationService.isAdmin(token)) {
            return reportMapperRepository.totalMacroAmount(macroId, from, to);
        } else if (authorizationService.isPm(token))
            return reportMapperRepository.totalMacroAmount(macroId, from, to);
//            throw new AuthorizationFailureException();//TODO
        else if (authorizationService.isUser(token))
            throw new AuthorizationFailureException();
        else
            throw new AuthorizationFailureException();
    }
}
