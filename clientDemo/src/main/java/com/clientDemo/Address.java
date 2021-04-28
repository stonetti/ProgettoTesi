package com.clientDemo;

import javax.validation.constraints.NotNull;

public class Address {
	@NotNull
	String city;
	String street;
	String cap;
	
	Address(){
		this.cap = ":)";
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}
}
