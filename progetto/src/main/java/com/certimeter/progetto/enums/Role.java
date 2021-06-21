package com.certimeter.progetto.enums;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN"),
    PM("PM");

    private String role;

    public String getRole() {
        return this.role;
    }

    Role(String user) {
        this.role = user;
    }
}
