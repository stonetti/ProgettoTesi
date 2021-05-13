package com.certimeter.progetto.repository.activity;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.certimeter.progetto.repository.dao.ActivityDao;

public interface ActivityQueries extends MongoRepository<ActivityDao, String> {

}
