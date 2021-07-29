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
@CrossOrigin
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
    public User createUser(@RequestBody User user) throws AuthorizationFailureException {
        return userService.createUser(user);
    }

    @Override
    @PutMapping("/{passwordChanged}")
    public User updateUser(@RequestBody User user, @PathVariable boolean passwordChanged) throws AuthorizationFailureException {
        return userService.updateUser(user, passwordChanged);
    }

    @Override
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) throws AuthorizationFailureException {
        userService.deleteUser(userId);
    }

    @Override
    @GetMapping("/{userId}")
    public User getUser(@PathVariable String userId) throws AuthorizationFailureException {
        return userService.getUser(userId);
    }

    @Override
    @PostMapping("/list")
    public List<User> getList(@RequestBody UserFilter param) throws AuthorizationFailureException {//TODO:implementare filtri report
        return userService.getList(param);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody AccountDetails login) throws Exception {
        return userService.userLogin(login);
    }

    @PostMapping("/auth/refresh-token")
    public Map<String, Object> refreshToken(@RequestBody String refreshToken) {
        jwtService.checkTokenAndInitSession(refreshToken);
        User user = jwtService.getUserFromToken();
        return jwtService.setTokenMap(user, jwtService.getRole());
    }

    @GetMapping("/switch-role/{role}")
    public Map<String, Object> switchRole(@PathVariable Role role) {
        System.out.println("Ruolo: " + role);
        return userService.switchRole(role);
    }

    @GetMapping("/set-default-role/{role}")
    public void setDefaultRole(@PathVariable Role role) {
        userService.setDefaultRole(role);
    }
}