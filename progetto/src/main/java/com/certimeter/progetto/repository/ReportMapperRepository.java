package com.certimeter.progetto.repository;

import com.certimeter.progetto.dao.ReportDao;
import com.certimeter.progetto.errorHandling.AuthorizationFailureException;
import com.certimeter.progetto.filters.common.QueryParameter;
import com.certimeter.progetto.model.HoursSum;
import com.certimeter.progetto.persistence.ReportQueries;
import com.certimeter.progetto.pojo.ReportPojo;
import com.certimeter.progetto.utilities.Converter;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.mongodb.client.model.Sorts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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
            report.setPm(pojo.getPm());
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
            pojo.setPm(report.getPm());
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

    public ReportPojo getReportById(String reportId) {
        return Converter.convert(db.findById(reportId).get(), ReportPojo.class);
    }

    public List<ReportPojo> getList(List<QueryParameter> params) {
        Query query = new Query();
        for (QueryParameter param : params)
            query.addCriteria(Converter.toCriteria(param.getKey(), param.getOp(), param.getValue()));
        return Converter.convert(mongoTemplate.find(query, ReportDao.class), ReportPojo.class);
    }

    public List<ReportPojo> getListByPm(String pm, List<QueryParameter> params) {
        Query query = new Query();
        for (QueryParameter param : params)
            query.addCriteria(Converter.toCriteria(param.getKey(), param.getOp(), param.getValue()));
        query.addCriteria(Criteria.where("pm").is(pm));
        return Converter.convert(mongoTemplate.find(query, ReportDao.class), ReportPojo.class);
    }

    public List<ReportPojo> getListByUser(String user, List<QueryParameter> params) {
        Query query = new Query();
        for (QueryParameter param : params)
            query.addCriteria(Converter.toCriteria(param.getKey(), param.getOp(), param.getValue()));
        query.addCriteria(Criteria.where("user").is(user));
        return Converter.convert(mongoTemplate.find(query, ReportDao.class), ReportPojo.class);
    }

    public ReportPojo getReportByIdAndPm(String reportId, String pm) throws AuthorizationFailureException {
        ReportDao reportResult = db.findByIdAndPm(reportId, pm).orElseThrow(AuthorizationFailureException::new);
        return Converter.convert(reportResult, ReportPojo.class);
    }

    public List<ReportPojo> getReportByMacroId(String macroId) {
        List<ReportDao> reportResult = db.findByMacroId(macroId);
        return Converter.convert(reportResult, ReportPojo.class);
    }

    public List<HoursSum> workingMinutesAmount(String macroId, String userId) {
        MatchOperation matchStageMacro = Aggregation.match(new Criteria("idPath").is(macroId));
        MatchOperation matchStageUser = Aggregation.match(new Criteria("user").is(userId));
        GroupOperation groupByDate = Aggregation.group("month('date')")
                .sum("amount").as("totalAmount");
        Aggregation aggregation = Aggregation.newAggregation(matchStageMacro, matchStageUser, groupByDate);
        AggregationResults<HoursSum> output = mongoTemplate.aggregate(aggregation, "reports", HoursSum.class);
        return output.getMappedResults();
    }


    public List<HoursSum> totalMacroAmount(String macroId, String from, String to) {
        MatchOperation matchStageMacro = Aggregation.match(new Criteria("idPath").is(macroId));
        MatchOperation matchStageDate = Aggregation.match(new Criteria("date").gte(from).lte(to));
        GroupOperation groupByDate = Aggregation.group("date")
                .sum("amount").as("totalAmount");
        SortOperation sortReports = Aggregation.sort(Sort.Direction.ASC, "_id");
        Aggregation aggregation = Aggregation.newAggregation(matchStageMacro, matchStageDate, groupByDate, sortReports);
        AggregationResults<HoursSum> output = mongoTemplate.aggregate(aggregation, "reports", HoursSum.class);
        return output.getMappedResults();
    }
}
