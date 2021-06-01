package com.certimeter.progetto.service;

import com.certimeter.progetto.filters.MacroFilter;
import com.certimeter.progetto.model.Macro;
import com.certimeter.progetto.repository.MacroMapperRepository;
import com.certimeter.progetto.utilities.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MacroService {

    @Autowired
    MacroMapperRepository macroMapperRepository;

    public List<Macro> getList(MacroFilter param, String token) {
        return Converter.convert(macroMapperRepository.getAll(param.toParam()), Macro.class);
    }
}
