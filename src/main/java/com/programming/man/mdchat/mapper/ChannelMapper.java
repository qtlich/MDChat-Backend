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

//    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(channel.getPosts()))")
    SearchChannelsResponseDto mapSearchChannelToDto(SearchChannelsResponseDto channel);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    //    @InheritInverseConfiguration
//    @Mapping(target = "name", expression = "channelDto.name")
//    @Mapping(target = "description", expression = "channelDto.description")
//    @Mapping(target = "channelType", expression = "channelDto.channelType")
//    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Channel mapDtoToChannel(ChannelDto channelDto, User user);
}
