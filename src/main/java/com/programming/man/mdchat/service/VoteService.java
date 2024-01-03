package com.programming.man.mdchat.service;

import com.programming.man.mdchat.dto.GetUserPostVotesResponseDto;
import com.programming.man.mdchat.dto.OperationResultDto;
import com.programming.man.mdchat.dto.VoteRequestDto;
import com.programming.man.mdchat.mapper.OperationResultMapper;
import com.programming.man.mdchat.dto.GetCountPostVotesRequestDto;
import com.programming.man.mdchat.repository.VoteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.AllArgsConstructor;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VoteService {

    @PersistenceContext(name = "MDCHAT")
    private EntityManager entityManager;
    private final VoteRepository voteRepository;
    private final AuthService authService;
    private OperationResultMapper operationResultMapper;

//    @Transactional(readOnly = false)
//    public GetUserPostVotesResponseDto getPostVotes(GetCountPostVotesRequestDto request) {
//        return voteRepository.getCountPostVotes(authService.getCurrentUser().getId(), request.getPostId());
//    }

    @Transactional(readOnly = false)
    public List<OperationResultDto> vote(VoteRequestDto vote) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("vote")
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_postId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_commentId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_voteType", Short.class, ParameterMode.IN)
                                                            .setParameter("p_userId", authService.getCurrentUser().getId())
                                                            .setParameter("p_postId", vote.getPostId())
                                                            .setParameter("p_commentId", vote.getCommentId())
                                                            .setParameter("p_voteType", vote.getVoteType());
        List<OperationResultDto> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();
            System.out.println(resultObjects);
            if (resultObjects == null) {
                result = new ArrayList<OperationResultDto>();
                result.add(new OperationResultDto(-vote.getPostId(), "Can't vote"));
            } else
                result = resultObjects.stream()
                                      .map(item -> new OperationResultDto((Long) item[0],
                                                                          (String) item[1]))
                                      .collect(Collectors.toList());
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }
}
