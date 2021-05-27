package com.certimeter.progetto.repository;

import com.certimeter.progetto.dao.ActivityDao;
import com.certimeter.progetto.dao.MacroDao;
import com.certimeter.progetto.dao.UserDao;
import com.certimeter.progetto.dao.UserInfoDao;
import com.certimeter.progetto.filters.common.QueryParameter;
import com.certimeter.progetto.pojo.ActivityPojo;
import com.certimeter.progetto.pojo.MacroPojo;
import com.certimeter.progetto.pojo.UserInfoPojo;
import com.certimeter.progetto.pojo.UserPojo;
import com.certimeter.progetto.queries.MacroQueries;
import com.certimeter.progetto.utilities.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Function;

@Repository
public class MacroMapperRepository {
	
    @Autowired
    MacroQueries db;

    @Autowired
    MongoTemplate mongoTemplate;

    @PostConstruct
    static void init() {
        Function<MacroPojo, MacroDao> toDao = (pojo) -> {
            MacroDao macroDao = new MacroDao();
            macroDao.setId(pojo.getId());
            macroDao.setName(pojo.getName());
            macroDao.setActivities(Converter.convert(pojo.getActivities(), ActivityDao.class));
            macroDao.setDescription(pojo.getDescription());
            macroDao.setId(pojo.getId());
            macroDao.setName(pojo.getName());
            macroDao.setPm(Converter.convert(pojo.getPm(), UserDao.class));
            macroDao.setAssignedUsers(Converter.convert(pojo.getAssignedUsers(), UserInfoDao.class));
            macroDao.setSubAssignedUsers(Converter.convert(pojo.getSubAssignedUsers(), UserInfoDao.class));
            macroDao.setDateOfCreation(pojo.getDateOfCreation());
            macroDao.setExpiringDate(pojo.getExpiringDate());
            return macroDao;
        };
        Converter.put(MacroPojo.class, MacroDao.class, toDao);

        Function<MacroDao, MacroPojo> toPojo = (macro) -> {
            MacroPojo pojo = new MacroPojo();
            pojo.setId(macro.getId());
            pojo.setName(macro.getName());
            pojo.setActivities(Converter.convert(macro.getActivities(), ActivityPojo.class));
            pojo.setDescription(macro.getDescription());
            pojo.setId(macro.getId());
            pojo.setName(macro.getName());
            pojo.setPm(Converter.convert(macro.getPm(), UserPojo.class));
            pojo.setAssignedUsers(Converter.convert(macro.getAssignedUsers(), UserInfoPojo.class));
            pojo.setSubAssignedUsers(Converter.convert(macro.getSubAssignedUsers(), UserInfoPojo.class));
            pojo.setDateOfCreation(macro.getDateOfCreation());
            pojo.setExpiringDate(macro.getExpiringDate());
            return pojo;
        };

        Converter.put(MacroDao.class, MacroPojo.class, toPojo);

        Function<UserInfoPojo, UserInfoDao> pojoToDao = (pojo) -> {
            UserInfoDao userInfoDao = new UserInfoDao();
            userInfoDao.setId(pojo.getId());
            userInfoDao.setLastname(pojo.getLastname());
            userInfoDao.setName(pojo.getName());
            return userInfoDao;
        };
        Converter.put(UserInfoPojo.class, UserInfoDao.class, pojoToDao);

        Function<UserInfoDao, UserInfoPojo> daoToPojo = (userInfoDao) -> {
            UserInfoPojo pojo = new UserInfoPojo();
            pojo.setId(userInfoDao.getId());
            pojo.setLastname(userInfoDao.getLastname());
            pojo.setName(userInfoDao.getName());
            return pojo;
        };

        Converter.put(UserInfoDao.class, UserInfoPojo.class, daoToPojo);

        Function<ActivityPojo, ActivityDao> pojoToActDao = (pojo) -> {
            ActivityDao actDao = new ActivityDao();
            actDao.setDescription(pojo.getDescription());
            actDao.setExpiringDate(pojo.getExpiringDate());
            actDao.setId(pojo.getId());
            actDao.setName(pojo.getName());
            actDao.setSub_activities(pojo.getSub_activities());
            actDao.setUsers(pojo.getUsers());
            return actDao;
        };
        Converter.put(ActivityPojo.class, ActivityDao.class, pojoToActDao);

        Function<ActivityDao, ActivityPojo> actDaoToPojo = (actDao) -> {
            ActivityPojo pojo = new ActivityPojo();
            pojo.setDescription(actDao.getDescription());
            pojo.setExpiringDate(actDao.getExpiringDate());
            pojo.setId(actDao.getId());
            pojo.setName(actDao.getName());
            pojo.setSub_activities(actDao.getSub_activities());
            pojo.setUsers(actDao.getUsers());
            return pojo;
        };
        Converter.put(ActivityDao.class, ActivityPojo.class, actDaoToPojo);
    }

    public MacroPojo getMacro(String macroId) {
        return Converter.convert(db.findById(macroId).get(), MacroPojo.class);
    }

    public MacroPojo createMacro(MacroPojo macro) {
        MacroDao macrodao = Converter.convert(macro, MacroDao.class);
        return Converter.convert(db.save(macrodao), MacroPojo.class);
    }

    public MacroPojo updateMacro(MacroPojo macro) {
        MacroDao macrodao = Converter.convert(macro, MacroDao.class);
        return Converter.convert(db.save(macrodao), MacroPojo.class);
    }

    public void deleteMacro(String macroId) {
        db.deleteById(macroId);
    }

    public List<MacroPojo> getAll(List<QueryParameter> params) {
        Query query = new Query();
        for (QueryParameter param : params)
            query.addCriteria(Converter.toCriteria(param.getKey(), param.getOp(), param.getValue()));
        return Converter.convert(mongoTemplate.find(query, MacroDao.class), MacroPojo.class);
    }

}
