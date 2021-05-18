package com.certimeter.progetto.repository;

import java.util.List;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.certimeter.progetto.dao.UserDao;
import com.certimeter.progetto.pojo.UserPojo;
import com.certimeter.progetto.queries.UserQueries;
import com.certimeter.progetto.utilities.Converter;

@Repository
public class UserMapperRepository {

	@Autowired
	MongoTemplate mongotTemplate;

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
			return pojo;
		};

		Converter.put(UserDao.class, UserPojo.class, toPojo);
	}

	@Autowired
	UserQueries db;

	public UserPojo createUser(UserPojo user) {
		UserDao userdao = Converter.convert(user, UserDao.class);
		return Converter.convert(db.save(userdao), UserPojo.class);
	}

	public UserPojo updateUser(UserPojo user) {
		UserDao userdao = Converter.convert(user, UserDao.class);
		return Converter.convert((db.save(userdao)), UserPojo.class);
	}

	public void deleteUser(String userId) {
		db.deleteById(userId);
	}

	public UserPojo getUser(String userId) {
		return Converter.convert(db.findById(userId).get(), UserPojo.class);
	}

	public List<UserPojo> getList() {
		return Converter.convert(db.findAll(), UserPojo.class);
	}

}
