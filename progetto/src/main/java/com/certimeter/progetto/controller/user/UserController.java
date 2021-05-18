
package com.certimeter.progetto.controller.user;

import java.util.List;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.certimeter.progetto.model.User;
import com.certimeter.progetto.pojo.UserPojo;
import com.certimeter.progetto.repository.UserMapperRepository;
import com.certimeter.progetto.utilities.Converter;

@RestController
@RequestMapping("/users")
public class UserController implements UserControllerInterface {

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
			return pojo;
		};

		Converter.put(User.class, UserPojo.class, toPojo);
	}

	@Autowired
	UserMapperRepository repo;

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
	@GetMapping("/list")
	public List<User> getList() {
		return Converter.convert(repo.getList(), User.class);
	}

}
