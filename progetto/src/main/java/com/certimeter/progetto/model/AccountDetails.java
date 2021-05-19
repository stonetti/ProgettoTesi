package com.certimeter.progetto.model;

import lombok.Data;

@Data
public class AccountDetails {
	private String username;
	private String password;
	// TODO: codificare la password per il lato db
}
