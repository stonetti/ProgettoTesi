package com.certimeter.progetto.model;

import com.certimeter.progetto.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class User {
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