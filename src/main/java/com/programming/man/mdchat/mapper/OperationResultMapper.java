package com.programming.man.mdchat.mapper;

import com.programming.man.mdchat.dto.OperationResultDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OperationResultMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "message", source = "message")
    OperationResultDto mapOperationResultToDto(OperationResultDto result);
}
