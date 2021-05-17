package com.certimeter.progetto.repository.macro;

import java.util.List;

import com.certimeter.progetto.model.Activity;
import com.certimeter.progetto.model.Macro;
import com.certimeter.progetto.model.SubActivity;

public class MacroRepository {

	public List<Macro> getOpen() {

		return null;
	}

	public List<Macro> getAll() {

		return null;
	}

	public List<Macro> getAllOpen() {

		return null;
	}

	public List<Macro> getAllHistory() {

		return null;
	}

	public Macro getMacro(String macroId) {

		return null;
	}

	public Macro createMacro(Macro macro) {

		return null;
	}

	public Macro getMacroByActivityId(String activityId) {

		return null;
	}

	public Macro updateMacro(Macro macro) {

		return null;
	}

	public void deleteMacro(String macroId) {

	}

	// metodi per gli oggetti embedded

	public <T> T setObject(Macro macro, T activity) {
		return null;
	}

	public void deleteObject(String id) {
	}

	public List<Activity> getAllActivities(Macro macro) {
		return null;
	}

	public <T> T getActivity(Macro macro, String activityId) {
		return null;
	}

	public List<SubActivity> getAllSubActvities(Macro macro) {
		return null;
	}

}
