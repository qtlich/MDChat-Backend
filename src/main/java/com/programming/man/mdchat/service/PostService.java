package com.programming.man.mdchat.service;

import com.programming.man.mdchat.dto.*;
import com.programming.man.mdchat.exceptions.ChannelNotFoundException;
import com.programming.man.mdchat.exceptions.PostNotFoundException;
import com.programming.man.mdchat.mapper.PostMapper;
import com.programming.man.mdchat.model.Channel;
import com.programming.man.mdchat.model.Post;
import com.programming.man.mdchat.model.User;
import com.programming.man.mdchat.repository.ChannelRepository;
import com.programming.man.mdchat.repository.OperationResultRepository;
import com.programming.man.mdchat.repository.PostRepository;
import com.programming.man.mdchat.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
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
    private OperationResultRepository operationResultRepository;
    private ChannelRepository channelRepository;
    private UserRepository userRepository;
    private AuthService authService;
    private PostMapper postMapper;

    public Post save(PostRequest postRequest) {
        Channel channel = channelRepository.findById(postRequest.getChannelId())
                                           .orElseThrow(() -> new ChannelNotFoundException(postRequest.getChannelName()));
        return postRepository.save(postMapper.map(postRequest, channel, authService.getCurrentUser()));
    }

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

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                                  .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                             .stream()
                             .map(postMapper::mapToDto)
                             .collect(toList());
    }

    @Transactional(readOnly = false)
    public List<GetAllPostsResponse> getAllPostsV1(GetAllPostsRequest request) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("getPosts")
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_sortMode", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_offset", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_limit", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_postNameMaxLength", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_postDescriptionMaxLength", Long.class, ParameterMode.IN)
                                                            .setParameter("p_userId", authService.isLoggedIn() ? (Long) authService.getCurrentUser().getId() : null)
                                                            .setParameter("p_sortMode", request.getSortMode())
                                                            .setParameter("p_offset", request.getOffset())
                                                            .setParameter("p_limit", request.getLimit())
                                                            .setParameter("p_postNameMaxLength", request.getPostNameMaxLength())
                                                            .setParameter("p_postDescriptionMaxLength", request.getPostDescriptionMaxLength());
        List<GetAllPostsResponse> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();

            if (resultObjects == null) {
                result = new ArrayList();
            } else
                result = resultObjects.stream()
                                      .map(item -> new GetAllPostsResponse((Long) item[0], //postId
                                                                           authService.isLoggedIn() ? (Long) authService.getCurrentUser().getId() : (Long) null, //userId
                                                                           (Long) item[1], //postChannelId
                                                                           (Short) item[2],//channelType
                                                                           (String) item[3],//channelName
                                                                           (String) item[4],//channelDescription
                                                                           (Long) item[5],//postUseId
                                                                           (String) item[6],//postUserName
                                                                           (String) item[7],//postName
                                                                           (String) item[8],//postDescription
                                                                           (Integer) item[9],//postVoteCount
                                                                           (String) item[10],//postCreated
                                                                           (String) item[11],//postModified
                                                                           (String) item[12],//postUrl
                                                                           (Integer) item[13],//currentUserVoteType
                                                                           (Integer) item[14],//postCountComments
                                                                           (String) item[15]))//posttimeAgo
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

    @Transactional
    public List<OperationResultDto> deletePostById(Long postId) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("deletePost")
                                                            .registerStoredProcedureParameter("postId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("userId", Long.class, ParameterMode.IN)
                                                            .setParameter("postId", postId)
                                                            .setParameter("userId", authService.getCurrentUser().getId());
        List<OperationResultDto> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();
            if (resultObjects == null) {
                result = new ArrayList();
                result.add(new OperationResultDto(-postId, "Can't delete post"));
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
