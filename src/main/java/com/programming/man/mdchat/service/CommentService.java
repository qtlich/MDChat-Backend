package com.programming.man.mdchat.service;

import com.programming.man.mdchat.dto.*;
import com.programming.man.mdchat.exceptions.PostNotFoundException;
import com.programming.man.mdchat.exceptions.SpringMDChatException;
import com.programming.man.mdchat.mapper.CommentMapper;
import com.programming.man.mdchat.model.NotificationEmail;
import com.programming.man.mdchat.model.Post;
import com.programming.man.mdchat.model.User;
import com.programming.man.mdchat.repository.CommentRepository;
import com.programming.man.mdchat.repository.PostRepository;
import com.programming.man.mdchat.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import lombok.AllArgsConstructor;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {
    @PersistenceContext(name = "MDCHAT")
    private EntityManager entityManager;
    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    @Transactional(readOnly = false)
    public List<GetAllCommentsResponseDto> getAllPostComments(GetAllCommentsRequestDto request) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("getComments")
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_commentId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_postId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_sortMode", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_offset", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_limit", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_commentMaxLength", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_showDeleted", Boolean.class, ParameterMode.IN)
                                                            .setParameter("p_userId", authService.isLoggedIn() ? (Long) authService.getCurrentUser().getId() : null)
                                                            .setParameter("p_commentId", request.getCommentId())
                                                            .setParameter("p_postId", request.getPostId())
                                                            .setParameter("p_sortMode", request.getSortMode())
                                                            .setParameter("p_offset", request.getOffset())
                                                            .setParameter("p_limit", request.getLimit())
                                                            .setParameter("p_commentMaxLength", request.getCommentMaxLength())
                                                            .setParameter("p_showDeleted", request.getShowDeleted());
        List<GetAllCommentsResponseDto> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();

            if (resultObjects == null) {
                result = new ArrayList();
            } else
                result = resultObjects.stream()
                                      .map(item -> new GetAllCommentsResponseDto((Long) item[0], //id
                                                                                 (Long) item[1],
                                                                                 (String) item[2],
                                                                                 (Long) item[3],
                                                                                 (Long) item[4],
                                                                                 (String) item[5],
                                                                                 (Integer) item[6],
                                                                                 (Integer) item[7],
                                                                                 (Boolean) item[8],
                                                                                 (String) item[9],
                                                                                 (String) item[10],
                                                                                 (String) item[11],
                                                                                 (String) item[12],
                                                                                 (Boolean) item[13],
                                                                                 (Boolean) item[14],
                                                                                 (Boolean) item[15]))
                                      .collect(Collectors.toList());
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }

    @Transactional(readOnly = false)
    public List<CommentCUDResponse> commentCUD(CommentCUDRequest commentRequest) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("commentCUD")
                                                            .registerStoredProcedureParameter("p_operationType", Integer.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_commentId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_parentId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_postId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_comment", String.class, ParameterMode.IN)
                                                            .setParameter("p_operationType", commentRequest.getOperationType())
                                                            .setParameter("p_userId", authService.isLoggedIn() ? (Long) authService.getCurrentUser().getId() : null)
                                                            .setParameter("p_commentId", commentRequest.getCommentId())
                                                            .setParameter("p_parentId", commentRequest.getParentId())
                                                            .setParameter("p_postId", commentRequest.getPostId())
                                                            .setParameter("p_comment", commentRequest.getComment());
        List<CommentCUDResponse> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();

            if (resultObjects == null) {
                result = new ArrayList();
            } else
                result = resultObjects.stream()
                                      .map(item -> new CommentCUDResponse((Long) item[0], //id
                                                                          (String) item[1]))//message
                                      .collect(Collectors.toList());
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }

    @Transactional(readOnly = false)
    public List<GetUserCommentsUniversalResponse> getUserCommentsUniversal(GetUserCommentsUniversalRequest request) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("getUserCommentsUniversal")
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("selectedUserView", Integer.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_sortMode", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_offset", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_limit", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_commentMaxTextLength", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_postNameMaxLength", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_postDescriptionMaxLength", Long.class, ParameterMode.IN)
                                                            .setParameter("p_userId", authService.isLoggedIn() ? (Long) authService.getCurrentUser().getId() : null)
                                                            .setParameter("selectedUserView", request.getSelectedUserView())
                                                            .setParameter("p_sortMode", request.getSortMode())
                                                            .setParameter("p_offset", request.getOffset())
                                                            .setParameter("p_limit", request.getLimit())
                                                            .setParameter("p_commentMaxTextLength", request.getCommentMaxTextLength())
                                                            .setParameter("p_postNameMaxLength", request.getPostNameMaxLength())
                                                            .setParameter("p_postDescriptionMaxLength", request.getPostDescriptionMaxLength());
        List<GetUserCommentsUniversalResponse> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();

            if (resultObjects == null) {
                result = new ArrayList();
            } else
                result = resultObjects.stream()
                                      .map(item -> new GetUserCommentsUniversalResponse((Long) item[0],//commentId
                                                                                        (Long) item[1],//commentParentId
                                                                                        (Long) item[2],//channelId
                                                                                        (String) item[3],//channelName
                                                                                        (Long) item[4],//postId
                                                                                        (String) item[5],//postName
                                                                                        (String) item[6],//postDescription
                                                                                        (Long) item[7],//commentUserId
                                                                                        (String) item[8],//commentUserName
                                                                                        (String) item[9], //commentText
                                                                                        (String) item[10], //commentCreated
                                                                                        (String) item[11], //commentModified
                                                                                        (Integer) item[12], //commentVoteCount
                                                                                        (Boolean) item[13], //commentsClosed
                                                                                        (Integer) item[14], //commentClaimsCount
                                                                                        (String) item[15], //commentTimeAgo
                                                                                        (Boolean) item[16], //canEdit
                                                                                        (Boolean) item[17], //canDelete
                                                                                        (Boolean) item[18])) //isDeleted
                                      .collect(Collectors.toList());
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post)
                                .stream()
                                .map(commentMapper::mapToDto).toList();
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                                  .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                                .stream()
                                .map(commentMapper::mapToDto)
                                .toList();
    }

}
