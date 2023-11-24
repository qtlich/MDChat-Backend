package com.programming.man.mdchat.service;

import com.programming.man.mdchat.dto.OperationResultDto;
import com.programming.man.mdchat.dto.PostRequest;
import com.programming.man.mdchat.dto.PostResponse;
import com.programming.man.mdchat.exceptions.ChannelNotFoundException;
import com.programming.man.mdchat.exceptions.PostNotFoundException;
import com.programming.man.mdchat.mapper.OperationResultMapper;
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
    private OperationResultMapper operationResultMapper;

    public Post save(PostRequest postRequest) {
        Channel channel = channelRepository.findById(postRequest.getChannelId())
                                           .orElseThrow(() -> new ChannelNotFoundException(postRequest.getChannelName()));
        return postRepository.save(postMapper.map(postRequest, channel, authService.getCurrentUser()));
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
