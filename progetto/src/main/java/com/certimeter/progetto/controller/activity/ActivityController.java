
package com.certimeter.progetto.controller.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.certimeter.progetto.model.Activity;
import com.certimeter.progetto.model.Macro;
import com.certimeter.progetto.repository.macro.MacroRepository;

@RestController
@RequestMapping("/activities")
public class ActivityController {

	@Autowired
	MacroRepository macroRepo;

	@GetMapping("/{activityId}")
	public Activity getActivity(@PathVariable String activityId) {
		Macro macro = macroRepo.getMacroByActivityId(activityId);
		return macroRepo.getActivity(macro, activityId);
	}

	@GetMapping("/list")
	public List<Activity> getAll(Macro macro) {
		return macroRepo.getAllActivities(macro);
	}

	@DeleteMapping("/{activityId}")
	public void deleteActivity(@PathVariable String activityId) {
		macroRepo.deleteObject(activityId);
	}

	@PostMapping("/")
	@PutMapping("/")
	public Activity updateActivity(Activity activity) {
		Macro macro = macroRepo.getMacroByActivityId(activity.getId());
		return macroRepo.setObject(macro, activity);
	}

}
