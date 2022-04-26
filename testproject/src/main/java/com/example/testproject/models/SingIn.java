package com.example.testproject.models;

public class SingIn {
    private String login;
    private String password;

    public SingIn(String login, String pass) {
        this.login = login;
        this.password = pass;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
