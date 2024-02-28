package com.programming.man.mdchat.service;

import com.programming.man.mdchat.dto.*;
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
                                                            .setParameter("p_userId", authService.isLoggedIn() ? authService.getCurrentUser().getId() : null);
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

    @Transactional(readOnly = false)
    public List<SearchUsersResponseDto> searchUsers(SearchUsersRequestDto request) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("searchUsers")
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_userName", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_offset", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_limit", Long.class, ParameterMode.IN)
                                                            .setParameter("p_userId", authService.isLoggedIn() ? authService.getCurrentUser().getId() : null)
                                                            .setParameter("p_userName", request.getUserName())
                                                            .setParameter("p_offset", request.getOffset())
                                                            .setParameter("p_limit", request.getLimit());
        List<SearchUsersResponseDto> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();
            if (resultObjects == null) {
                result = new ArrayList();
            } else
                result = resultObjects.stream()
                                      .map(item -> new SearchUsersResponseDto((Long) item[0],
                                                                              (String) item[1],
                                                                              (String) item[2],
                                                                              (String) item[3],
                                                                              (String) item[4],
                                                                              (String) item[5],
                                                                              (Boolean) item[6],
                                                                              (String) item[7]))
                                      .collect(Collectors.toList());
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }

    @Transactional(readOnly = false)
    public List<GetChannelBannedUsersResponseDto> getChannelBannedUsers(GetChannelBannedUsersRequestDto request) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("getChannelBannedUsers")
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_channelId", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_offset", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_limit", Long.class, ParameterMode.IN)
                                                            .setParameter("p_userId", authService.isLoggedIn() ? authService.getCurrentUser().getId() : null)
                                                            .setParameter("p_channelId", request.getChannelId())
                                                            .setParameter("p_offset", request.getOffset())
                                                            .setParameter("p_limit", request.getLimit());

        List<GetChannelBannedUsersResponseDto> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();
            if (resultObjects == null) {
                result = new ArrayList();
            } else

                result = resultObjects.stream()
                                      .map(item -> new GetChannelBannedUsersResponseDto((Long) item[0],
                                                                                        (Long) item[1],
                                                                                        (Short) item[2],
                                                                                        (String) item[3],
                                                                                        (Long) item[4],
                                                                                        (String) item[5],
                                                                                        (Long) item[6],
                                                                                        (String) item[7],
                                                                                        (String) item[8],
                                                                                        (Short) item[9],
                                                                                        (Boolean) item[10],
                                                                                        (Long) item[11],
                                                                                        (String) item[12],
                                                                                        (String) item[13]))
                                      .collect(Collectors.toList());
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }

    @Transactional(readOnly = false)
    public List<GetChannelModeratorUsersResponseDto> getChannelModeratorUsers(GetChannelModeratorUsersRequestDto request) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("getChannelModerators")
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_channelId", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_offset", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_limit", Long.class, ParameterMode.IN)
                                                            .setParameter("p_userId", authService.isLoggedIn() ? authService.getCurrentUser().getId() : null)
                                                            .setParameter("p_channelId", request.getChannelId())
                                                            .setParameter("p_offset", request.getOffset())
                                                            .setParameter("p_limit", request.getLimit());

        List<GetChannelModeratorUsersResponseDto> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();
            if (resultObjects == null) {
                result = new ArrayList();
            } else

                result = resultObjects.stream()
                                      .map(item -> new GetChannelModeratorUsersResponseDto((Long) item[0],
                                                                                           (Long) item[1],
                                                                                           (Short) item[2],
                                                                                           (String) item[3],
                                                                                           (Long) item[4],
                                                                                           (String) item[5],
                                                                                           (Long) item[6],
                                                                                           (String) item[7],
                                                                                           (String) item[8],
                                                                                           (String) item[9],
                                                                                           (Boolean) item[10],
                                                                                           (Boolean) item[11],
                                                                                           (Boolean) item[12],
                                                                                           (Boolean) item[13],
                                                                                           (Boolean) item[14],
                                                                                           (Boolean) item[15],
                                                                                           (Boolean) item[16]))
                                      .collect(Collectors.toList());
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }

    @Transactional(readOnly = false)
    public List<ManageUserChannelBanningResponseDto> banUnbanUserInChannel(ManageUserChannelBanningRequestDto request) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("manageUserChannelBanning")
                                                            .registerStoredProcedureParameter("p_currentUserId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_channelId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_banReasonId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_note", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_daysBanned", Short.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_permanentBanned", Boolean.class, ParameterMode.IN)
                                                            .setParameter("p_currentUserId", authService.isLoggedIn() ? authService.getCurrentUser().getId() : null)
                                                            .setParameter("p_userId", request.getUserId())
                                                            .setParameter("p_channelId", request.getChannelId())
                                                            .setParameter("p_banReasonId", request.getBanReasonId())
                                                            .setParameter("p_note", request.getNote())
                                                            .setParameter("p_daysBanned", request.getDaysBanned())
                                                            .setParameter("p_permanentBanned", request.getPermanentBanned());

        List<ManageUserChannelBanningResponseDto> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();
            if (resultObjects == null) {
                result = new ArrayList();
            } else

                result = resultObjects.stream()
                                      .map(item -> new ManageUserChannelBanningResponseDto((Long) item[0],
                                                                                           (String) item[1]))
                                      .collect(Collectors.toList());
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }

    @Transactional
    public GetIsUserBannedInChannelResponseDto isUserBannedInChannel(GetIsUserBannedInChannelRequestDto request) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("isUserBannedInChannel")
                                                            .registerStoredProcedureParameter("p_currentUserId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_channelId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("out_isBannned", Boolean.class, ParameterMode.OUT)
                                                            .setParameter("p_currentUserId", authService.isLoggedIn() ? authService.getCurrentUser().getId() : null)
                                                            .setParameter("p_userId", request.getUserId())
                                                            .setParameter("p_channelId", request.getChannelId());

        GetIsUserBannedInChannelResponseDto result;
        try {
            storedProcedure.execute();
            result = new GetIsUserBannedInChannelResponseDto(request.getUserId(),
                                                             request.getChannelId(),
                                                             (Boolean) storedProcedure.getOutputParameterValue("out_isBannned"));
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }


}