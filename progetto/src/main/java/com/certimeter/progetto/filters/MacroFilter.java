package com.certimeter.progetto.filters;

import java.util.List;

import com.certimeter.progetto.filters.common.QueryParamConverter;
import com.certimeter.progetto.filters.common.RequestParameter;
import com.certimeter.progetto.model.User;
import com.certimeter.progetto.model.UserInfo;

import lombok.Data;

@Data
public class MacroFilter implements QueryParamConverter {
	RequestParameter<Integer> id;
	RequestParameter<String> name;
	RequestParameter<Integer> yearOfCreation;
	RequestParameter<List<User>> pm;
	RequestParameter<List<UserInfo>> users;
}
