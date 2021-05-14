
package com.certimeter.progetto.repository.macro;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.certimeter.progetto.repository.dao.MacroDao;

public interface MacroQueries extends MongoRepository<MacroDao, String> {

}