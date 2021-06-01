package com.certimeter.progetto.service;


import com.certimeter.progetto.enums.Role;
import com.certimeter.progetto.errorHandling.AuthorizationFailureException;
import com.certimeter.progetto.filters.UserFilter;
import com.certimeter.progetto.model.AccountDetails;
import com.certimeter.progetto.model.User;
import com.certimeter.progetto.pojo.UserPojo;
import com.certimeter.progetto.queries.UserQueries;
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

    public List<User> getList(UserFilter param, String token) throws AuthorizationFailureException {
        if (authorizationService.isAdmin(token))
            return Converter.convert(userMapperRepository.getList(param.toParam()), User.class);
        else if (authorizationService.isPm(token)) {
            String pm = jwtService.getUserFromToken(token).getId();
            return Converter.convert(userMapperRepository.getListByPm(pm, param.toParam()), User.class);
        } else
            throw new AuthorizationFailureException();
    }

    public User getUser(String userId, String token) throws AuthorizationFailureException {
        if (authorizationService.isAdmin(token))
            return Converter.convert(userMapperRepository.getUser(userId), User.class);
        else {
            final String id = jwtService.getUserFromToken(token).getId();
            if (authorizationService.isPm(token)) {
                String pm = id;
                return Converter.convert(userMapperRepository.getUserByPm(pm, userId), User.class);
            } else if (id == userId)
                return Converter.convert(userMapperRepository.getUser(userId), User.class);
            else
                throw new AuthorizationFailureException();
        }
    }


    public void deleteUser(String userId, String token) throws AuthorizationFailureException {
        if (authorizationService.isAdmin(token))
            userMapperRepository.deleteUser(userId);
        else
            throw new AuthorizationFailureException();
    }

    public User createUser(User user, String token) throws AuthorizationFailureException {
        if (authorizationService.isAdmin(token)) {
            UserPojo userpojo = Converter.convert(user, UserPojo.class);
            return Converter.convert(userMapperRepository.createUser(userpojo), User.class);
        } else
            throw new AuthorizationFailureException();
    }

    public User updateUser(User updatedUser, String token) throws AuthorizationFailureException {
        UserPojo userpojo;
        if (authorizationService.isAdmin(token)) {
            userpojo = Converter.convert(updatedUser, UserPojo.class);
            return Converter.convert(userMapperRepository.updateUser(userpojo), User.class);
        } else {
            final User userFromToken = jwtService.getUserFromToken(token);
            if (userFromToken.getId() == updatedUser.getId()) {
                userFromToken.setAccDetails(updatedUser.getAccDetails());
                userpojo = Converter.convert(userFromToken, UserPojo.class);
                return Converter.convert(userMapperRepository.updateUser(userpojo), User.class);
            } else
                throw new AuthorizationFailureException();
        }
    }


    public Map<String, Object> userLogin(AccountDetails login) throws Exception {
        if (login.getUsername() == null || login.getPassword() == null)
            throw new ServletException("Invalid username or password!");
        User user = Converter.convert(userQueries.findByUsername(login.getUsername()), User.class);
        if (user == null)
            throw new ServletException("User not found!");
        if (!jwtService.passwordCheck(user, login))
            throw new ServletException("Invalid password!");
        return jwtService.setTokenMap(user, user.getDefaultRole());
    }

    public Map<String, Object> switchRole(Role role, String token) {
        User user = jwtService.getUserFromToken(token);
        if (user.getRoles().contains(role))
            return jwtService.setTokenMap(user, role);
        else
            return jwtService.setTokenMap(user, user.getDefaultRole());
    }

    public void setDefaultRole(Role role, String token) {
        User user = jwtService.getUserFromToken(token);
        user.setDefaultRole(role);
        UserPojo userpojo = Converter.convert(user, UserPojo.class);
        Converter.convert(userMapperRepository.updateUser(userpojo), User.class);
    }
}

