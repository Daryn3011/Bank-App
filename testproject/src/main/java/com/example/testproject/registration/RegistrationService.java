//package com.example.testproject.registration;
//
//import com.example.testproject.registration.token.ConfirmToken;
//import com.example.testproject.registration.token.ConfirmTokenService;
//import com.example.testproject.user.User;
//import com.example.testproject.user.UserRole;
//import com.example.testproject.user.UserServiceSecurity;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//
//@Service
//@AllArgsConstructor
//public class RegistrationService {
//    private final UserServiceSecurity userServiceSecurity;
//    private final EmailValidator emailValidator;
//    private final ConfirmTokenService confirmTokenService;
//
//    public String register(RegistrationRequest request) {
//        boolean isValidEmail = emailValidator.test(request.getEmail());
//        if (!isValidEmail ){
//            throw new IllegalStateException("email not valid");
//        }
//
//
//        String token = userServiceSecurity.signUpUser(
//                new User(request.getUsername(),
//                        request.getEmail(),
//                        request.getPassword(),
//                        UserRole.USER
//
//                )
//        );
//
//        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
//        return token;
//    }
//
//    @Transactional
//    public String confirmToken(String token){
//        ConfirmToken confirmToken = confirmTokenService
//                .getToken(token)
//                .orElseThrow(() ->
//                        new IllegalStateException("token not found"));
//
//        if (confirmToken.getConfirmedAt() != null) {
//            throw new IllegalStateException("email already confirmed");
//        }
//
//        LocalDateTime expiredAt = confirmToken.getExpiresAt();
//
//        if (expiredAt.isBefore(LocalDateTime.now())) {
//            throw new IllegalStateException("token expired");
//        }
//
//        confirmTokenService.setConfirmedAt(token);
//        userServiceSecurity.enableUser(
//                confirmToken.getUser().getEmail());
//        return "confirmed";
//
//
//
//    }
//}
