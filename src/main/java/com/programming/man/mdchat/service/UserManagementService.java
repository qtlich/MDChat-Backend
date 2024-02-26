package com.programming.man.mdchat.service;

import com.programming.man.mdchat.dto.BanReasonResponseDto;
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
@Transactional
public class UserManagementService {

    @PersistenceContext(name = "MDCHAT")
    private EntityManager entityManager;
    private final AuthService authService;

    @Transactional(readOnly = false)
    public List<BanReasonResponseDto> getBanReasons() {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("getBanReasons")
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .setParameter("p_currentUserId", authService.isLoggedIn() ? authService.getCurrentUser().getId() : null);
        List<BanReasonResponseDto> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();
            if (resultObjects == null) {
                result = new ArrayList();
            } else

                result = resultObjects.stream()
                                      .map(item -> new BanReasonResponseDto((Long) item[0],
                                                                            (String) item[1]))
                                      .collect(Collectors.toList());
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }


}