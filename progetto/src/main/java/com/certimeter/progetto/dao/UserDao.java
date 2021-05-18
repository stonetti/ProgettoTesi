package com.certimeter.progetto.dao;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.certimeter.progetto.model.AccountDetails;

import lombok.Data;

@Data
@Document(collection = "users")
public class UserDao {
	private String id;
	private String name;
	private String lastname;
	private List<Integer> businessUnits;
	private List<String> macro;
	private String email;
	private AccountDetails accDetails;

}
