package com.certimeter.progetto.queries;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.certimeter.progetto.dao.UserDao;
import com.certimeter.progetto.model.User;

public interface UserQueries extends MongoRepository<UserDao, String> {

	@Query("{ 'accDetails.username' : ?0, 'accDetails.password' : ?1}")
	User findByAccountDetails(String uName, String pwd);
}