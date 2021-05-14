package com.certimeter.progetto.repository.subActivity;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.certimeter.progetto.repository.dao.SubActivityDao;

public interface SubActivityQueries extends MongoRepository<SubActivityDao, String> {

}
