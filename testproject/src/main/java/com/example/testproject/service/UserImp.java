package com.example.testproject.service;

import com.example.testproject.Helpers.TokenHelper;
import com.example.testproject.models.Money;
import com.example.testproject.models.SingIn;
import com.example.testproject.models.Update;
import com.example.testproject.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class UserImp implements UserService{
    private String Token;

    @Override
    public User whichAccount() {
        if(getToken() != null){
            String login = TokenHelper.getLoginByToken(getToken());
            return listByLogin.get(login);
        }
        return null;
    }

    @Override
    public boolean createUser(User user) {
        boolean hasUser = listById.containsKey(user.getId()) || listByLogin.containsKey(user.getLogin());
        if(!hasUser){
            listById.put(user.getId(), user);
            listByLogin.put(user.getLogin(), user);
          return true;
        }
        return false;
    }

    @Override
    public String loginUser(SingIn singIn) {
        if(getToken() == null) {
            boolean check = listByLogin.containsKey(singIn.getLogin())
                    && listByLogin.get(singIn.getLogin()).getPassword().equals(singIn.getPassword());
            if (check) {
                    String token = TokenHelper.getToken(singIn.getLogin());
                    setToken(token);
                return "Ok";
            }
            return "WrongLog";
        }
        return "ErrorSystem";
    }

    @Override
    public Boolean logoutUser() {
        if(getToken() != null){
            setToken(null);
            return true;
        }
        return false;
    }


    @Override
    public ResponseEntity getUserById(long id) {
        if(getToken() != null) {
            String login = TokenHelper.getLoginByToken(getToken());
            if(login.equals("Admin")) {
                if (listById.containsKey(id)) {
                    return ResponseEntity.ok( listById.get(id));
                }
                return ResponseEntity.badRequest().body("User not found");
            }
            return ResponseEntity.badRequest().body("You are not admin");
        }
        return ResponseEntity.badRequest().body("Please, firstly login in to the bank account");
    }

    @Override
    public ResponseEntity getUserByLogin(String login) {
        if (getToken() != null) {
            if (listByLogin.containsKey(login)) {
                return ResponseEntity.ok(listByLogin.get(login));
            }
            return ResponseEntity.badRequest().body("User not found");
        }
        return ResponseEntity.badRequest().body("You are not logged in");
    }

    @Override
    public ResponseEntity updateUser(Update update) {
        if(getToken()!=null){
            String login = TokenHelper.getLoginByToken(getToken());
            User user = listByLogin.get(login);
            if(update.getName() != null)
                user.setName(update.getName());
                listById.get(user.getId()).setName(update.getName());
            if(update.getSurname() != null)
                user.setSurname(update.getSurname());
                listById.get(user.getId()).setSurname(update.getSurname());
            if(update.getPassword() != null)
                user.setPassword(update.getPassword());
                listById.get(user.getId()).setPassword(update.getPassword());

            return ResponseEntity.ok("Your profile successfully updated");
        }
        return ResponseEntity.badRequest().body("You are not logged in. Please, firstly log in");
    }

    @Override
    public String deleteMyAccount() {
        if(getToken() != null) {
            String login = TokenHelper.getLoginByToken(getToken());
            if(!login.equals("Admin")) {
                if (listByLogin.containsKey(login)) {
                    listByLogin.remove(login);
                    setToken(null);
                    return "OK";
                }
                return "ErrorDelete";
            }
        }
        return "NotSigned";
    }

    @Override
    public String deleteAccount(long id) {
        if(getToken() != null){
            String login = TokenHelper.getLoginByToken(getToken());
            if(login.equals("Admin")) {
                if (listById.containsKey(id)) {
                    listById.remove(id);
                    return "OK";
                }
                return "ErrorDelete";
            }
            return "notAdmin";
        }
        return "notSigned";
    }

    @Override
    public String insertMoney(Money money1){
        if (getToken() != null) {
            String login = TokenHelper.getLoginByToken(getToken());
            User user = listByLogin.get(login);
            String mon = money1.getMoney();
            if (money1.getMoney() != null) {
                listById.get(user.getId()).setMoney(Integer.parseInt(mon) + (user.getMoney()));
                return "MoneyInserted";
            }
            return "EnterAmount";
        }
        return "LoginPlease";
    }

    @Override
    public String takeMoney(Money money1){
        if (getToken() != null) {
            String login = TokenHelper.getLoginByToken(getToken());
            User user = listByLogin.get(login);
            String mon = money1.getMoney();
            if (money1.getMoney() != null) {
                if((user.getMoney()) - Integer.parseInt(mon) >= 0) {
                    listById.get(user.getId()).setMoney((user.getMoney()) - Integer.parseInt(mon));
                    return "MoneyTaken";
                }
                return "NotEnough";
            }
            return "EnterAmount";
        }
        return "LoginPlease";
    }

    @Override
    public Serializable showAllUsers(){
        if(getToken() != null) {
            String login = TokenHelper.getLoginByToken(getToken());
            if(login.equals("Admin")) {
                return "Success";
            }
            return "You are not admin";
        }
        return "You are not logged in";
    }

    /*@Override
    public HashMap showHighest(UserService user){
        if(getToken() != null) {
            String login = TokenHelper.getLoginByToken(getToken());
            if(login.equals("Admin")) {
                for (int i=1; i< listById.size(); i++) {
                    String max = null;
                    if (user.getUserById(i) > max) {
                        max = user.getUserById(i);
                    }
                    return UserService.listByLogin.containsKey(max);
                }
            }
            return false;
        }
        return false;
    }*/

    @Override
    public String showAmountOfUsers(){
        if(getToken() != null) {
            String login = TokenHelper.getLoginByToken(getToken());
            if(login.equals("Admin")) {
                return "Success";
            }
            return "NotAdmin";
        }
        return "NotLogged";
    }


    public String getToken() {
        return Token;
    }
    public void setToken(String token) {
        Token = token;
    }
}
