package com.example.testproject.controllers;


import com.example.testproject.Entity.UserEntity;
import com.example.testproject.models.*;
import com.example.testproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

//@EnableGlobalMethodSecurity(prePostEnabled = true)
@RestController
@RequestMapping("/user")
public class  UserController {
    private long id = 0;
    private String token;
    private int money = 0;
    @Autowired
    UserService userService;

    //@PreAuthorize("hasAnyAuthority('Admin')")
    @GetMapping("/myProfile")
    public ResponseEntity whoIam(@RequestHeader String token){
        try{
            User user = userService.whoIam(token);
            if(user != null){
                return ResponseEntity.ok(user);
            }
            return ResponseEntity.badRequest().body("You aren't signed!!! Please, firstly sign in to the website!!!");
        }catch (Exception e){return ResponseEntity.badRequest().body(e);}
    }

    @PostMapping("/create")
    public Object createUser(@RequestBody SignUp signUp, Model model){
        model.addAttribute("user",new User());
        try{
            signUp.setMoney(0);
            boolean created = userService.createUser(signUp);
            if (created) {
               return "loginUser";
            }
            else {
               return ResponseEntity.badRequest().body("This user is already have!!! Please, write another login!!!");
            }
        }catch(Exception e){
             return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity LogUser(@RequestBody SingIn sing_in, @RequestHeader String token){
         try{
             return userService.singInUser(sing_in,token);

         }catch(Exception e){
            return ResponseEntity.badRequest().body(e);
         }

    }
    @PostMapping("/logout")
    public ResponseEntity LogOutUser(@RequestHeader String token){
        try{
            Boolean logOut = userService.LogOut(token);
            if(logOut){
                return ResponseEntity.accepted().body("You are sing out successfully!!!");
            }
            return ResponseEntity.badRequest().body("You are not signed in to the the website!!!\n Please,firstly sign in!!!");
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @GetMapping("/getUserById")
    public ResponseEntity getUserById(@RequestParam long id,@RequestHeader String token){
        try{
               return userService.getUserById(id,token);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }
    @GetMapping("/get") public ResponseEntity getUserById(@RequestParam Integer id) {
        UserEntity userRequest = userService.getUser(id);
        if (userRequest == null) {
            return new ResponseEntity("User not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(userRequest);
    }

    @GetMapping("/getUserByLogin")
    public ResponseEntity getUserByLogin(@RequestParam String login, @RequestHeader String token){
        try{
            return userService.getUserByLogin(login,token);
        }catch(Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/update")
    public ResponseEntity setProfile(@RequestBody Update update, @RequestHeader String token){
        try{
            return userService.setProfile(update,token);
        }catch (Exception e){return ResponseEntity.badRequest().body(e);}
    }

    @PostMapping("/deleteMyAccount")
    public ResponseEntity deleteMyAccount(@RequestHeader String token){
        try{
            String deleteMsg = userService.deleteMyAccount(token);
             switch (deleteMsg){
                 case "OK" : return ResponseEntity.accepted().body("You are deleted your account successfully");
                 case "ErrorDelete" : return ResponseEntity.badRequest().body("System have some problems by deleting account!!!");
                 case "NotSigned" : return ResponseEntity.badRequest().body("You are not signed!!! Please. firstly sign up for delete account!!!");
             }
             return ResponseEntity.badRequest().body("System has error!!!");


             }catch(Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }


    @PostMapping("/deleteAccount")
    public ResponseEntity deleteAccount(@RequestParam Long id, @RequestHeader String token){
        try{
            String deleteMsg = userService.deleteAccount(id, token);
            switch (deleteMsg){
                case "OK" : return ResponseEntity.accepted().body("You are deleted account successfully");
                case "ErrorDelete" : return ResponseEntity.badRequest().body("System have some problems by deleting account!!!");
                case "notAdmin" : return ResponseEntity.badRequest().body("You are not admin!!!");
                case "notSigned" : return ResponseEntity.badRequest().body("You are not signed!!! Please. firstly sign up for delete account!!!");
            }
            return ResponseEntity.badRequest().body("System has error!!!");


        }catch(Exception e){
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/addService")
    public ResponseEntity addService(@RequestBody AddServiceRequest request) {
        userService.addServiceToUser(request.getUserId(), request.getServiceId());
        return ResponseEntity.ok().body("");
    }

    @PostMapping("/insertMoney")
    public HttpEntity<? extends Serializable> insertMoney(@RequestBody Money money, @RequestHeader String token) {
        try {
            String check = userService.insertMoney(money, token);
            switch (check) {
                case "MoneyInserted":
                    return ResponseEntity.accepted().body("Money has successfully inserted");
                case "EnterAmount":
                    return ResponseEntity.accepted().body("Please, enter amount of money you want to take");
                case "LoginPlease":
                    return ResponseEntity.accepted().body("You have to login");
            }
            userService.insertMoney(money, token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
        return ResponseEntity.badRequest().body("Error");
    }

    @PostMapping("/takeMoney")
    public ResponseEntity takeMoney(@RequestBody Money money, @RequestHeader String token) {
        try {
            String check = userService.takeMoney(money, token);
            switch (check) {
                case "MoneyTaken":
                    return ResponseEntity.accepted().body("Money has successfully taken");
                case "NotEnough":
                    return ResponseEntity.accepted().body("Not enough funds");
                case "EnterAmount":
                    return ResponseEntity.accepted().body("Please, enter amount of money you want to take");
                case "LoginPlease":
                    return ResponseEntity.accepted().body("You have to login");
            }
            userService.takeMoney(money, token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
        return ResponseEntity.badRequest().body("Error");
    }

    @PostMapping("/sendMoney")
    public ResponseEntity sendMoney(@RequestBody Money money, @RequestParam String login1, @RequestHeader String token) {
        try {
            String check = userService.sendMoney(money, login1, token);
            switch (check) {
                case "MoneyTaken":
                    return ResponseEntity.accepted().body("Money has successfully sent");
                case "NotEnough":
                    return ResponseEntity.accepted().body("Not enough funds");
                case "EnterAmount":
                    return ResponseEntity.accepted().body("Please, enter amount of money you want to sent");
                case "LoginPlease":
                    return ResponseEntity.accepted().body("You have to login");
            }
            userService.takeMoney(money, token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
        return ResponseEntity.badRequest().body("Error");
    }

}
