package com.certimeter.progetto.pojo;

import com.certimeter.progetto.enums.Role;
import com.certimeter.progetto.model.AccountDetails;
import lombok.Data;

import java.util.List;

@Data
public class UserPojo {
    private String id;
    private String name;
    private String lastname;
    private List<Integer> businessUnits;
    private List<String> macro;
    private String email;
    private AccountDetails accDetails;
    private List<Role> roles;
    private Role defaultRole;
}
