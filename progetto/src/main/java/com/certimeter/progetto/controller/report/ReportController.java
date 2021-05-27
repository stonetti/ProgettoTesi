package com.certimeter.progetto.controller.report;

import com.certimeter.progetto.filters.MacroFilter;
import com.certimeter.progetto.model.Report;
import com.certimeter.progetto.pojo.ReportPojo;
import com.certimeter.progetto.repository.ReportMapperRepository;
import com.certimeter.progetto.utilities.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/reports")
public class ReportController implements ReportControllerInterface {

    @Autowired
    ReportMapperRepository repo;

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
            return pojo;
        };

        Converter.put(Report.class, ReportPojo.class, toPojo);
    }

    @PostMapping("/list")
    public List<Report> getList(@RequestBody MacroFilter param) {
        return Converter.convert(repo.getList(param.toParam()), Report.class);
    }

    @Override
    @GetMapping("/{reportId}")
    public Report getReport(@PathVariable String reportId) {
        return Converter.convert(repo.getReport(reportId), Report.class);
    }

    @Override
    @PostMapping("/")
    public Report createReport(@RequestBody Report report) {
        ReportPojo reportpojo = Converter.convert(report, ReportPojo.class);
        return Converter.convert(repo.createReport(reportpojo), Report.class);
    }

    @Override
    @PutMapping("/")
    public Report updateReport(@RequestBody Report report) {
        ReportPojo reportpojo = Converter.convert(report, ReportPojo.class);
        return Converter.convert(repo.updateReport(reportpojo), Report.class);
    }

    @Override
    @DeleteMapping("/{reportId}")
    public void deleteReport(@PathVariable String reportId) {
        repo.deleteReport(reportId);
    }
}
