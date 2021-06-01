package com.certimeter.progetto.repository;

import com.certimeter.progetto.dao.ReportDao;
import com.certimeter.progetto.filters.common.QueryParameter;
import com.certimeter.progetto.model.Report;
import com.certimeter.progetto.pojo.ReportPojo;
import com.certimeter.progetto.queries.ReportQueries;
import com.certimeter.progetto.utilities.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Function;

@Repository
public class ReportMapperRepository {

    @Autowired
    ReportQueries db;

    @Autowired
    MongoTemplate mongoTemplate;


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

    public List<ReportPojo> getList(List<QueryParameter> params) {
        Query query = new Query();
        for (QueryParameter param : params)
            query.addCriteria(Converter.toCriteria(param.getKey(), param.getOp(), param.getValue()));
        return Converter.convert(mongoTemplate.find(query, ReportDao.class), ReportPojo.class);
    }

    public List<ReportPojo> getListByPm(String pm, List<QueryParameter> toParam) {
        return null; //TODO: Converter.convert();
    }

    public List<ReportPojo> getListByUser(String user, List<QueryParameter> toParam) {
        return null; //TODO: Converter.convert();
    }

    public Report getReportByPm(String reportId) {
        return null;//TODO: Converter.convert();
    }
}
