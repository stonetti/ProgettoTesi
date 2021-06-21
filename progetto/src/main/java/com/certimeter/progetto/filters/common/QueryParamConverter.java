package com.certimeter.progetto.filters.common;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public interface QueryParamConverter {

    default List<QueryParameter> toParam() {
        List<QueryParameter> queryParamList = new ArrayList<QueryParameter>();
        Field[] allFields = FieldUtils.getAllFields(this.getClass());
        for (Field f : allFields) {
            try {
                RequestParameter<?> reqP = new RequestParameter<>();
                Object fieldValue = FieldUtils.readField(f, this, true);
                if (fieldValue != null && f.getType().equals(RequestParameter.class)) {
                    reqP = (RequestParameter<?>) fieldValue;
                    queryParamList.add(new QueryParameter(f.getName(), reqP));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return queryParamList;
    }

}
