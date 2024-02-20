package com.programming.man.mdchat.service;

import com.programming.man.mdchat.dto.GetCountPostVotesRequestDto;
import com.programming.man.mdchat.dto.GetUserPostVotesResponseDto;
import com.programming.man.mdchat.dto.VoteRequestDto;
import com.programming.man.mdchat.dto.VoteResponseDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.AllArgsConstructor;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class VoteService {

    private final AuthService authService;
    @PersistenceContext(name = "MDCHAT")
    private EntityManager entityManager;

    @Transactional
    public GetUserPostVotesResponseDto getVotes(GetCountPostVotesRequestDto request) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("votes")
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_postId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_commentId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_isVoted", Integer.class, ParameterMode.OUT)
                                                            .registerStoredProcedureParameter("p_countVotes", Long.class, ParameterMode.OUT)
                                                            .registerStoredProcedureParameter("p_upVoted", Integer.class, ParameterMode.OUT)
                                                            .registerStoredProcedureParameter("p_downVoted", Integer.class, ParameterMode.OUT)
                                                            .registerStoredProcedureParameter("p_voteMoment", String.class, ParameterMode.OUT)
                                                            .setParameter("p_userId", authService.isLoggedIn() ? authService.getCurrentUser().getId() : null)
                                                            .setParameter("p_postId", request.getPostId())
                                                            .setParameter("p_commentId", request.getCommentId());

        GetUserPostVotesResponseDto result;
        try {
            storedProcedure.execute();
            result = new GetUserPostVotesResponseDto(request.getPostId(),
                                                     request.getCommentId(),
                                                     (Integer) storedProcedure.getOutputParameterValue("p_isVoted") == 1,
                                                     (Long) storedProcedure.getOutputParameterValue("p_countVotes"),
                                                     (Integer) storedProcedure.getOutputParameterValue("p_upVoted") == 1,
                                                     (Integer) storedProcedure.getOutputParameterValue("p_downVoted") == 1,
                                                     (String) storedProcedure.getOutputParameterValue("p_voteMoment"));
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }

    @Transactional
    public VoteResponseDto vote(VoteRequestDto vote) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("vote")
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_postId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_commentId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_voteType", Integer.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_voteCounted", Integer.class, ParameterMode.OUT)
                                                            .registerStoredProcedureParameter("p_countVotes", Long.class, ParameterMode.OUT)
                                                            .registerStoredProcedureParameter("id", Integer.class, ParameterMode.OUT)
                                                            .registerStoredProcedureParameter("message", String.class, ParameterMode.OUT)
                                                            .setParameter("p_userId", authService.getCurrentUser().getId())
                                                            .setParameter("p_postId", vote.getPostId())
                                                            .setParameter("p_commentId", vote.getCommentId())
                                                            .setParameter("p_voteType", vote.getVoteType());

        VoteResponseDto result;
        try {
            storedProcedure.execute();
            result = new VoteResponseDto(vote.getPostId(),
                                         vote.getCommentId(),
                                         (Long) storedProcedure.getOutputParameterValue("p_countVotes"),
                                         vote.getVoteType(),
                                           ((Integer) storedProcedure.getOutputParameterValue("p_voteCounted") == 1) && vote.getVoteType() == 1,
                                           ((Integer) storedProcedure.getOutputParameterValue("p_voteCounted") == 1) && vote.getVoteType() == -1,
                                         (Integer) storedProcedure.getOutputParameterValue("id"),
                                         (String) storedProcedure.getOutputParameterValue("message"));
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }
}
