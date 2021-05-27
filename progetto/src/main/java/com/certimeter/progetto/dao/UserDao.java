package com.certimeter.progetto.dao;

import com.certimeter.progetto.enums.Roles;
import com.certimeter.progetto.model.AccountDetails;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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
    private List<Roles> roles;
    private Roles defaultRole;
}
