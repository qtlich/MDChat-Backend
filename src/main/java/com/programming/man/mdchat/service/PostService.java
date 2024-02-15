package com.programming.man.mdchat.service;

import com.programming.man.mdchat.dto.*;
import com.programming.man.mdchat.mapper.PostMapper;
import com.programming.man.mdchat.model.Post;
import com.programming.man.mdchat.model.User;
import com.programming.man.mdchat.repository.ChannelRepository;
import com.programming.man.mdchat.repository.PostRepository;
import com.programming.man.mdchat.repository.UserRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {
    @PersistenceContext(name = "MDCHAT")
    private EntityManager entityManager;
    private PostRepository postRepository;
    private ChannelRepository channelRepository;
    private UserRepository userRepository;
    private AuthService authService;
    private PostMapper postMapper;

    @Transactional(readOnly = false)
    public List<PostCUDResponse> postCUD(PostCUDRequest postRequest) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("postCUD")
                                                            .registerStoredProcedureParameter("p_operationType", Integer.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_postId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_channelId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_postName", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_postDescription", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_url", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_commentsLocked", Boolean.class, ParameterMode.IN)
                                                            .setParameter("p_operationType", postRequest.getOperationType())
                                                            .setParameter("p_postId", postRequest.getPostId())
                                                            .setParameter("p_userId", authService.isLoggedIn() ? (Long) authService.getCurrentUser().getId() : null)
                                                            .setParameter("p_channelId", postRequest.getChannelId())
                                                            .setParameter("p_postName", postRequest.getPostName())
                                                            .setParameter("p_postDescription", postRequest.getPostDescription())
                                                            .setParameter("p_url", postRequest.getUrl())
                                                            .setParameter("p_commentsLocked", postRequest.getCommentsLocked());
        List<PostCUDResponse> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();

            if (resultObjects == null) {
                result = new ArrayList();
            } else
                result = resultObjects.stream()
                                      .map(item -> new PostCUDResponse((Long) item[0], //id
                                                                       (String) item[1]))//message
                                      .collect(Collectors.toList());
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }

    @Transactional(readOnly = false)
    public List<ShowHidePostResponseDto> showHidePost(ShowHidePostRequestDto postRequest) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("showHidePost")
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_postId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_showPost", Boolean.class, ParameterMode.IN)
                                                            .setParameter("p_userId", authService.isLoggedIn() ? (Long) authService.getCurrentUser().getId() : null)
                                                            .setParameter("p_postId", postRequest.getPostId())
                                                            .setParameter("p_showPost", postRequest.getShowPost());
        List<ShowHidePostResponseDto> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();

            if (resultObjects == null) {
                result = new ArrayList();
            } else
                result = resultObjects.stream()
                                      .map(item -> new ShowHidePostResponseDto((Long) item[0], //id
                                                                               (String) item[1]))//message
                                      .collect(Collectors.toList());
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }

    @Transactional(readOnly = false)
    public List<BookmarkPostResponseDto> bookmarkPost(BookmarkPostRequestDto postRequest) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("saveUnsaveUserPost")
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_postId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_savePost", Boolean.class, ParameterMode.IN)
                                                            .setParameter("p_userId", authService.isLoggedIn() ? (Long) authService.getCurrentUser().getId() : null)
                                                            .setParameter("p_postId", postRequest.getPostId())
                                                            .setParameter("p_savePost", postRequest.getBookmarkPost());
        List<BookmarkPostResponseDto> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();

            if (resultObjects == null) {
                result = new ArrayList();
            } else
                result = resultObjects.stream()
                                      .map(item -> new BookmarkPostResponseDto((Long) item[0], //id
                                                                               (String) item[1]))//message
                                      .collect(Collectors.toList());
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
    }

    @Transactional(readOnly = false)
    public List<GetPostByIdResponseDto> getPostById(GetPostByIdRequestDto request) {

        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("getPost")
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_postId", Long.class, ParameterMode.IN)
                                                            .setParameter("p_userId", authService.isLoggedIn() ? (Long) authService.getCurrentUser().getId() : null)
                                                            .setParameter("p_postId", request.getPostId());

        List<GetPostByIdResponseDto> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();

            if (resultObjects == null) {
                result = new ArrayList();
            } else
                result = resultObjects.stream()
                                      .map(item -> new GetPostByIdResponseDto((Long) item[0],
                                                                              (Long) item[1],
                                                                              (Short) item[2],
                                                                              (String) item[3],
                                                                              (String) item[4],
                                                                              (Long) item[5],
                                                                              (String) item[6],
                                                                              (String) item[7],
                                                                              (String) item[8],
                                                                              (String) item[9],
                                                                              (Integer) item[10],
                                                                              (Long) item[11],
                                                                              (String) item[12],
                                                                              (String) item[13],
                                                                              (String) item[14],
                                                                              (String) item[15],
                                                                              (Boolean) item[16],
                                                                              (Boolean) item[17],
                                                                              (Boolean) item[18]))//message
                                      .collect(Collectors.toList());
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;

    }


    @Transactional(readOnly = false)
    public Long getCommentsCount(Long postId) {
        Query query = entityManager
                .createNativeQuery("SELECT getPostCountComments(?1)")
                .setParameter(1, postId);
        return ((Long) query.getSingleResult()).longValue();
    }

    @Transactional(readOnly = false)
    public List<GetAllUserPostsUniversalResponse> getUserPostsUniversal(GetAllUserPostsUniversalRequest request) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("getUserPostsUniversal")
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("selectedUserView", Integer.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_sortMode", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_offset", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_limit", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_postNameMaxLength", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_postDescriptionMaxLength", Long.class, ParameterMode.IN)
                                                            .setParameter("p_userId", request.getUserId() != null ? request.getUserId() : authService.isLoggedIn() ? (Long) authService.getCurrentUser().getId() : null)
                                                            .setParameter("selectedUserView", request.getSelectedUserView())
                                                            .setParameter("p_sortMode", request.getSortMode())
                                                            .setParameter("p_offset", request.getOffset())
                                                            .setParameter("p_limit", request.getLimit())
                                                            .setParameter("p_postNameMaxLength", request.getPostNameMaxLength())
                                                            .setParameter("p_postDescriptionMaxLength", request.getPostDescriptionMaxLength());
        List<GetAllUserPostsUniversalResponse> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();

            if (resultObjects == null) {
                result = new ArrayList();
            } else
                result = resultObjects.stream()
                                      .map(item -> new GetAllUserPostsUniversalResponse((Long) item[0], //postId
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



    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByChannel(Long channelId) {
        List<Post> posts = postRepository.getChannelsPosts(channelId, authService.getCurrentUser().getId());
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                             .stream()
                             .map(postMapper::mapToDto)
                             .collect(toList());
    }
}
