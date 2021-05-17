
package com.certimeter.progetto.controller.macro;

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

import com.certimeter.progetto.model.Macro;
import com.certimeter.progetto.repository.macro.MacroRepository;

@RestController
@RequestMapping("/macros")
public class MacroController implements MacroControllerInterface {

	@Autowired
	MacroRepository repo;

	@PostMapping("/list")
	@Override
	public List<Macro> getList() {
		return repo.getAll();
	}

	@GetMapping("/{macroId}")
	@Override
	public Macro getMacro(@PathVariable String macroId) {
		return repo.getMacro(macroId);
	}

	@PostMapping("/")
	@Override
	public Macro createMacro(@RequestBody Macro macro) {
		return repo.createMacro(macro);
	}

	@PutMapping("/")
	@Override
	public Macro updateMacro(@RequestBody Macro macro) {
		return repo.updateMacro(macro);
	}

	@DeleteMapping("/{macroId}")
	@Override
	public void deleteMacro(@PathVariable String macroId) {
		repo.deleteMacro(macroId);
	}

}
