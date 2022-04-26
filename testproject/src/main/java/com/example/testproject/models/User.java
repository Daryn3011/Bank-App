package com.example.testproject.models;

import com.example.testproject.Entity.UserEntity;

import javax.persistence.Entity;
import javax.persistence.Table;


@Table(name = "usr")

public class User {
    private Long id;
    private String firstname;
    private String lastname;
    private String login;
    private Integer money;

    public static User ToModel(UserEntity userEntity){
        User user = new User();
        user.setId(userEntity.getId());
        user.setFirstname(userEntity.getFirstname());
        user.setLastname(userEntity.getLastname());
        user.setLogin(userEntity.getLogin());
        user.setMoney(userEntity.getMoney());

        return user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Integer getMoney(){
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }
}
