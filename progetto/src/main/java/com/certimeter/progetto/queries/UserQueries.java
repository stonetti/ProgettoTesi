package com.certimeter.progetto.queries;

import com.certimeter.progetto.dao.UserDao;
import com.certimeter.progetto.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserQueries extends MongoRepository<UserDao, String> {

    @Query("{ 'accDetails.username' : ?0}")
    User findByUsername(String uName);
}