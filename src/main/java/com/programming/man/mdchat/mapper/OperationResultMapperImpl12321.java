package com.programming.man.mdchat.mapper;

import com.programming.man.mdchat.dto.OperationResultDto;
import org.springframework.stereotype.Component;

@Component
public class OperationResultMapperImpl12321 {
    OperationResultMapperImpl12321() {

    }

    public OperationResultDto mapOperationResultToDto(OperationResultDto operationResultDto) {
        if (operationResultDto == null) {
            return null;
        } else {
            OperationResultDto result = new OperationResultDto();
            result.setId(operationResultDto.getId());
            result.setMessage(operationResultDto.getMessage());
            return result;
        }
    }
}
