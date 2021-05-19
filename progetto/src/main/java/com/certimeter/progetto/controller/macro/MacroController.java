
package com.certimeter.progetto.controller.macro;

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

import com.certimeter.progetto.filters.MacroFilter;
import com.certimeter.progetto.model.Activity;
import com.certimeter.progetto.model.Macro;
import com.certimeter.progetto.model.User;
import com.certimeter.progetto.model.UserInfo;
import com.certimeter.progetto.pojo.ActivityPojo;
import com.certimeter.progetto.pojo.MacroPojo;
import com.certimeter.progetto.pojo.UserInfoPojo;
import com.certimeter.progetto.pojo.UserPojo;
import com.certimeter.progetto.repository.MacroMapperRepository;
import com.certimeter.progetto.utilities.Converter;

@RestController
@RequestMapping("/macro")
public class MacroController implements MacroControllerInterface {

	@Autowired
	MacroMapperRepository repo;

	@PostConstruct
	static void init() {
		Function<MacroPojo, Macro> toModel = (pojo) -> {
			Macro macro = Macro.builder().build();
			macro.setId(pojo.getId());
			macro.setName(pojo.getName());
			macro.setActivities(Converter.convert(pojo.getActivities(), Activity.class));
			macro.setDescription(pojo.getDescription());
			macro.setId(pojo.getId());
			macro.setPm(Converter.convert(pojo.getPm(), User.class));
			macro.setAssignedUsers(Converter.convert(pojo.getAssignedUsers(), UserInfo.class));
			macro.setSubAssignedUsers(Converter.convert(pojo.getSubAssignedUsers(), UserInfo.class));
			macro.setDateOfCreation(pojo.getDateOfCreation());
			macro.setExpiringDate(pojo.getExpiringDate());
			return macro;
		};
		Converter.put(MacroPojo.class, Macro.class, toModel);

		Function<Macro, MacroPojo> toPojo = (macro) -> {
			MacroPojo pojo = new MacroPojo();
			pojo.setId(macro.getId());
			pojo.setName(macro.getName());
			pojo.setActivities(Converter.convert(macro.getActivities(), ActivityPojo.class));
			pojo.setDescription(macro.getDescription());
			pojo.setId(macro.getId());
			pojo.setPm(Converter.convert(macro.getPm(), UserPojo.class));
			pojo.setAssignedUsers(Converter.convert(macro.getAssignedUsers(), UserInfoPojo.class));
			pojo.setSubAssignedUsers(Converter.convert(macro.getSubAssignedUsers(), UserInfoPojo.class));
			pojo.setDateOfCreation(macro.getDateOfCreation());
			pojo.setExpiringDate(macro.getExpiringDate());
			return pojo;
		};

		Converter.put(Macro.class, MacroPojo.class, toPojo);

		Function<UserInfoPojo, UserInfo> pojoToModel = (pojo) -> {
			UserInfo userInfo = new UserInfo();
			userInfo.setId(pojo.getId());
			userInfo.setLastname(pojo.getLastname());
			userInfo.setName(pojo.getName());
			return userInfo;
		};
		Converter.put(UserInfoPojo.class, UserInfo.class, pojoToModel);

		Function<UserInfo, UserInfoPojo> modelToPojo = (userInfo) -> {
			UserInfoPojo pojo = new UserInfoPojo();
			pojo.setId(userInfo.getId());
			pojo.setLastname(userInfo.getLastname());
			pojo.setName(userInfo.getName());
			return pojo;
		};

		Converter.put(UserInfo.class, UserInfoPojo.class, modelToPojo);

		Function<Activity, ActivityPojo> actToPojo = (act) -> {
			ActivityPojo actPojo = new ActivityPojo();
			actPojo.setDescription(act.getDescription());
			actPojo.setExpiringDate(act.getExpiringDate());
			actPojo.setId(act.getId());
			actPojo.setName(act.getName());
			actPojo.setSub_activities(act.getSub_activities());
			actPojo.setUsers(act.getUsers());
			return actPojo;
		};
		Converter.put(Activity.class, ActivityPojo.class, actToPojo);

		Function<ActivityPojo, Activity> pojoToAct = (pojo) -> {
			Activity act = Activity.builder().build();
			act.setDescription(pojo.getDescription());
			act.setExpiringDate(pojo.getExpiringDate());
			act.setId(pojo.getId());
			act.setName(pojo.getName());
			act.setSub_activities(pojo.getSub_activities());
			act.setUsers(pojo.getUsers());
			return act;
		};
		Converter.put(ActivityPojo.class, Activity.class, pojoToAct);
	}

	@PostMapping("/list")
	@Override
	public List<Macro> getList(@RequestBody MacroFilter param) {
		return Converter.convert(repo.getAll(param.toParam()), Macro.class);
	}

	@GetMapping("/{macroId}")
	@Override
	public Macro getMacro(@PathVariable String macroId) {
		return Converter.convert(repo.getMacro(macroId), Macro.class);
	}

	@PostMapping("/")
	@Override
	public Macro createMacro(@RequestBody Macro macro) {
		MacroPojo macropojo = Converter.convert(macro, MacroPojo.class);
		return Converter.convert(repo.createMacro(macropojo), Macro.class);
	}

	@PutMapping("/")
	@Override
	public Macro updateMacro(@RequestBody Macro macro) {
		MacroPojo macropojo = Converter.convert(macro, MacroPojo.class);
		return Converter.convert(repo.updateMacro(macropojo), Macro.class);
	}

	@DeleteMapping("/{macroId}")
	@Override
	public void deleteMacro(@PathVariable String macroId) {
		repo.deleteMacro(macroId);
	}

}
