package com.programming.man.mdchat.mapper;

import com.programming.man.mdchat.dto.ChannelDto;
import com.programming.man.mdchat.dto.SearchChannelsResponseDto;
import com.programming.man.mdchat.model.Channel;
import com.programming.man.mdchat.model.Post;
import com.programming.man.mdchat.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChannelMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(channel.getPosts()))")
    ChannelDto mapChannelToDto(Channel channel);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "channelName", source = "channelName")
    @Mapping(target = "channelDescription", source = "channelDescription")
    @Mapping(target = "channelType", source = "channelType")
    @Mapping(target = "created", source = "created")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "countPosts", source = "countPosts")
    SearchChannelsResponseDto mapSearchChannelToDto(SearchChannelsResponseDto channel);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "channelDto.id")
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "created", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "channelDto.description")
    Channel mapDtoToChannel(ChannelDto channelDto, User user);
}
