package com.certimeter.progetto.filters;

import com.certimeter.progetto.filters.common.QueryParamConverter;
import com.certimeter.progetto.filters.common.RequestParameter;

import java.time.LocalDate;
import java.util.List;

public class ReportFilter implements QueryParamConverter {

    RequestParameter<String> id;
    RequestParameter<List<String>> idPath;
    RequestParameter<Integer> amount;
    RequestParameter<LocalDate> date;
    RequestParameter<String> note;
    RequestParameter<String> user;
    RequestParameter<List<String>> pm;

}
