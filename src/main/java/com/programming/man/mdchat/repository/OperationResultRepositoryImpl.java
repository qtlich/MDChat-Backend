package com.programming.man.mdchat.repository;

import com.programming.man.mdchat.dto.OperationResultDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OperationResultRepositoryImpl implements OperationResultRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<OperationResultDto> deletePostByPostId(Long postId) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("deletePost");
        storedProcedure.registerStoredProcedureParameter("postId", Long.class, ParameterMode.IN);
        storedProcedure.setParameter("postId", postId);
        storedProcedure.execute();
        return (List<OperationResultDto>) storedProcedure.getResultList();
    }
}
