package com.certimeter.progetto.persistence;

import com.certimeter.progetto.dao.UserDao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserQueries extends MongoRepository<UserDao, String> {

    @Query("{ 'accDetails.username' : ?0}")
    UserDao findByUsername(String uName);

    @Query("aiut!")
    UserDao getUserByPm(String pm, String userId);
}