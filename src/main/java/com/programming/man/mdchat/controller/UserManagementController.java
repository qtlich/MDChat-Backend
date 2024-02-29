package com.programming.man.mdchat.controller;

import com.programming.man.mdchat.dto.*;
import com.programming.man.mdchat.service.UserManagementService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("search-users")
    public ResponseEntity<List<SearchUsersResponseDto>> searchUsers(@RequestBody SearchUsersRequestDto request) {
        return status(HttpStatus.OK).body(userManagementService.searchUsers(request));
    }

    @PostMapping("get-channel-banned-users")
    public ResponseEntity<List<GetChannelBannedUsersResponseDto>> getChannelBannedUsers(@RequestBody GetChannelBannedUsersRequestDto request) {
        return status(HttpStatus.OK).body(userManagementService.getChannelBannedUsers(request));
    }

    @PostMapping("get-channel-moderator-users")
    public ResponseEntity<List<GetChannelModeratorUsersResponseDto>> getChannelModeratorUsers(@RequestBody GetChannelModeratorUsersRequestDto request) {
        return status(HttpStatus.OK).body(userManagementService.getChannelModeratorUsers(request));
    }

    @PostMapping("manage-user-channel-banning")
    public ResponseEntity<List<ManageUserChannelBanningResponseDto>> banUnbanUserInChannel(@RequestBody ManageUserChannelBanningRequestDto request) {
        return status(HttpStatus.OK).body(userManagementService.banUnbanUserInChannel(request));
    }

    @PostMapping("is-user-banned")
    public ResponseEntity<GetIsUserBannedInChannelResponseDto> isU(@RequestBody GetIsUserBannedInChannelRequestDto request) {
        return status(HttpStatus.OK).body(userManagementService.isUserBannedInChannel(request));
    }

}
