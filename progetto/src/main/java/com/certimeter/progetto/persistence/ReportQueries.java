package com.certimeter.progetto.persistence;

import com.certimeter.progetto.dao.ReportDao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReportQueries extends MongoRepository<ReportDao, String> {

    Optional<ReportDao> findByIdAndPm(String reportId, String pm);
    
    @Query("{ 'idPath.0' : ?0}")
    List<ReportDao> findByMacroId(String macroId);
}

