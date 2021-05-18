
package com.certimeter.progetto.queries;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.certimeter.progetto.dao.MacroDao;

public interface MacroQueries extends MongoRepository<MacroDao, String> {

}