package com.certimeter.progetto.repository.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.certimeter.progetto.repository.dao.UserDao;

public interface UserQueries extends MongoRepository<UserDao, String> {

}