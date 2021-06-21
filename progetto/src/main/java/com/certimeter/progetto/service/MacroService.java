package com.certimeter.progetto.service;

import com.certimeter.progetto.errorHandling.AuthorizationFailureException;
import com.certimeter.progetto.filters.MacroFilter;
import com.certimeter.progetto.model.Activity;
import com.certimeter.progetto.model.Macro;
import com.certimeter.progetto.model.User;
import com.certimeter.progetto.model.UserInfo;
import com.certimeter.progetto.pojo.MacroPojo;
import com.certimeter.progetto.repository.MacroMapperRepository;
import com.certimeter.progetto.repository.ReportMapperRepository;
import com.certimeter.progetto.utilities.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MacroService {

    @Autowired
    AuthorizationService authorizationService;
    @Autowired
    MacroMapperRepository macroMapperRepository;
    @Autowired
    JwtService jwtService;

    @Autowired
    ReportMapperRepository reportMapperRepository;

    public List<Macro> getList(MacroFilter param, String token) throws AuthorizationFailureException {
        if (authorizationService.isAdmin(token))
            return Converter.convert(macroMapperRepository.getAll(param.toParam()), Macro.class);
        else if (authorizationService.isPm(token)) {
            String pm = jwtService.getUserFromToken(token).getId();

            return Converter.convert(macroMapperRepository.getListByPm(pm, param.toParam()), Macro.class);
        } else if (authorizationService.isUser(token)) {
            String user = jwtService.getUserFromToken(token).getId();
            return Converter.convert(macroMapperRepository.getListByUser(user, param.toParam()), Macro.class);
        } else
            throw new AuthorizationFailureException();
    }

    //TEST PER USER
    public Macro getMacro(String macroId, String token) throws AuthorizationFailureException {
        if (authorizationService.isAdmin(token))
            return Converter.convert(macroMapperRepository.getMacro(macroId), Macro.class);
        else if (authorizationService.isPm(token))
            return Converter.convert(macroMapperRepository.getMacroByIdAndPm(macroId, jwtService.getUserFromToken(token).getId()), Macro.class);
        else if (authorizationService.isUser(token)) {
            Macro macro = Converter.convert(macroMapperRepository.getMacro(macroId), Macro.class);
            User user = jwtService.getUserFromToken(token);
            List<UserInfo> assignedUserInfoList = macro.getAssignedUsers();
            for (UserInfo userinfo : assignedUserInfoList) {
                if (userinfo.getId().equals(user.getId()))
                    return macro;
            }
            List<UserInfo> subAssignedUserList = macro.getSubAssignedUsers();
            for (UserInfo userinfo : subAssignedUserList) {
                if (userinfo.getId().equals(user.getId())) {
                    for (Activity activity : macro.getActivities())
                        for (UserInfo activityUserInfo : activity.getUsers()) {
                            if (activityUserInfo.getId().equals(user.getId())) {
                            }
                            //??
                        }
                }
            }
            //throw solo se nel for non return
            throw new AuthorizationFailureException();
        } else
            throw new AuthorizationFailureException();
    }


    public Macro createMacro(Macro macro, String token) throws AuthorizationFailureException {
        if (authorizationService.isAdmin(token)) {
            MacroPojo macroPojo = Converter.convert(macro, MacroPojo.class);
            return Converter.convert(macroMapperRepository.createMacro(macroPojo), Macro.class);
        } else if (authorizationService.isPm(token))//DEBUG
            throw new AuthorizationFailureException();
        else if (authorizationService.isUser(token))
            throw new AuthorizationFailureException();
        else
            throw new AuthorizationFailureException();
    }


    public Macro updateMacro(Macro macro, String token) throws AuthorizationFailureException {
        MacroPojo macroPojo = Converter.convert(macro, MacroPojo.class);
        if (authorizationService.isAdmin(token)) {
            return Converter.convert(macroMapperRepository.updateMacro(macroPojo), Macro.class);
        } else if (authorizationService.isPm(token)) {
            if (macro.getPm().contains(jwtService.getUserFromToken(token).getId()))
                return Converter.convert(macroMapperRepository.updateMacro(macroPojo), Macro.class);
            else
                throw new AuthorizationFailureException();
        } else if (authorizationService.isUser(token))
            throw new AuthorizationFailureException();
        else
            throw new AuthorizationFailureException();
    }


    public void deleteMacro(String macroId, String token) throws AuthorizationFailureException {
        if (reportMapperRepository.getReportByMacroId(macroId).size() == 0) {
            if (authorizationService.isAdmin(token))
                macroMapperRepository.deleteMacro(macroId);
            else if (authorizationService.isPm(token))
                if (getMacro(macroId, token).getPm().contains(jwtService.getUserFromToken(token).getId()))
                    macroMapperRepository.deleteMacro(macroId);
                else
                    throw new AuthorizationFailureException();
        } else
            throw new AuthorizationFailureException("This Macro can't be deleted: some reports reference to it.");
    }
}
