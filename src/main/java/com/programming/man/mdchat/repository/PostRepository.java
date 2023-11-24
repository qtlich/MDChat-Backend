package com.programming.man.mdchat.repository;

import com.programming.man.mdchat.dto.OperationResultDto;
import com.programming.man.mdchat.model.Channel;
import com.programming.man.mdchat.model.Post;
import com.programming.man.mdchat.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllPostsByChannel(Channel channel);

    List<Post> findByUser(User user);

    @Query(value = "{CALL selectChannelPosts(:channelId, :userId)}", nativeQuery = true)
    List<Post> getChannelsPosts(@Param("channelId") Long channelId, @Param("userId") Long userId) ;

//    @Query(value = "CALL deletePost(:postId);", nativeQuery = true)
//    @Transactional
//    @Procedure(name = "procDeletePost")
//    List<OperationResultDto> deletePostById(@Param("postId") Long postId,@Param("userId") Long userId);
}
