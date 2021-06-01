package com.certimeter.progetto.repository;

import com.certimeter.progetto.dao.UserDao;
import com.certimeter.progetto.filters.common.QueryParameter;
import com.certimeter.progetto.pojo.UserPojo;
import com.certimeter.progetto.queries.UserQueries;
import com.certimeter.progetto.utilities.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.function.Function;

@Repository
public class UserMapperRepository {

    @Autowired
    MongoTemplate mongotTemplate;

    @Autowired
    UserQueries userQueries;

    @PostConstruct
    static void init() {
        Function<UserPojo, UserDao> toDao = (pojo) -> {
            UserDao dao = new UserDao();
            dao.setId(pojo.getId());
            dao.setName(pojo.getName());
            dao.setLastname(pojo.getLastname());
            dao.setEmail(pojo.getEmail());
            dao.setAccDetails(pojo.getAccDetails());
            dao.setBusinessUnits(pojo.getBusinessUnits());
            dao.setMacro(pojo.getMacro());
            dao.setRoles(pojo.getRoles());
            dao.setDefaultRole(pojo.getDefaultRole());
            return dao;
        };

        Converter.put(UserPojo.class, UserDao.class, toDao);

        Function<UserDao, UserPojo> toPojo = (dao) -> {
            UserPojo pojo = new UserPojo();
            pojo.setId(dao.getId());
            pojo.setName(dao.getName());
            pojo.setLastname(dao.getLastname());
            pojo.setAccDetails(dao.getAccDetails());
            pojo.setEmail(dao.getEmail());
            pojo.setBusinessUnits(dao.getBusinessUnits());
            pojo.setMacro(dao.getMacro());
            pojo.setRoles(dao.getRoles());
            pojo.setDefaultRole(dao.getDefaultRole());
            return pojo;
        };

        Converter.put(UserDao.class, UserPojo.class, toPojo);
    }


    public UserPojo createUser(UserPojo user) {
        String pwd = user.getAccDetails().getPassword();
        String hashedPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
        user.getAccDetails().setPassword(hashedPwd);
        UserDao userdao = Converter.convert(user, UserDao.class);
        return Converter.convert(userQueries.save(userdao), UserPojo.class);
    }

    public UserPojo updateUser(UserPojo user) {
        String pwd = user.getAccDetails().getPassword();
        String hashedPwd = BCrypt.hashpw(pwd, BCrypt.gensalt());
        user.getAccDetails().setPassword(hashedPwd);
        UserDao userdao = Converter.convert(user, UserDao.class);
        return Converter.convert((userQueries.save(userdao)), UserPojo.class);
    }

    public void deleteUser(String userId) {
        userQueries.deleteById(userId);
    }

    public UserPojo getUser(String userId) {
        return Converter.convert(userQueries.findById(userId).get(), UserPojo.class);
    }

    public List<UserPojo> getList(List<QueryParameter> queryParameters) {
        return Converter.convert(userQueries.findAll(), UserPojo.class);
    }

    public List<UserPojo> getListByPm(String pm, List<QueryParameter> toParam) {
        return null; //TODO: Converter.convert(db.findAll(), UserPojo.class);
    }

    public UserPojo getUserByPm(String pm, String userId) {
        return Converter.convert(userQueries.getUserByPm(pm, userId), UserPojo.class);
    }

}
