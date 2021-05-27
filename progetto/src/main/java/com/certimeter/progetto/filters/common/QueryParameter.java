package com.certimeter.progetto.filters.common;

import java.util.EnumMap;
import java.util.List;
import java.util.stream.Collectors;

import com.certimeter.progetto.enums.Operators;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryParameter {

	private String key;
	private List<Object> value;
	private Operators op;

	EnumMap<Operators, String> opMap = new EnumMap<Operators, String>(Operators.class);

	// private void fillOpMap() {
	// opMap.put(Operators.EQ, " =");
	// opMap.put(Operators.BETWEEN, " beetween ");
	// opMap.put(Operators.CONTAINS, " contains");
	// opMap.put(Operators.ENDS, "4");
	// opMap.put(Operators.GREATER_EQ, " >=");
	// opMap.put(Operators.GREATER_THAN, " >");
	// opMap.put(Operators.IN, " in");
	// opMap.put(Operators.LESS_EQ, " <=");
	// opMap.put(Operators.LESS_THAN, " <");
	// opMap.put(Operators.NOT, " <>");
	// opMap.put(Operators.NOT_IN, " not in");
	// opMap.put(Operators.STARTS, " 3");
	// }

	public QueryParameter(String key, RequestParameter<?> param) {
		// fillOpMap();
		this.key = key;
		this.value = param.getValue().stream().map(e -> (Object) e).collect(Collectors.toList());
		this.op = param.getOperator();
		// opMap.get(param.getOperator());
	}

}
