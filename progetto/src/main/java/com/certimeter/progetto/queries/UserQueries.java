package com.certimeter.progetto.queries;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.certimeter.progetto.dao.UserDao;

public interface UserQueries extends MongoRepository<UserDao, String> {

}