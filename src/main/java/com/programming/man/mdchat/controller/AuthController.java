package com.programming.man.mdchat.controller;

import com.programming.man.mdchat.dto.*;
import com.programming.man.mdchat.service.AuthService;
import com.programming.man.mdchat.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@AllArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    @CrossOrigin()
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        return authService.signup(registerRequest);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("Your Account Activated Successfully", OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully!");
    }

    @PostMapping("/changeuserinfo")
    public ResponseEntity<List<OperationResultDto>> changeUserInfo(@Valid @RequestBody ChangeUserInfoDto userInfo) {
        return ResponseEntity.status(OK).body(authService.changeUserInfo(userInfo));
    }

    @PostMapping("/getusersinfo")
    public ResponseEntity<List<UserInfoResponseDto>> getUserInfo(@Valid @RequestBody UserInfoRequest userInfo) {
        return ResponseEntity.status(OK).body(authService.getUserInfo(userInfo.getUserIdentifier()));
    }
}
