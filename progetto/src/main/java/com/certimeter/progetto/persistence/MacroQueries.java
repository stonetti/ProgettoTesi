package com.certimeter.progetto.persistence;

import com.certimeter.progetto.dao.MacroDao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MacroQueries extends MongoRepository<MacroDao, String> {

    Optional<MacroDao> findByIdAndPm(String macroId, String pm);
}