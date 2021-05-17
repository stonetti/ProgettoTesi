
package com.certimeter.progetto.controller.subActivity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.certimeter.progetto.model.Macro;
import com.certimeter.progetto.model.SubActivity;
import com.certimeter.progetto.repository.macro.MacroRepository;

@RestController
@RequestMapping("/subActivities")
public class SubActivityController {

	@Autowired
	MacroRepository macroRepo;

	@GetMapping("/{subActivityId}")
	public SubActivity getSubActivity(@PathVariable String subActivityId) {
		Macro macro = macroRepo.getMacroByActivityId(subActivityId);
		return macroRepo.getActivity(macro, subActivityId);
	}

	@GetMapping("/list")
	public List<SubActivity> getAll(Macro macro) {
		return macroRepo.getAllSubActvities(macro);
	}

	@DeleteMapping("/{subActivityId}")
	public void deleteSubActivity(@PathVariable String subActivityId) {
		macroRepo.deleteObject(subActivityId);
	}

	@PostMapping("/")
	@PutMapping("/")
	public SubActivity updateSubActivity(SubActivity subActivity) {
		Macro macro = macroRepo.getMacroByActivityId(subActivity.getId());
		return macroRepo.setObject(macro, subActivity);
	}

}