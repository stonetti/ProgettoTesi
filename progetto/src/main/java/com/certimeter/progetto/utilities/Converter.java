package com.certimeter.progetto.utilities;

import com.certimeter.progetto.enums.Operators;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Converter {

    static public <T, R> List<R> convert(List<T> data, Class<R> cls) {
        if (data != null)
            return data.stream().map(e -> convert(e, cls)).collect(Collectors.toList());
        return null;
    }

    @SuppressWarnings("unchecked")
    static public <T, R> R convert(T data, Class<R> cls) {

        Function<?, ?> funct = functions.get(data.getClass()).get(cls);
        return ((Function<T, R>) funct).apply(data);
    }

    public static Map<Class<?>, Map<Class<?>, Function<?, ?>>> functions = new HashMap<>();

    public static void put(Class<?> from, Class<?> to, Function<?, ?> funct) {
        Map<Class<?>, Function<?, ?>> map = functions.getOrDefault(from, new HashMap<>());
        map.put(to, funct);
        functions.put(from, map);
    }

    public static Criteria toCriteria(String key, Operators op, List<Object> list) {
        Criteria crit = new Criteria();

        switch (op) {
            case EQ:
                crit = Criteria.where(key).is(list.get(0));
                break;
            case LESS_THAN:
                crit = Criteria.where(key).lt(list.get(0));
                break;
            case GREATER_THAN:
                crit = Criteria.where(key).gt(list.get(0));
                break;
            case NOT:
                crit = Criteria.where(key).ne(list.get(0));//TODO: non sempre utilizzo l'indice 0 della lista!!
                break;
            case LESS_EQ:
                crit = Criteria.where(key).lte(list.get(0));
                break;
            case GREATER_EQ:
                crit = Criteria.where(key).gte(list.get(0));
                break;
            case CONTAINS:
                String regex = list.get(0).toString();
                crit = Criteria.where(key).regex("." + regex + ".", "i");
                break;
            case STARTS:
                String re = list.get(0).toString();
                crit = Criteria.where(key).regex(re + ".", "i");
                break;
            case ENDS:
                String rex = list.get(0).toString();
                crit = Criteria.where(key).regex("." + rex, "i");
                break;
            case BETWEEN:
                crit = Criteria.where(key).gt(list.get(0)).lte(list.get(1));
                break;
            case IN:
                crit = Criteria.where(key).in(list);
                break;
            case NOT_IN:
                crit = Criteria.where(key).not().in(list.get(0));
                break;

        }
        return crit;
    }

    ;

}
