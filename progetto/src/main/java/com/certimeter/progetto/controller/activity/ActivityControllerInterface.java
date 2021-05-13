package com.certimeter.progetto.controller.activity;

import com.certimeter.progetto.model.Activity;

public interface ActivityControllerInterface {
	public Activity getActivity(String activityId);

	public Activity getActivityBySubActivity(String subActivityId);

}
