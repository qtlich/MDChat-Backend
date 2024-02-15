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

}
