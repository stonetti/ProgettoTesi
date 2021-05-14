
package com.certimeter.progetto.controller.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.certimeter.progetto.model.Activity;
import com.certimeter.progetto.repository.activity.ActivityRepository;

@RestController
@RequestMapping("/activities")
public class ActivityController {

	@Autowired
	ActivityRepository repo;

	@GetMapping("/{activityId}")
	public Activity getActivity(@PathVariable String activityId) {
		return repo.getActivity(activityId);
	}

	@GetMapping("/{subActivityId}")
	public Activity getActivityBySubActivity(String subActivityId) {
		return repo.getActivityBySubActivity(subActivityId);
	}

	@PostMapping("/")
	public Activity createActivity(@RequestBody Activity activity) {
		return repo.createActivity(activity);
	}

	@GetMapping("/list")
	public List<Activity> getAll(String macroId) {
		return repo.getAll();
	}

	@DeleteMapping("/{activityId}")
	public void deleteActivity(@PathVariable String activityId) {
		repo.deleteActivity(activityId);
	}

	@PutMapping("/")
	public Activity updateActivity(Activity activity) {
		return repo.updateActivity(activity);
	}

}
