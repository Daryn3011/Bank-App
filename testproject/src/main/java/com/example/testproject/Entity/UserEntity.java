package com.example.testproject.Entity;

import com.example.testproject.models.SignUp;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name = "usr")
@Getter
@Setter
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstname;
    private String lastname;
    private String login;
    private String password;
    private Integer money;
    @OneToMany
    private List<Service> services;

    public UserEntity(SignUp signUp) {
        this.firstname = signUp.getFirstname();
        this.lastname = signUp.getLastname();
        this.login = signUp.getLogin();
        this.password = signUp.getPassword();
        this.money = signUp.getMoney();
    }

    public UserEntity() {

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money){
        this.money = money;
    }
}
