package com.programming.man.mdchat.controller;

import com.programming.man.mdchat.dto.ChannelDto;
import com.programming.man.mdchat.dto.CommentsDto;
import com.programming.man.mdchat.dto.SearchChannelsRequestDto;
import com.programming.man.mdchat.dto.SearchChannelsResponseDto;
import com.programming.man.mdchat.service.ChannelService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/channel")
@CrossOrigin
@AllArgsConstructor
@Slf4j
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping("/create")
    public ResponseEntity<ChannelDto> createChannel(@RequestBody ChannelDto channelDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(channelService.save(channelDto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ChannelDto>> getAllChannels() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(channelService.getAll());
    }
    @GetMapping("/search")
    public ResponseEntity<List<SearchChannelsResponseDto>> searchChannels(@RequestBody SearchChannelsRequestDto channelName) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(channelService.searchChannels(channelName.getChannelName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChannelDto> getChannel(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(channelService.getChannel(id));
    }
}
