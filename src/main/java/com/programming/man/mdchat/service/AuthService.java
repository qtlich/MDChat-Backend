package com.programming.man.mdchat.service;

import com.programming.man.mdchat.dto.*;
import com.programming.man.mdchat.exceptions.SpringMDChatException;
import com.programming.man.mdchat.model.NotificationEmail;
import com.programming.man.mdchat.model.User;
import com.programming.man.mdchat.model.VerificationToken;
import com.programming.man.mdchat.repository.UserRepository;
import com.programming.man.mdchat.repository.VerificationTokenRepository;
import com.programming.man.mdchat.security.JwtProvider;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.AllArgsConstructor;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.OK;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    @PersistenceContext(name = "MDCHAT")
    private EntityManager entityManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final MailService mailService;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    public ResponseEntity<String> signup(RegisterRequest registerRequest) {

            User user = new User();
            user.setUsername(registerRequest.getUsername());
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setCreated(Instant.now());
            user.setEnabled(true);
            userRepository.save(user);

            String token = generateVerificationToken(user);
//            mailService.sendMail(new NotificationEmail("Please Activate your Account",
//                                                       user.getEmail(), "Thank you for signing up to Spring Reddit, " +
//                                                                        "please click on the below url to activate your account : " +
//                                                                        "<a href://http://localhost:8080/api/auth/accountVerification/" + token + "> Activate account link</a>"));

        return new ResponseEntity<>("Success registration", OK);
    }

    @Transactional(readOnly = true)
    public User getCurrentUser() {
        Jwt principal = (Jwt) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getSubject())
                             .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringMDChatException("User not found with name - " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringMDChatException("Invalid Token")));
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                                                                                                                 loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
//        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println("getCurrentUser().getId()");
//        System.out.println(getCurrentUser());
        return AuthenticationResponse.builder()
                                     .authenticationToken(token)
                                     .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                                     .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                                     .username(loginRequest.getUsername())
                                     .userid(0L)
                                     .build();
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                                     .authenticationToken(token)
                                     .refreshToken(refreshTokenRequest.getRefreshToken())
                                     .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                                     .username(refreshTokenRequest.getUsername())
                                     .build();
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }

    //    public List<OperationResultDto> changeUserInfoBySpring(ChangeUserInfoDto userInfo) {
//
////        User user = userRepository.findByUsername(userRepository. username).orElseThrow(() -> new SpringMDChatException("User not found with name - " + username));
////        user.setEnabled(true);
////        userRepository.save(user);
////
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//    }
    @Transactional()
    public List<UserInfoResponseDto> getUserInfo(String userIdentifier) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("getUserInfo")
                                                            .registerStoredProcedureParameter("p_userIdentifier", String.class, ParameterMode.IN)
                                                            .setParameter("p_userIdentifier", userIdentifier);
        List<UserInfoResponseDto> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();
            if (resultObjects == null) {
                result = new ArrayList();
            } else
                result = resultObjects.stream()
                                      .map(item -> new UserInfoResponseDto((Long) item[0],
                                                                           (String) item[1],
                                                                           (String) item[2],
                                                                           (String) item[3],
                                                                           (String) item[4],
                                                                           (String) item[5],
                                                                           (Boolean) item[6]))
                                      .collect(Collectors.toList());
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }

    @Transactional
    public List<OperationResultDto> changeUserInfo(ChangeUserInfoDto userInfo) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("changeUserInfo")
                                                            .registerStoredProcedureParameter("currentUserId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("newUserName", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("newEmail", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("newPassword", String.class, ParameterMode.IN)
                                                            .setParameter("currentUserId", getCurrentUser().getId())
                                                            .setParameter("userId", userInfo.getUserid())
                                                            .setParameter("newUserName", userInfo.getUsername())
                                                            .setParameter("newEmail", userInfo.getEmail())
                                                            .setParameter("newPassword", passwordEncoder.encode(userInfo.getPassword()));
        List<OperationResultDto> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();
            if (resultObjects == null) {
                result = new ArrayList();
                result.add(new OperationResultDto(-1L, "Can't change user info"));
            } else

                result = resultObjects.stream()
                                      .map(item -> new OperationResultDto((Long) item[0],
                                                                          (String) item[1]))
                                      .collect(Collectors.toList());
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }
}