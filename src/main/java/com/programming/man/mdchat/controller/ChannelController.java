package com.programming.man.mdchat.controller;

import com.programming.man.mdchat.dto.*;
import com.programming.man.mdchat.service.ChannelService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/channel")
@CrossOrigin
@AllArgsConstructor
@Slf4j
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping(value = "cud") //channel/cud
    public ResponseEntity<List<ChannelCUDResponse>> createPost(@RequestBody ChannelCUDRequest request) {
        return status(HttpStatus.OK).body(channelService.channelCUD(request));
    }

    @PostMapping(value = "universal-channel-posts")
    public ResponseEntity<List<GetChannelPostsResponseUniversalDto>> getChannelPostsUniversal(@RequestBody GetChannelPostsRequestUniversalDto request) {
        return status(HttpStatus.OK).body(channelService.getChannelPostsUniversal(request));
    }

    @PostMapping(value = "get-description")
    public ResponseEntity<GetChannelDescriptionResponseDto> getChannelDescription(@RequestBody GetChannelDescriptionRequestDto request) {
        return status(HttpStatus.OK).body(channelService.getChannelDescription(request));
    }

    @PostMapping("/search")
    public ResponseEntity<List<SearchChannelsResponseDto>> searchChannels(@RequestBody SearchChannelsRequestDto request) {
        return status(HttpStatus.OK).body(channelService.searchChannels(request));
    }

    @PostMapping("/change-subscription")
    public ResponseEntity<List<ChangeUserChannelSubscriptionResponseDto>> changeUserChannelSubscription(@RequestBody ChangeUserChannelSubscriptionRequestDto request) {
        return status(HttpStatus.OK).body(channelService.changeUserChannelSubscription(request));
    }

    @PostMapping("/get-subscription")
    public ResponseEntity<GetUserChannelSubscriptionResponseDto> getUserChannelSubscription(@RequestBody GetUserChannelSubscriptionRequestDto request) {
        return status(HttpStatus.OK).body(channelService.getUserChannelSubscription(request));
    }

    @PostMapping("/get-count-subscribers")
    public ResponseEntity<GetChannelCountSubscribersResponseDto> getChannelCountSubscribers(@RequestBody GetChannelCountSubscribersRequestDto request) {
        return status(HttpStatus.OK).body(channelService.getChannelCountSubscribers(request));
    }
}
