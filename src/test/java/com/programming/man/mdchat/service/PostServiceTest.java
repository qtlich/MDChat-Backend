package com.programming.man.mdchat.service;

import com.programming.man.mdchat.dto.PostResponse;
import com.programming.man.mdchat.mapper.OperationResultMapper;
import com.programming.man.mdchat.mapper.PostMapper;
import com.programming.man.mdchat.model.Post;
import com.programming.man.mdchat.repository.ChannelRepository;
import com.programming.man.mdchat.repository.OperationResultRepository;
import com.programming.man.mdchat.repository.PostRepository;
import com.programming.man.mdchat.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;
    @Mock
    private OperationResultRepository operationResultRepository;
    @Mock
    private OperationResultMapper operationResultMapper;
    @Mock
    private ChannelRepository channelRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AuthService authService;
    @Mock
    private PostMapper postMapper;

    @Captor
    private ArgumentCaptor<Post> postArgumentCaptor;

    private PostService postService;

//    @BeforeEach
//    public void setup() {
//        postService = new PostService(postRepository, operationResultRepository, channelRepository, userRepository, authService, postMapper, operationResultMapper,null);
//    }

//    @Test
//    @DisplayName("Should Retrieve Post by Id")
//    void shouldFindPostById() {
//        Post post = new Post(123L, "First Post", "http://url.site", "Test",
//                0, null, Instant.now(), null);
//        PostResponse expectedPostResponse = new PostResponse(123L, "First Post", "http://url.site", "Test",
//                "Test User", "Test Subredit", 0, 0, "1 Hour Ago", false, false);
//
//        Mockito.when(postRepository.findById(123L)).thenReturn(Optional.of(post));
//        Mockito.when(postMapper.mapToDto(Mockito.any(Post.class))).thenReturn(expectedPostResponse);
//
//        PostResponse actualPostResponse = postService.getPost(123L);
//
//        Assertions.assertThat(actualPostResponse.getId()).isEqualTo(expectedPostResponse.getId());
//        Assertions.assertThat(actualPostResponse.getPostName()).isEqualTo(expectedPostResponse.getPostName());
//    }

    @Test
    @DisplayName("Should Save Posts")
    void shouldSavePosts() {
//        User currentUser = new User(123L, "test user", "secret password", "user@email.com", Instant.now(), true);
//        Channel channel = new Channel(123L, "First Channel", "Channel Description", Collections.emptyList(), Instant.now(), currentUser);
//        Post post = new Post(123L, "First Post", "http://url.site", "Test",
//                0, null, Instant.now(),null);
//        PostRequest postRequest = new PostRequest(null, "First Channel", "First Post", "http://url.site", null);
//
//        Mockito.when(channelRepository.findByName("First Channel"))
//                .thenReturn(Optional.of(channel));
//        Mockito.when(authService.getCurrentUser())
//                .thenReturn(currentUser);
//        Mockito.when(postMapper.map(postRequest, channel, currentUser)).thenReturn(post);
//
//        postService.save(postRequest);
//        Mockito.verify(postRepository, Mockito.times(1)).save(postArgumentCaptor.capture());
//
//        Assertions.assertThat(postArgumentCaptor.getValue().getPostId()).isEqualTo(123L);
//        Assertions.assertThat(postArgumentCaptor.getValue().getPostName()).isEqualTo("First Post");
    }
}
