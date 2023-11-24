package com.programming.man.mdchat.repository;

import com.programming.man.mdchat.dto.SearchChannelsResponseDto;
import com.programming.man.mdchat.model.Channel;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    Optional<Channel> findByName(String channelName);
//    Optional<SearchChannelsResponseDto> searchByName(String channelName);
    Optional<Channel> findById(Long channelId);

//    @Query(value = "searchChannelsByNameDto", nativeQuery = true)
    @Query(value = "CALL searchChannelsByName(:channelName, :userId, :searchMode, :descLength);", nativeQuery = true)
    List<Tuple> searchChannelsByName(@Param("channelName") String channelName,
                                     @Param("userId") Long userId,
                                     @Param("searchMode") Short loadMode,
                                     @Param("descLength") Long descLength);
}
