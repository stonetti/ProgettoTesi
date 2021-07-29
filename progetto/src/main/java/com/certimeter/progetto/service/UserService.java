package com.certimeter.progetto.service;


import com.certimeter.progetto.enums.Role;
import com.certimeter.progetto.errorHandling.AuthorizationFailureException;
import com.certimeter.progetto.filters.UserFilter;
import com.certimeter.progetto.model.AccountDetails;
import com.certimeter.progetto.model.User;
import com.certimeter.progetto.persistence.UserQueries;
import com.certimeter.progetto.pojo.UserPojo;
import com.certimeter.progetto.repository.UserMapperRepository;
import com.certimeter.progetto.utilities.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserMapperRepository userMapperRepository;

    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserQueries userQueries;

    public List<User> getList(UserFilter param) throws AuthorizationFailureException {
        if (authorizationService.isAdmin()) {
            return Converter.convert(userMapperRepository.getList(param.toParam()), User.class);
        } else if (authorizationService.isPm()) {
            String pm = jwtService.getUserFromToken().getId();
            return Converter.convert(userMapperRepository.getListByPm(pm, param.toParam()), User.class);
        } else
            throw new AuthorizationFailureException();
    }

    public User getUser(String userId) throws AuthorizationFailureException {
        return Converter.convert(userMapperRepository.getUser(userId), User.class);
    }


    public void deleteUser(String userId) throws AuthorizationFailureException {
        if (authorizationService.isAdmin())
            userMapperRepository.deleteUser(userId);
        else
            throw new AuthorizationFailureException();
    }

    public User createUser(User user) throws AuthorizationFailureException {
        if (authorizationService.isAdmin()) {
            UserPojo userpojo = Converter.convert(user, UserPojo.class);
            return Converter.convert(userMapperRepository.createUser(userpojo), User.class);
        } else
            throw new AuthorizationFailureException();
    }

    public User updateUser(User updatedUser, boolean passwordChanged) throws AuthorizationFailureException {
        UserPojo userpojo;
        if (authorizationService.isAdmin()) {
            userpojo = Converter.convert(updatedUser, UserPojo.class);
            return Converter.convert(userMapperRepository.updateUser(userpojo, passwordChanged), User.class);
        } else {
            final User userFromToken = jwtService.getUserFromToken();
            if (userFromToken.getId().equals(updatedUser.getId())) {
                userFromToken.setAccDetails(updatedUser.getAccDetails());
                userpojo = Converter.convert(userFromToken, UserPojo.class);
                return Converter.convert(userMapperRepository.updateUser(userpojo, passwordChanged), User.class);
            } else
                throw new AuthorizationFailureException();
        }
    }


    public Map<String, Object> userLogin(AccountDetails login) throws Exception {
        if (login.getUsername() == null || login.getPassword() == null)
            throw new ServletException("Invalid username or password!");
        UserPojo userpojo = Converter.convert(userQueries.findByUsername(login.getUsername()), UserPojo.class);
        User user = Converter.convert(userpojo, User.class);
        if (user == null)
            throw new ServletException("User not found!");
        if (!jwtService.passwordCheck(user, login))
            throw new ServletException("Invalid password!");
        return jwtService.setTokenMap(user, user.getDefaultRole());
    }

    public Map<String, Object> switchRole(Role role) {
        User user = jwtService.getUserFromToken();
        if (user.getRoles().contains(role)) {
            setDefaultRole(role);
            return jwtService.setTokenMap(user, role);
        } else
            return jwtService.setTokenMap(user, user.getDefaultRole());
    }

    public void setDefaultRole(Role role) {
        User user = jwtService.getUserFromToken();
        user.setDefaultRole(role);
        UserPojo userpojo = Converter.convert(user, UserPojo.class);
        Converter.convert(userMapperRepository.updateUser(userpojo, false), User.class);
    }
}

