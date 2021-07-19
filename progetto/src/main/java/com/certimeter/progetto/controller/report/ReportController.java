package com.certimeter.progetto.controller.report;

import com.certimeter.progetto.errorHandling.AuthorizationFailureException;
import com.certimeter.progetto.filters.ReportFilter;
import com.certimeter.progetto.model.HoursSum;
import com.certimeter.progetto.model.Report;
import com.certimeter.progetto.pojo.ReportPojo;
import com.certimeter.progetto.service.ReportService;
import com.certimeter.progetto.utilities.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/reports")
@CrossOrigin
public class ReportController implements ReportControllerInterface {

    @Autowired
    ReportService reportService;

    @PostConstruct
    static void init() {
        Function<ReportPojo, Report> toModel = (pojo) -> {
            Report report = Report.builder().build();
            report.setId(pojo.getId());
            report.setAmount(pojo.getAmount());
            report.setIdPath(pojo.getIdPath());
            report.setNote(pojo.getNote());
            report.setUser(pojo.getUser());
            report.setDate(pojo.getDate());
            report.setPm(pojo.getPm());
            return report;
        };
        Converter.put(ReportPojo.class, Report.class, toModel);

        Function<Report, ReportPojo> toPojo = (report) -> {
            ReportPojo pojo = new ReportPojo();
            pojo.setId(report.getId());
            pojo.setAmount(report.getAmount());
            pojo.setIdPath(report.getIdPath());
            pojo.setNote(report.getNote());
            pojo.setDate(report.getDate());
            pojo.setUser(report.getUser());
            pojo.setPm(report.getPm());
            return pojo;
        };

        Converter.put(Report.class, ReportPojo.class, toPojo);
    }

    @Override
    @PostMapping("/list")
    public List<Report> getList(@RequestBody ReportFilter param, @RequestHeader(name = "Authorization") String token) throws AuthorizationFailureException {
        return reportService.getList(param, token.substring(7));
    }

    @Override
    @GetMapping("/{reportId}")
    public Report getReport(@PathVariable String reportId, @RequestHeader(name = "Authorization") String token) throws AuthorizationFailureException {
        return reportService.getReport(reportId, token.substring(7));
    }

    @Override
    @PostMapping("/")
    public Report createReport(@RequestBody Report report, @RequestHeader(name = "Authorization") String token) throws AuthorizationFailureException {
        return reportService.createReport(report, token.substring(7));
    }

    @Override
    @PutMapping("/")
    public Report updateReport(@RequestBody Report report, @RequestHeader(name = "Authorization") String token) throws AuthorizationFailureException {
        return reportService.updateReport(report, token.substring(7));

    }

    @Override
    @DeleteMapping("/{reportId}")
    public void deleteReport(@PathVariable String reportId, @RequestHeader(name = "Authorization") String token) throws AuthorizationFailureException {
        reportService.deleteReport(reportId, token.substring(7));
    }

    @GetMapping("/total_amount/{macroId}/{userId}")
    public List<HoursSum> workingMinutesAmount(@PathVariable String macroId, @PathVariable String userId, @RequestHeader(name = "Authorization") String token) throws AuthorizationFailureException {
        return reportService.workingMinutesAmount(macroId, userId, token.substring(7));
    }

    @GetMapping("/total_macro_amount/{macroId}/{from}/{to}")
    public List<HoursSum> totalMacroAmount(@PathVariable String macroId, @PathVariable Date from, @PathVariable Date to, @RequestHeader(name = "Authorization") String token) throws AuthorizationFailureException {
        return reportService.totalMacroAmount(macroId, from, to, token.substring(7));
    }
}
