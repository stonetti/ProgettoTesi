package com.certimeter.progetto;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.certimeter.progetto.controller.macro.MacroController;
import com.certimeter.progetto.controller.report.ReportController;
import com.certimeter.progetto.controller.user.UserController;
import com.certimeter.progetto.model.Activity;
import com.certimeter.progetto.model.Macro;
import com.certimeter.progetto.model.Report;
import com.certimeter.progetto.model.SubActivity;
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
	ReportController reportController;

	@Autowired
	MacroController macroController;

	@Test
	void contextLoads() {
	}

	@Test
	void userInsertTest() {
		// AccountDetails accDet = new AccountDetails();
		// accDet.setPassword("Lprova");
		// accDet.setUsername("Lory");
		//
		// AccountDetails accDet2 = new AccountDetails();
		// accDet2.setPassword("Allyprova");
		// accDet2.setUsername("Alo23165");

		// List<Integer> bUnits = new ArrayList<>();
		// bUnits.add(3);
		// bUnits.add(2);
		// User user1 = User.builder().accDetails(accDet).businessUnits(bUnits).email("asdo@wee.ij").lastname("Pillow").macro(null).name("Alligan").build();
		// User user = User.builder().accDetails(accDet2).businessUnits(bUnits).email("wery@wee.ij").lastname("Monday").macro(null).name("Ustru").build();
		//
		// userController.createUser(user1);
		// userController.createUser(user);
		//
		// User user = userController.getUser("60a3df6647078350fc925bd3");
		// User user1 = userController.getUser("60a4cfd8be852b0cc5d980c4");
		// User user2 = userController.getUser("60a4e0a54d762e232e1e9eaf");
		//
		// UserInfo userInfo = createUserInfo(user1);
		// UserInfo userInfo2 = createUserInfo(user);
		// UserInfo userInfo3 = createUserInfo(user2);
		//
		// List<UserInfo> userInfoList = new ArrayList<>();
		// userInfoList.add(userInfo);
		// userInfoList.add(userInfo2);
		// userInfoList.add(userInfo3);
		//
		// List<User> userList = new ArrayList<>();
		// userList.add(user1);
		//
		// Activity activity = createActivity(userInfo);
		//
		// Macro macro = createMacro(userList, userInfoList, activity);
		// List<String> macroList = new ArrayList<>();
		// macroList.add(macro.getId());
		// user1.setMacro(macroList);
		// user.setMacro(macroList);
		// // createReport(macro);
		createReport(macroController.getMacro("60a4eb0442f6845c9165f764"));

	}

	private void createReport(Macro macro) {
		List<String> idPath = new ArrayList<>();
		idPath.add("60a4eb0442f6845c9165f764");
		idPath.add("db2c8f17-2944-41a0-8498-b25babf5c701");
		idPath.add("55cc8589-fa47-42ab-9807-508b72b837ca");

		String user = macro.getAssignedUsers().get(1).getId();
		Report report = Report.builder().idPath(idPath).date(LocalDate.now()).amount(200).note("report").user(user).build();
		reportController.createReport(report);
	}

	private Macro createMacro(List<User> userList, List<UserInfo> userInfoList, Activity activity) {

		List<Activity> activityList = new ArrayList<>();
		activityList.add(activity);
		userInfoList.remove(1);
		Macro macro = Macro.builder().assignedUsers(userInfoList).activities(activityList).activities(activityList).dateOfCreation(LocalDate.now()).description("Macro di prova 3")
				.expiringDate(LocalDate.of(2027, 02, 28)).name("MacroTre").pm(userList).subAssignedUsers(userInfoList).build();

		return macroController.createMacro(macro);
	}

	private UserInfo createUserInfo(User user1) {
		UserInfo userinfo = new UserInfo();
		userinfo.setLastname(user1.getLastname());
		userinfo.setName(user1.getName());
		userinfo.setId(user1.getId());
		return userinfo;
	}

	private Activity createActivity(UserInfo userInfo) {
		List<UserInfo> userInfoList = new ArrayList<>();
		userInfoList.add(userInfo);

		List<String> userIdList = new ArrayList<>();
		userIdList.add(userInfo.getId());
		List<SubActivity> subList = new ArrayList<>();
		subList.add(createSubActivity(userIdList));
		subList.add(createSubActivity(userIdList));

		SubActivity subAct = createSubActivity(userIdList);
		List<SubActivity> subListLvl2 = new ArrayList<>();
		subListLvl2.add(createSubActivity(userIdList));
		subAct.setSub_activities(subListLvl2);
		subList.add(subAct);

		LocalDate date = LocalDate.of(2021, Month.APRIL, 17);
		Activity activity = Activity.builder().id(UUID.randomUUID().toString()).description("Attività 3 di prova").name("Attività3").expiringDate(date).users(userInfoList).sub_activities(subList)
				.build();
		return activity;
	}

	private SubActivity createSubActivity(List<String> userIdList) {
		SubActivity subAct = SubActivity.builder().id(UUID.randomUUID().toString()).description("Sottoattività di prova").expiringDate(LocalDate.of(2025, 01, 14)).name("sottoattività")
				.sub_activities(null).users(userIdList).build();
		return subAct;
	}
}
