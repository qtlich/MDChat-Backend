package com.programming.man.mdchat.controller;

import com.programming.man.mdchat.dto.VoteDto;
import com.programming.man.mdchat.service.VoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/votes")
@CrossOrigin
@AllArgsConstructor
@Slf4j
public class VoteController {

    private final VoteService voteService;

    @PostMapping(value = "vote")
    public ResponseEntity<Void> vote(@RequestBody VoteDto voteDto) {
        voteService.vote(voteDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
