package com.certimeter.progetto.controller.user;

import com.certimeter.progetto.enums.Roles;
import com.certimeter.progetto.filters.MacroFilter;
import com.certimeter.progetto.model.AccountDetails;
import com.certimeter.progetto.model.User;
import com.certimeter.progetto.pojo.UserPojo;
import com.certimeter.progetto.queries.UserQueries;
import com.certimeter.progetto.repository.UserMapperRepository;
import com.certimeter.progetto.service.JwtService;
import com.certimeter.progetto.utilities.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/users")
public class UserController implements UserControllerInterface {
    @Autowired
    UserMapperRepository repo;

    @Autowired
    UserQueries db;

    @Autowired
    JwtService jwt;

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
    public User createUser(@RequestBody User user) {
        UserPojo userpojo = Converter.convert(user, UserPojo.class);
        return Converter.convert(repo.createUser(userpojo), User.class);
    }

    @Override
    @PutMapping("/")
    public User updateUser(@RequestBody User user) {
        UserPojo userpojo = Converter.convert(user, UserPojo.class);
        return Converter.convert(repo.updateUser(userpojo), User.class);
    }

    @Override
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        repo.deleteUser(userId);
    }

    @Override
    @GetMapping("/{userId}")
    public User getUser(@PathVariable String userId) {
        return Converter.convert(repo.getUser(userId), User.class);
    }

    @Override
    @PostMapping("/list")
    public List<User> getList(@RequestBody MacroFilter param) {//TODO:implementare filtri user e report
        return Converter.convert(repo.getList(param.toParam()), User.class);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody AccountDetails login) throws ServletException {

        if (login.getUsername() == null || login.getPassword() == null)
            throw new ServletException("Invalid username or password!");

        User user = db.findByUsername(login.getUsername());

        if (user == null)
            throw new ServletException("User not found!");
        if (!jwt.passwordCheck(user, login))
            throw new ServletException("Invalid password!");
        return jwt.setTokenMap(user, user.getRoles().get(0));

    }

    //con getTokenClaims verifico la consistenza della signature del token e recupero il role corrente
    @PostMapping("/auth/refresh-token")
    public Map<String, Object> refreshToken(@RequestBody String refreshToken) {
        Roles role = jwt.getTokenClaims(refreshToken).getBody().get("Role", Roles.class);
        User user = jwt.getUserFromToken(refreshToken);
        return jwt.setTokenMap(user, role);
    }

    @GetMapping("/switch-role/{role}")
    public Map<String, Object> switchRole(@PathVariable Roles role, @RequestHeader(name = "Authorization") String token) {
        token = token.substring(7);
        User user = jwt.getUserFromToken(token);
        if (user.getRoles().contains(role))
            return jwt.setTokenMap(user, role);
        else
            return jwt.setTokenMap(user, user.getRoles().get(0));
        //TODO: settare il defualt role come nuovo elemento del db a cui accedere facilmente
    }
    /*TODO: importante!!! modificare tutti i metodi per accedere a risorse con gli id: questi dovranno prima essere decodificati
     */

}