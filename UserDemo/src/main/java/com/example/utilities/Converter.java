package com.example.utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Converter {

	static public <T, R> List<R> convert(List<T> data,Function<T,R> convertFunction){
		return data.stream().map(convertFunction).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	static public <T, R> R convert(T data, Class<R> cls){
		Function<?, ?> funct = functions.get(data.getClass()).get(cls);
		return ((Function<T,R>) funct).apply(data);
	}

	public static Map<Class<?>, Map<Class<?>, Function<?, ?>>> functions = new HashMap<>();
	
	public static void put(Class<?> from, Class<?> to, Function<?, ?> funct) {
		Map<Class<?>, Function<?, ?>> map = new HashMap<>();
		map.put(to, funct);
		functions.put(from, map );
	}
	
}
