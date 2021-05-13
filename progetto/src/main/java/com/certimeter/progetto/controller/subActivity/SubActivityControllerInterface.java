package com.certimeter.progetto.controller.subActivity;

import com.certimeter.progetto.model.SubActivity;

public interface SubActivityControllerInterface {
	public SubActivity getSubActivity(String subActivityId);

	public SubActivity getSubActivityBySubActivity(String SubActivityId);
	// non utile come activity controlelr
}
