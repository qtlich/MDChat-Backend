package com.programming.man.mdchat.controller;

import com.programming.man.mdchat.dto.BanReasonResponseDto;
import com.programming.man.mdchat.service.AuthService;
import com.programming.man.mdchat.service.UserManagementService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/user-management")
@CrossOrigin
@AllArgsConstructor
@Slf4j
public class UserManagementController {
    private final UserManagementService userManagementService;

    @CrossOrigin()
    @GetMapping("banreasons/get")
    public ResponseEntity<List<BanReasonResponseDto>> getBanReasons() {
        return status(HttpStatus.OK).body(userManagementService.getBanReasons());
    }
}
