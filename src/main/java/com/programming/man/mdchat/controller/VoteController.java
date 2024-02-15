package com.programming.man.mdchat.controller;

import com.programming.man.mdchat.dto.*;
import com.programming.man.mdchat.service.VoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/votes")
@CrossOrigin
@AllArgsConstructor
@Slf4j
public class VoteController {

    private final VoteService voteService;

    @PostMapping(value = "vote")
    public ResponseEntity<VoteResponseV1Dto> vote(@RequestBody VoteRequestV1Dto voteDto) {
        return status(HttpStatus.OK).body(voteService.vote(voteDto));
    }

    @PostMapping(value = "get")
    public ResponseEntity<GetUserPostVotesResponseDto> vote(@RequestBody GetCountPostVotesRequestDto request) {
        return status(HttpStatus.OK).body(voteService.getVotes(request));
    }
}
