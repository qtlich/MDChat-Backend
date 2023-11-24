package com.programming.man.mdchat.repository;

import com.programming.man.mdchat.dto.SearchChannelsResponseDto;
import com.programming.man.mdchat.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    Optional<Channel> findByName(String channelName);
    Optional<SearchChannelsResponseDto> searchByName(String channelName);

    Optional<Channel> findById(Long channelId);
}
