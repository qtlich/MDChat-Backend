package com.programming.man.mdchat.repository;

import com.programming.man.mdchat.dto.OperationResultDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationResultRepository {
    List<OperationResultDto> deletePostByPostId(Long postId);
}
