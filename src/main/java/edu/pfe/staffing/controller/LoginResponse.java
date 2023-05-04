package edu.pfe.staffing.controller;

import java.io.Serializable;

public class LoginResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private String rolename;

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public LoginResponse(String jwttoken) {
        this.jwttoken = jwttoken;
    }public LoginResponse(String jwttoken,String rolename) {
        this.jwttoken = jwttoken;
        this.rolename=rolename;
    }

    public String getToken() {
        return this.jwttoken;
    }
}
