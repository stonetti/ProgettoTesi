package com.certimeter.progetto.controller.macro;

import com.certimeter.progetto.errorHandling.AuthorizationFailureException;
import com.certimeter.progetto.filters.MacroFilter;
import com.certimeter.progetto.model.Macro;

import java.util.List;

public interface MacroControllerInterface {

    public List<Macro> getList(MacroFilter param, String token) throws AuthorizationFailureException;

    ;

    public Macro getMacro(String macroId, String token) throws AuthorizationFailureException;

    ;

    public Macro createMacro(Macro macro, String token) throws AuthorizationFailureException;

    ;

    public Macro updateMacro(Macro macro, String token) throws AuthorizationFailureException;

    ;

    public void deleteMacro(String macroId, String token) throws AuthorizationFailureException;

    ;

}
