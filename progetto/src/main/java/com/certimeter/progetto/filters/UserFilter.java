package com.certimeter.progetto.filters;

import com.certimeter.progetto.enums.Role;
import com.certimeter.progetto.filters.common.QueryParamConverter;
import com.certimeter.progetto.filters.common.RequestParameter;
import com.certimeter.progetto.model.AccountDetails;
import lombok.Data;

import java.util.List;

@Data
public class UserFilter implements QueryParamConverter {
    RequestParameter<String> id;
    RequestParameter<String> name;
    RequestParameter<String> lastname;
    RequestParameter<Integer> businessUnits;
    RequestParameter<List<String>> macro;
    RequestParameter<String> email;
    RequestParameter<AccountDetails> accDetails;
    RequestParameter<List<Role>> roles;
    RequestParameter<Role> defaultRole;
}
