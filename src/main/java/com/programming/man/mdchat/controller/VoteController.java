package com.programming.man.mdchat.controller;

import com.programming.man.mdchat.dto.GetCountPostVotesRequestDto;
import com.programming.man.mdchat.dto.GetUserPostVotesResponseDto;
import com.programming.man.mdchat.dto.OperationResultDto;
import com.programming.man.mdchat.dto.VoteRequestDto;
import com.programming.man.mdchat.service.VoteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/votes")
@CrossOrigin
@AllArgsConstructor
@Slf4j
public class VoteController {

    private final VoteService voteService;

    @PostMapping(value = "vote")
    public ResponseEntity<List<OperationResultDto>> vote(@RequestBody VoteRequestDto voteDto) {
        return status(HttpStatus.OK).body(voteService.vote(voteDto));
    }

//    @PostMapping(value = "getvotes")
//    public ResponseEntity<GetUserPostVotesResponseDto> vote(@RequestBody GetCountPostVotesRequestDto request) {
//        return status(HttpStatus.OK).body(new GetUserPostVotesResponseDto(voteService.getPostVotes(request)));
//    }
}
