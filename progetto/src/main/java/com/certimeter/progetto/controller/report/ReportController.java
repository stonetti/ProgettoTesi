package com.certimeter.progetto.controller.report;

import java.util.List;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.certimeter.progetto.model.Report;
import com.certimeter.progetto.pojo.ReportPojo;
import com.certimeter.progetto.repository.ReportMapperRepository;
import com.certimeter.progetto.utilities.Converter;

@RestController
@RequestMapping("/reports")
public class ReportController implements ReportControllerInterface {

	@Autowired
	ReportMapperRepository repo;

	@PostConstruct
	static void init() {
		Function<ReportPojo, Report> toModel = (pojo) -> {
			Report report = new Report();
			report.setId(pojo.getId());
			report.setAmount(pojo.getAmount());
			report.setIdPath(pojo.getIdPath());
			report.setNote(pojo.getNote());
			report.setUser(pojo.getUser());
			return report;
		};
		Converter.put(ReportPojo.class, Report.class, toModel);

		Function<Report, ReportPojo> toPojo = (report) -> {
			ReportPojo pojo = new ReportPojo();
			pojo.setId(report.getId());
			pojo.setAmount(report.getAmount());
			pojo.setIdPath(report.getIdPath());
			pojo.setNote(report.getNote());
			pojo.setUser(report.getUser());
			return pojo;
		};

		Converter.put(Report.class, ReportPojo.class, toPojo);
	}

	@Override
	@GetMapping("/list")
	public List<Report> getList() {
		return Converter.convert(repo.getList(), Report.class);
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
