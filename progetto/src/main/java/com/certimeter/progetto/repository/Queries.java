package com.certimeter.progetto.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface Queries<T> extends MongoRepository<T, String> {

}
