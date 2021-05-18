package com.certimeter.progetto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.certimeter.progetto.controller.macro.MacroController;
import com.certimeter.progetto.controller.user.UserController;
import com.certimeter.progetto.model.AccountDetails;
import com.certimeter.progetto.model.Macro;
import com.certimeter.progetto.model.User;
import com.certimeter.progetto.model.UserInfo;
import com.certimeter.progetto.repository.UserMapperRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
class ProgettoApplicationTests {

	@Autowired
	UserMapperRepository repo;

	@Autowired
	UserController userController;

	@Autowired
	MacroController macroController;

	@Test
	void contextLoads() {
	}

	@Test
	void userInsertTest() {
		AccountDetails accDet = new AccountDetails();
		accDet.setPassword("provaFFF");
		accDet.setUsername("Fiorello");

		AccountDetails accDet2 = new AccountDetails();
		accDet2.setPassword("provaNNN");
		accDet2.setUsername("Nicholas");

		List<Integer> bUnits = new ArrayList<>();
		bUnits.add(3);
		User user1 = User.builder().accDetails(accDet).businessUnits(new ArrayList<>()).email("fiore@wee.ij").lastname("Cognome").macro(null).name("Fiorello").build();
		User user = User.builder().accDetails(accDet2).businessUnits(bUnits).email("Nicho@wee.ij").lastname("CogNicho").macro(null).name("Nicholas").build();
		UserInfo userinfo = new UserInfo();
		userinfo.setLastname(user1.getLastname());
		userinfo.setName(user1.getName());
		userinfo.setId(user1.getId());
		UserInfo userinfo2 = new UserInfo();
		userinfo2.setLastname(user.getLastname());
		userinfo2.setName(user.getName());
		userinfo2.setId(user.getId());

		userController.createUser(user1);
		List<UserInfo> userInfoList = new ArrayList<>();
		userInfoList.add(userinfo);
		userInfoList.add(userinfo2);

		List<User> userList = new ArrayList<>();
		userList.add(user1);

		Macro macro = Macro.builder().assignedUsers(userInfoList).activities(null).dateOfCreation(new Date()).description("Macro di prova 1").expiringDate(new Date(2021 - 06 - 18)).name("MacroUno")
				.pm(userList).subAssignedUsers(userInfoList).build();

		macroController.createMacro(macro);

		// TODO: Inserire nella map del converter le classi UserInfo, UserInfoDao, UserInfoPojo
	}
}
