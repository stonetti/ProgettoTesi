package com.certimeter.progetto.controller.user;

import com.certimeter.progetto.enums.Role;
import com.certimeter.progetto.errorHandling.AuthorizationFailureException;
import com.certimeter.progetto.filters.UserFilter;
import com.certimeter.progetto.model.AccountDetails;
import com.certimeter.progetto.model.User;
import com.certimeter.progetto.pojo.UserPojo;
import com.certimeter.progetto.service.JwtService;
import com.certimeter.progetto.service.UserService;
import com.certimeter.progetto.utilities.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/users")
public class UserController implements UserControllerInterface {

    @Autowired
    JwtService jwtService;

    @Autowired
    UserService userService;


    @PostConstruct
    static void init() {
        Function<UserPojo, User> toModel = (pojo) -> {
            User user = User.builder().build();
            user.setId(pojo.getId());
            user.setName(pojo.getName());
            user.setLastname(pojo.getLastname());
            user.setEmail(pojo.getEmail());
            user.setAccDetails(pojo.getAccDetails());
            user.setBusinessUnits(pojo.getBusinessUnits());
            user.setMacro(pojo.getMacro());
            user.setRoles(pojo.getRoles());
            user.setDefaultRole(pojo.getDefaultRole());
            return user;
        };
        Converter.put(UserPojo.class, User.class, toModel);

        Function<User, UserPojo> toPojo = (model) -> {
            UserPojo pojo = new UserPojo();
            pojo.setId(model.getId());
            pojo.setName(model.getName());
            pojo.setLastname(model.getLastname());
            pojo.setAccDetails(model.getAccDetails());
            pojo.setEmail(model.getEmail());
            pojo.setBusinessUnits(model.getBusinessUnits());
            pojo.setMacro(model.getMacro());
            pojo.setRoles(model.getRoles());
            pojo.setDefaultRole(model.getDefaultRole());
            return pojo;
        };

        Converter.put(User.class, UserPojo.class, toPojo);
    }

    @Override
    @PostMapping("/")
    public User createUser(@RequestBody User user, @RequestHeader(name = "Authorization") String token) throws AuthorizationFailureException {
        return userService.createUser(user, token);
    }

    @Override
    @PutMapping("/")
    public User updateUser(@RequestBody User user, @RequestHeader(name = "Authorization") String token) throws AuthorizationFailureException {
        return userService.updateUser(user, token);
    }

    @Override
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId, @RequestHeader(name = "Authorization") String token) throws AuthorizationFailureException {
        userService.deleteUser(userId, token);
    }

    @Override
    @GetMapping("/{userId}")
    public User getUser(@PathVariable String userId, @RequestHeader(name = "Authorization") String token) throws AuthorizationFailureException {
        return userService.getUser(userId, token);
    }

    @Override
    @PostMapping("/list")
    public List<User> getList(@RequestBody UserFilter param, @RequestHeader(name = "Authorization") String token) throws AuthorizationFailureException {//TODO:implementare filtri report
        return userService.getList(param, token);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody AccountDetails login) throws Exception {
        return userService.userLogin(login);
    }

    //con getTokenClaims verifico la consistenza della signature del token e recupero il role corrente
    @PostMapping("/auth/refresh-token")
    public Map<String, Object> refreshToken(@RequestBody String refreshToken) {
        Role role = jwtService.getTokenClaims(refreshToken).getBody().get("Role", Role.class);
        User user = jwtService.getUserFromToken(refreshToken);
        return jwtService.setTokenMap(user, role);
    }

    @GetMapping("/switch-role/{role}")
    public Map<String, Object> switchRole(@PathVariable Role role, @RequestHeader(name = "Authorization") String token) {
        return userService.switchRole(role, token);
    }

    @GetMapping("/set-default-role/{role}")
    public void setDefaultRole(@PathVariable Role role, @RequestHeader(name = "Authorization") String token) {
        userService.setDefaultRole(role, token);
    }

}