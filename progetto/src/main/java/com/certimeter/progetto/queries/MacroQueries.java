package com.certimeter.progetto.queries;

import com.certimeter.progetto.dao.MacroDao;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MacroQueries extends MongoRepository<MacroDao, String> {
//
//    getAssignedUsersFromMacroId
//    getSubAssignedUserFromMacroId
//    getSubAssignedUsersFromActivityId
}