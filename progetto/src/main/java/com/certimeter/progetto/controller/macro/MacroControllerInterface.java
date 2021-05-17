
package com.certimeter.progetto.controller.macro;

import java.util.List;

import com.certimeter.progetto.model.Macro;

public interface MacroControllerInterface {

	public List<Macro> getList();

	public Macro getMacro(String macroId);

	public Macro createMacro(Macro macro);

	public Macro updateMacro(Macro macro);

	public void deleteMacro(String macroId);

}
