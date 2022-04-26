package com.example.testproject.service;

import com.example.testproject.Entity.UserEntity;
import com.example.testproject.Helpers.TokenHelper;
import com.example.testproject.Repository.ServiceRepository;
import com.example.testproject.Repository.UserRepository;
import com.example.testproject.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    
    public User whoIam(String token) {
        if(token != null){
            String login = TokenHelper.getLoginByToken(token);
            return User.ToModel(userRepository.getByLogin(login));
        }
        return null;
    }

    public boolean createUser(SignUp signUp) {
        boolean hasUser = userRepository.existsUserEntitiesByLogin(signUp.getLogin());
        if(!hasUser){
            UserEntity userEntity = new UserEntity(signUp);
            userRepository.save(userEntity);
          return true;
        }
        return false;
    }

    public ResponseEntity singInUser(SingIn singIn, String token) {
        if(token.equals("")) {
            boolean check = userRepository.existsUserEntitiesByLogin(singIn.getLogin())
                    && userRepository.getByLogin(singIn.getLogin()).getPassword().equals(singIn.getPassword());
            if (check) {
                     String tkn;
                     if(TokenHelper.blackListContains(singIn.getLogin())){
                         tkn = TokenHelper.getFromBlackList(singIn.getLogin());
                     }
                     else {
                         tkn = TokenHelper.getToken(singIn.getLogin());
                     }

                return ResponseEntity.ok(tkn);
            }
            return ResponseEntity.badRequest().body("Incorrect login or password!!!");
        }
        return ResponseEntity.badRequest().body("Firstly sign out please!!!");
    }

    public Boolean LogOut(String token) {
        if(token != null){
            String login = TokenHelper.getLoginByToken(token);
            TokenHelper.addToBlackList(login,token);
            return true;
        }
        return false;
    }


    public ResponseEntity getUserById(long id,String token) {
        if(token != null) {
            String login = TokenHelper.getLoginByToken(token);
            if(login.equals("Admin")) {
                if (userRepository.existsUserEntitiesById(id)) {
                    return ResponseEntity.ok(User.ToModel(userRepository.getById(id)));
                }
                return ResponseEntity.badRequest().body("User not found");
            }
            return ResponseEntity.badRequest().body("You are not an admin!!!");
        }
        return ResponseEntity.badRequest().body("Plaese, firstly sign in to the website!!!");
    }

    public ResponseEntity getUserByLogin(String login, String token) {
        if (token != null) {
            if (userRepository.existsUserEntitiesByLogin(login)) {
                return ResponseEntity.ok(userRepository.getByLogin(login));
            }
            return ResponseEntity.badRequest().body("User not found");
        }
        return ResponseEntity.badRequest().body("You are not signed");
    }

    public ResponseEntity setProfile(Update update, String token) {
        if(token!=null){
            String login = TokenHelper.getLoginByToken(token);
            UserEntity user = userRepository.getByLogin(login);
            if(update.getFirstname() != null) {
                user.setFirstname(update.getFirstname());

            }
            if(update.getLastname() != null) {
                user.setLastname(update.getLastname());
            }
            if(update.getPassword() != null) {
                user.setPassword(update.getPassword());
            }
            userRepository.save(user);
            return ResponseEntity.ok("Your profile successfully updated!!!");
        }
        return ResponseEntity.badRequest().body("You are not signed!!!Please, firstly sign in!!!");
    }

    public String deleteMyAccount(String token) {
        if(token != null) {
            String login = TokenHelper.getLoginByToken(token);
            if(!login.equals("Admin")) {
                if (userRepository.existsUserEntitiesByLogin(login)) {
                    userRepository.deleteByLogin(login);
                    TokenHelper.removeFromBlackList(login);
                    return "OK";
                }
                return "ErrorDelete";
            }
        }
        return "NotSigned";
    }

    public String deleteAccount(long id, String token) {

        if(token != null){
            String login = TokenHelper.getLoginByToken(token);
            if(login.equals("Admin")) {
                long admin_id = userRepository.getByLogin(login).getId();
                if (userRepository.existsUserEntitiesById(id) && id != admin_id) {
                    userRepository.deleteById(id);
                    TokenHelper.removeFromBlackList(login);
                    return "OK";
                }
                return "ErrorDelete";
            }
            return "notAdmin";
        }
        return "notSigned";

    }
    @Autowired
    private ServiceRepository serviceRepository;

    public boolean addServiceToUser(Integer userId, List<Integer> serviceId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId.longValue());
        UserEntity user = userOptional.orElse(null);
        if(user == null) {
            return false;
        }
        List<com.example.testproject.Entity.Service> service = new ArrayList<>();
        serviceId.forEach(id -> serviceRepository.findById(id.longValue()).ifPresent(p -> service.add(p)));
        user.setServices(service);
        userRepository.save(user);
        return true;
    }
    public UserEntity getUser(Integer id) {
        return userRepository.findById(id.longValue()).orElse(null);
    }

    public String insertMoney(Money money1, String token){
        if (token != null) {
            String login = TokenHelper.getLoginByToken(token);
            UserEntity user = userRepository.getByLogin(login);
            Integer mon = money1.getMoney();
            if (money1.getMoney() != null) {
                user.setMoney(mon + (user.getMoney()));
                userRepository.save(user);
                return "MoneyInserted";
            }
            return "EnterAmount";
        }
        return "LoginPlease";
    }

    public String takeMoney(Money money1, String token){
        if (token != null) {
            String login = TokenHelper.getLoginByToken(token);
            UserEntity user = userRepository.getByLogin(login);
            Integer mon = money1.getMoney();
            //System.out.println(mon);
            //System.out.println(user.getMoney());
            //System.out.println(mon);
            //System.out.println(user.getLogin());
            //userRepository.getByLogin(user.getLogin()).setMoney(500);
            if (money1.getMoney() != null) {
                if((user.getMoney()) - mon >= 0) {
                    user.setMoney((user.getMoney()) - mon);
                    userRepository.save(user);

                    return "MoneyTaken";
                }
                return "NotEnough";
            }
            return "EnterAmount";
        }
        return "LoginPlease";
    }

    public String sendMoney(Money money1, String login1, String token){
        if (token != null) {
            String login = TokenHelper.getLoginByToken(token);
            UserEntity user = userRepository.getByLogin(login);
            Integer mon = money1.getMoney();
            if (money1.getMoney() != null) {
                if((user.getMoney()) - mon >= 0) {
                    user.setMoney((user.getMoney()) - mon);
                    userRepository.save(user);
                    UserEntity user1 = userRepository.getByLogin(login1);
                    Integer mon1 = money1.getMoney();
                    user1.setMoney(mon1 + (user1.getMoney()));
                    userRepository.save(user1);
                    return "MoneyTaken";
                }
                return "NotEnough";
            }
            return "EnterAmount";
        }
        return "LoginPlease";
    }

}
