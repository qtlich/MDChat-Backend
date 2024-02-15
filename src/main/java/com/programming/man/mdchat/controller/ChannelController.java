package com.programming.man.mdchat.controller;

import com.programming.man.mdchat.dto.*;
import com.programming.man.mdchat.service.AuthService;
import com.programming.man.mdchat.service.ChannelService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
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

    @GetMapping("/all")
    public ResponseEntity<List<ChannelDto>> getAllChannels() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(channelService.getAll());
    }
    @PostMapping("/search")
    public ResponseEntity<List<SearchChannelsResponseDto>> searchChannels(@RequestBody SearchChannelsRequestDto searchChannelRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(channelService.searchChannels(searchChannelRequest.getChannelName(),  searchChannelRequest.getDescLength(),searchChannelRequest.getSearchMode()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChannelDto> getChannel(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(channelService.getChannel(id));
    }
}
