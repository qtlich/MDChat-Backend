package com.programming.man.mdchat.repository;

import com.programming.man.mdchat.dto.OperationResultDto;
import com.programming.man.mdchat.model.Post;
import com.programming.man.mdchat.model.User;
import com.programming.man.mdchat.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentUser);

    @Query(value = "SELECT getCountPostVotes(:p_userId, :p_postId)", nativeQuery = true)
    Long getCountPostVotes(@Param("p_userId") Long p_userId, @Param("p_postId") Long p_postId);


}
