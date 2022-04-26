//package com.example.testproject.user;
//
//import com.example.testproject.registration.token.ConfirmToken;
//import com.example.testproject.registration.token.ConfirmTokenService;
//import lombok.AllArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Service
//@AllArgsConstructor
//
//public class UserServiceSecurity implements UserDetailsService {
//
//    private final static String USER_NOT_FOUND_MESSAGE =
//            "user with email %s not found";
//
//    private final UserRepositorySecurity userRepositorySecurity;
//
//    private final BCryptPasswordEncoder bCryptPasswordEncoder;
//    private final ConfirmTokenService confirmTokenService;
//
//    @Override
//    public UserDetails loadUserByUsername(String email)
//            throws UsernameNotFoundException {
//        return userRepositorySecurity.findByEmail(email)
//                .orElseThrow( ()-> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MESSAGE,email)) );
//    }
//
//    public String signUpUser(User user){
//        boolean userExist = userRepositorySecurity.findByEmail(user.getEmail())
//                .isPresent();
//
//        if (userExist){
//            throw new IllegalStateException("email already taken");
//        }
//        String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
//
//        user.setPassword(encodePassword);
//
//        userRepositorySecurity.save(user);
//
//        String token = UUID.randomUUID().toString();
//
//        // TODO: sen conf  token
//        ConfirmToken confirmToken = new ConfirmToken(
//                token,
//                LocalDateTime.now(),
//                LocalDateTime.now().plusMinutes(15),
//                user
//
//        );
//
//        confirmTokenService.saveConfirmationToken(
//                confirmToken
//        );
//
//
//        return token;
//    }
//
//    public int enableUser(String email){
//        return userRepositorySecurity.enableUser(email);
//    }
//}
