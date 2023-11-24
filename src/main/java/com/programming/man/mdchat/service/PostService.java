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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class PostService {

    private PostRepository postRepository;
    private OperationResultRepository operationResultRepository;
    private ChannelRepository channelRepository;
    private UserRepository userRepository;
    private AuthService authService;
    private PostMapper postMapper;
    @Autowired
    private OperationResultMapper operationResultMapper;

    public void save(PostRequest postRequest) {
        Channel channel = channelRepository.findById(postRequest.getChannelId())
                .orElseThrow(() -> new ChannelNotFoundException(postRequest.getChannelName()));
        postRepository.save(postMapper.map(postRequest, channel, authService.getCurrentUser()));
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
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new ChannelNotFoundException(channelId.toString()));
        List<Post> posts = postRepository.findAllPostsByChannel(channel);
        return posts.stream().map(postMapper::mapToDto).collect(toList());
    }

    @Transactional()
    public List<OperationResultDto> deletePostById(Long postId) {
        List<OperationResultDto> result = operationResultRepository.deletePostByPostId(postId);
        log.info("**********************************************");
        log.info("Result: {}", Arrays.toString(result.toArray()));
        log.info("**********************************************");
        return result.stream().map(operationResultMapper::mapOperationResultToDto).collect(toList());
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
