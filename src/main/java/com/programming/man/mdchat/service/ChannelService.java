package com.programming.man.mdchat.service;

import com.programming.man.mdchat.dto.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ChannelService {
    private final AuthService authService;
    @PersistenceContext(name = "MDCHAT")
    private EntityManager entityManager;

    @Transactional(readOnly = false)
    public List<ChannelCUDResponse> channelCUD(ChannelCUDRequest request) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("channelCUD")
                                                            .registerStoredProcedureParameter("p_operationType", Integer.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_channelId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_channelType", Short.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_channelName", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_channelDescription", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_Deleted", Boolean.class, ParameterMode.IN)
                                                            .setParameter("p_operationType", request.getOperationType())
                                                            .setParameter("p_userId", authService.isLoggedIn() ? (Long) authService.getCurrentUser().getId() : null)
                                                            .setParameter("p_channelId", request.getChannelId())
                                                            .setParameter("p_channelType", request.getChannelTypeId())
                                                            .setParameter("p_channelName", request.getChannelName())
                                                            .setParameter("p_channelDescription", request.getChannelDescription())
                                                            .setParameter("p_Deleted", request.getDeleted());
        List<ChannelCUDResponse> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();
            if (resultObjects == null) {
                result = new ArrayList();
            } else
                result = resultObjects.stream()
                                      .map(item -> new ChannelCUDResponse((Long) item[0], //id
                                                                          (String) item[1]))//message
                                      .collect(Collectors.toList());
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }

    @Transactional
    public GetUserChannelSubscriptionResponseDto getUserChannelSubscription(GetUserChannelSubscriptionRequestDto request) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("getUserChannelSubscription")
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_channelId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("out_isSubscribed", Boolean.class, ParameterMode.OUT)
                                                            .setParameter("p_userId", authService.isLoggedIn() ? authService.getCurrentUser().getId() : null)
                                                            .setParameter("p_channelId", request.getChannelId());

        GetUserChannelSubscriptionResponseDto result;
        try {
            storedProcedure.execute();
            result = new GetUserChannelSubscriptionResponseDto(authService.isLoggedIn() ? authService.getCurrentUser().getId() : null,
                                                               request.getChannelId(),
                                                               (Boolean) storedProcedure.getOutputParameterValue("out_isSubscribed"));
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }

    @Transactional
    public GetChannelCountSubscribersResponseDto getChannelCountSubscribers(GetChannelCountSubscribersRequestDto request) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("getChannelCountSubscribers")
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_channelId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("out_countSubscribers", Long.class, ParameterMode.OUT)
                                                            .setParameter("p_userId", authService.isLoggedIn() ? authService.getCurrentUser().getId() : null)
                                                            .setParameter("p_channelId", request.getChannelId());

        GetChannelCountSubscribersResponseDto result;
        try {
            storedProcedure.execute();
            result = new GetChannelCountSubscribersResponseDto(request.getChannelId(),
                                                               (Long) storedProcedure.getOutputParameterValue("out_countSubscribers"));
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }

    @Transactional(readOnly = false)
    public List<ChangeUserChannelSubscriptionResponseDto> changeUserChannelSubscription(ChangeUserChannelSubscriptionRequestDto request) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("changeUserChannelSubscription")
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_channelId", Long.class, ParameterMode.IN)
                                                            .setParameter("p_userId", authService.isLoggedIn() ? (Long) authService.getCurrentUser().getId() : null)
                                                            .setParameter("p_channelId", request.getChannelId());
        List<ChangeUserChannelSubscriptionResponseDto> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();
            if (resultObjects == null) {
                result = new ArrayList();
            } else
                result = resultObjects.stream()
                                      .map(item -> new ChangeUserChannelSubscriptionResponseDto((Long) item[0], //id
                                                                                                (String) item[1]))//message
                                      .collect(Collectors.toList());
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }

    @Transactional(readOnly = false)
    public List<GetChannelPostsResponseUniversalDto> getChannelPostsUniversal(GetChannelPostsRequestUniversalDto request) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("getChannelPostsUniversal")
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_channelId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_channelName", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("selectedUserView", Integer.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_sortMode", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_offset", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_limit", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_postNameMaxLength", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_postDescriptionMaxLength", Long.class, ParameterMode.IN)
                                                            .setParameter("p_userId", request.getUserId() != null ? request.getUserId() : authService.isLoggedIn() ? (Long) authService.getCurrentUser().getId() : null)
                                                            .setParameter("p_channelId", request.getChannelId())
                                                            .setParameter("p_channelName", request.getChannelName())
                                                            .setParameter("selectedUserView", request.getSelectedUserView())
                                                            .setParameter("p_sortMode", request.getSortMode())
                                                            .setParameter("p_offset", request.getOffset())
                                                            .setParameter("p_limit", request.getLimit())
                                                            .setParameter("p_postNameMaxLength", request.getPostNameMaxLength())
                                                            .setParameter("p_postDescriptionMaxLength", request.getPostDescriptionMaxLength());
        List<GetChannelPostsResponseUniversalDto> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();

            if (resultObjects == null) {
                result = new ArrayList();
            } else
                result = resultObjects.stream()
                                      .map(item -> new GetChannelPostsResponseUniversalDto((Long) item[0], //postId
                                                                                           (Long) item[1], //postChannelId
                                                                                           (Short) item[2],//channelType
                                                                                           (String) item[3],//channelName
                                                                                           (String) item[4],//channelDescription
                                                                                           (Long) item[5],//postUserId
                                                                                           (String) item[6],//postUserName
                                                                                           (String) item[7],//postName
                                                                                           (String) item[8],//postDescription
                                                                                           (Integer) item[9],//postVoteCount
                                                                                           (String) item[10],//postCreated
                                                                                           (String) item[11],//postModified
                                                                                           (String) item[12],//postUrl
                                                                                           (Integer) item[13],//currentUserVoteType
                                                                                           (Long) item[14],//postCountComments
                                                                                           (String) item[15],//posttimeAgo
                                                                                           (String) item[16],//lastVisaited
                                                                                           (Boolean) item[17],//hidden
                                                                                           (Boolean) item[18],//saved
                                                                                           (Boolean) item[19],//canEdit
                                                                                           (Boolean) item[20]))//canDelete
                                      .collect(Collectors.toList());
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }

    @Transactional
    public GetChannelDescriptionResponseDto getChannelDescription(GetChannelDescriptionRequestDto request) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("getChannelDescription")
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_channelId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_showDeleted", Boolean.class, ParameterMode.IN)
                                                            .setParameter("p_userId", authService.isLoggedIn() ? authService.getCurrentUser().getId() : null)
                                                            .setParameter("p_channelId", request.getChannelId())
                                                            .setParameter("p_showDeleted", request.getShowDeleted());
        List<GetChannelDescriptionResponseDto> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();
            if (resultObjects == null) {
                result = new ArrayList();
            } else {
                result = resultObjects.stream()
                                      .map(item -> new GetChannelDescriptionResponseDto((Long) item[0], //channelId
                                                                                        (Short) item[1], //channelType
                                                                                        (Long) item[2],//userId
                                                                                        (String) item[3],//userName
                                                                                        (String) item[4],//channelName
                                                                                        (String) item[5],//channelDescription
                                                                                        (String) item[6],//created
                                                                                        (String) item[7],//modified
                                                                                        (Long) item[8],//countPosts
                                                                                        (String) item[9],//createdTimeAgo
                                                                                        (Boolean) item[10],//isDeleted
                                                                                        (Boolean) item[11],//isSubscribedOnChannel
                                                                                        (Boolean) item[12],//hasAccessToChannel
                                                                                        (Boolean) item[13],//isChannelModerator
                                                                                        (Boolean) item[14],//canEdit
                                                                                        (Boolean) item[15]))//canDelete
                                      .collect(Collectors.toList());
            }
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return !result.isEmpty() ? result.get(0) : null;
    }

    @Transactional(readOnly = false)
    public List<SearchChannelsResponseDto> searchChannels(SearchChannelsRequestDto request) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("searchChannels")
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_channelName", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_maxNameLength", Integer.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_maxDescriptionLength", Integer.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_limit", Integer.class, ParameterMode.IN)
                                                            .setParameter("p_userId", request.getUserId() != null ? request.getUserId() : authService.isLoggedIn() ? (Long) authService.getCurrentUser().getId() : null)
                                                            .setParameter("p_channelName", request.getChannelName())
                                                            .setParameter("p_maxNameLength", request.getMaxNameLength())
                                                            .setParameter("p_maxDescriptionLength", request.getMaxDescriptionLength())
                                                            .setParameter("p_limit", request.getLimit());
        List<SearchChannelsResponseDto> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();

            if (resultObjects == null) {
                result = new ArrayList();
            } else
                result = resultObjects.stream()
                                      .map(item -> new SearchChannelsResponseDto((Long) item[0], //channelId
                                                                                 (Short) item[1], //channelType
                                                                                 (Long) item[2],//userId
                                                                                 (String) item[3],//userName
                                                                                 (String) item[4],//channelName
                                                                                 (String) item[5],//channelDescription
                                                                                 (String) item[6],//created
                                                                                 (String) item[7],//modified
                                                                                 (Long) item[8],//countPosts
                                                                                 (String) item[9],//createdTimeAgo
                                                                                 (Boolean) item[10],//isDeleted
                                                                                 (Boolean) item[11],//isSubscribedOnChannel
                                                                                 (Boolean) item[12],//hasAccessToChannel
                                                                                 (Boolean) item[13],//isChannelModerator
                                                                                 (Boolean) item[14],//canEdit
                                                                                 (Boolean) item[15]))//canDelete
                                      .collect(Collectors.toList());
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }

}
