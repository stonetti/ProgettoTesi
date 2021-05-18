package com.certimeter.progetto.queries;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.certimeter.progetto.dao.ReportDao;

public interface ReportQueries extends MongoRepository<ReportDao, String> {

}
