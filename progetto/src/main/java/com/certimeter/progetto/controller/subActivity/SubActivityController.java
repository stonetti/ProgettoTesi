package com.certimeter.progetto.controller.subActivity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.certimeter.progetto.model.SubActivity;
import com.certimeter.progetto.repository.activity.ActivityRepository;
import com.certimeter.progetto.repository.macro.MacroRepository;
import com.certimeter.progetto.repository.subActivity.SubActivityRepository;

@RestController
@RequestMapping("/subActivities")
public class SubActivityController {

	@Autowired
	SubActivityRepository repo;

	@Autowired
	MacroRepository macroRepo;

	@Autowired
	ActivityRepository activityRepo;

	@GetMapping("/{subActivityId}")
	public SubActivity getSubActivity(@PathVariable String subActivityId) {
		return repo.getSubActivity(subActivityId);
	}

//	@GetMapping("/{subActivityId}")
//	public SubActivity getSubActivityBySubSubActivity(String subActivityId) {
//		return repo.getSubActivityBySubSubActivity(subActivityId);
//	}

	@PostMapping("/")
	public SubActivity createSubActivity(@RequestBody SubActivity subActivity) {
		return macroRepo.updateMacro(subActivity);
	}

	@GetMapping("/list")
	public List<SubActivity> getAll() {
		return repo.getAll();
	}

	@DeleteMapping("/{subActivityId}")
	public void deleteSubActivity(@PathVariable String subActivityId) {
		macroRepo.updateMacro(subActivityId);
	}

	// Come evitare questo passaggio? Oltre che la scrittura poco elegante questa
	// "soluzione" presenta un problema:
	// come prendere gli indici delle subActivities annidate?
//	@PutMapping("/")
//	public SubActivity updateSubActivity(SubActivity subActivity) {
//		Macro macro = macroRepo.getMacroByActivityId(subActivity.getId());
//		int acIndex = macro.getActivities().indexOf(activityRepo.getActivityBySubActivity(subActivity.getId()));
//		int subIndex = macro.getActivities().get(acIndex).getSub_activities().indexOf(subActivity);
//		return macro.getActivities().get(acIndex).getSub_activities().set(subIndex, subActivity);
//	}

}
