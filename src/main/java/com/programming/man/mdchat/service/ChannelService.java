package com.programming.man.mdchat.service;

import com.programming.man.mdchat.dto.ChannelDto;
import com.programming.man.mdchat.dto.SearchChannelsResponseDto;
import com.programming.man.mdchat.exceptions.SpringMDChatException;
import com.programming.man.mdchat.mapper.ChannelMapper;
import com.programming.man.mdchat.model.Channel;
import com.programming.man.mdchat.repository.ChannelRepository;
import jakarta.persistence.Tuple;
import jakarta.persistence.TupleElement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final ChannelMapper channelMapper;
    private final AuthService authService;

    @Transactional
    public ChannelDto save(ChannelDto channelDto) {
        Channel save = channelRepository.save(channelMapper.mapDtoToChannel(channelDto, authService.getCurrentUser()));
        channelDto.setId(save.getId());
        return channelDto;
    }

    @Transactional(readOnly = true)
    public List<ChannelDto> getAll() {
        return channelRepository.findAll()
                .stream()
                .map(channelMapper::mapChannelToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public ChannelDto getChannel(Long id) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new SpringMDChatException("No channel found with ID - " + id));
        return channelMapper.mapChannelToDto(channel);
    }

    @Transactional(readOnly = true)
    public List<SearchChannelsResponseDto> searchChannels(String channelName, Long descLength, Short loadMode) {

        List<Tuple> stockTotalTuples = channelRepository.searchChannelsByName(channelName, authService.getCurrentUser().getId(),loadMode, descLength);
        List<SearchChannelsResponseDto> searched = stockTotalTuples.stream()
                .map(item -> new SearchChannelsResponseDto(item.get(0, Long.class),
                        item.get(1, String.class),
                        item.get(2, String.class),
                        item.get(3, Short.class),
                        item.get(4, String.class),
                        item.get(5, String.class),
                        item.get(6, Long.class)))
                .collect(Collectors.toList());
        return searched;
    }

}
