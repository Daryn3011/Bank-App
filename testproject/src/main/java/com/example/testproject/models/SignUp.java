package com.example.testproject.models;

import javax.persistence.criteria.CriteriaBuilder;

public class SignUp {
    private String firstname;
    private String lastname;
    private String login;
    private String password;
    private Integer money;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public Integer getMoney(){
        return money;
    }

    public void setMoney(Integer money){
        this.money = money;
    }
}
