package com.programming.man.mdchat.service;

import com.programming.man.mdchat.dto.*;
import com.programming.man.mdchat.exceptions.SpringMDChatException;
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

    @Transactional(readOnly = false)
    public List<OperationResultDto> signup(SignupUserRequest request) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("signUpUser")
                                                            .registerStoredProcedureParameter("p_currentUserId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_userName", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_email", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_password", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_description", String.class, ParameterMode.IN)
                                                            .setParameter("p_currentUserId", isLoggedIn() ? getCurrentUser().getId() : null)
                                                            .setParameter("p_userName", request.getUsername())
                                                            .setParameter("p_email", request.getEmail())
                                                            .setParameter("p_password",  passwordEncoder.encode(request.getPassword()))
                                                            .setParameter("p_description",request.getDescription());
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
    public List<OperationResultDto> changeUserInfo(ChangeUserInfoDto request) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("changeUserInfo")
                                                            .registerStoredProcedureParameter("p_currentUserId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_newUserName", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_newEmail", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_newPassword", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_description", String.class, ParameterMode.IN)
                                                            .setParameter("p_currentUserId", isLoggedIn() ? getCurrentUser().getId() : null)
                                                            .setParameter("p_userId", request.getUserid())
                                                            .setParameter("p_newUserName", request.getUsername())
                                                            .setParameter("p_newEmail", request.getEmail())
                                                            .setParameter("p_newPassword", passwordEncoder.encode(request.getPassword()))
                                                            .setParameter("p_description", request.getDescription());
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