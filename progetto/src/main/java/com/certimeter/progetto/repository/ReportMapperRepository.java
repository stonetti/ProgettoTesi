package com.certimeter.progetto.repository;

import java.util.List;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.certimeter.progetto.dao.ReportDao;
import com.certimeter.progetto.pojo.ReportPojo;
import com.certimeter.progetto.queries.ReportQueries;
import com.certimeter.progetto.utilities.Converter;

@Repository
public class ReportMapperRepository {
	@Autowired
	ReportQueries db;

	@PostConstruct
	static void init() {
		Function<ReportPojo, ReportDao> toDao = (pojo) -> {
			ReportDao report = new ReportDao();
			report.setId(pojo.getId());
			report.setAmount(pojo.getAmount());
			report.setIdPath(pojo.getIdPath());
			report.setNote(pojo.getNote());
			report.setUser(pojo.getUser());
			report.setDate(pojo.getDate());
			return report;
		};
		Converter.put(ReportPojo.class, ReportDao.class, toDao);

		Function<ReportDao, ReportPojo> toPojo = (report) -> {
			ReportPojo pojo = new ReportPojo();
			pojo.setId(report.getId());
			pojo.setAmount(report.getAmount());
			pojo.setIdPath(report.getIdPath());
			pojo.setNote(report.getNote());
			pojo.setDate(report.getDate());
			pojo.setUser(report.getUser());
			return pojo;
		};

		Converter.put(ReportDao.class, ReportPojo.class, toPojo);
	}

	public ReportPojo createReport(ReportPojo report) {
		ReportDao reportdao = Converter.convert(report, ReportDao.class);
		return Converter.convert(db.save(reportdao), ReportPojo.class);
	}

	public ReportPojo updateReport(ReportPojo report) {
		ReportDao reportdao = Converter.convert(report, ReportDao.class);
		return Converter.convert((db.save(reportdao)), ReportPojo.class);
	}

	public void deleteReport(String reportId) {
		db.deleteById(reportId);
	}

	public ReportPojo getReport(String reportId) {
		return Converter.convert(db.findById(reportId).get(), ReportPojo.class);
	}

	public List<ReportPojo> getList() {
		return Converter.convert(db.findAll(), ReportPojo.class);
	}
}
