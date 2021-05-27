package com.certimeter.progetto.filters.common;

import java.util.List;

import com.certimeter.progetto.enums.Operators;

import lombok.Data;

@Data
public class RequestParameter<T> {

	private List<T> value;
	private Operators operator;

}
