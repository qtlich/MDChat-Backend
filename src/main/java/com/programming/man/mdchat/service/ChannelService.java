package com.programming.man.mdchat.service;

import com.programming.man.mdchat.dto.*;
import com.programming.man.mdchat.exceptions.SpringMDChatException;
import com.programming.man.mdchat.mapper.ChannelMapper;
import com.programming.man.mdchat.model.Channel;
import com.programming.man.mdchat.repository.ChannelRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.status;

@Service
@AllArgsConstructor
@Slf4j
public class ChannelService {

    @PersistenceContext(name = "MDCHAT")
    private EntityManager entityManager;
    private final ChannelRepository channelRepository;
    private final ChannelMapper channelMapper;
    private final AuthService authService;

    @Transactional(readOnly = false)
    public List<ChannelCUDResponse> channelCUD(ChannelCUDRequest request) {
        StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("channelCUD")
                                                            .registerStoredProcedureParameter("p_operationType", Integer.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_userId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_channelId", Long.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_channelType", Short.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_channelName", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_channelDescription", String.class, ParameterMode.IN)
                                                            .registerStoredProcedureParameter("p_Deleted", Boolean.class, ParameterMode.IN)
                                                            .setParameter("p_operationType", request.getOperationType())
                                                            .setParameter("p_userId", authService.isLoggedIn() ? (Long) authService.getCurrentUser().getId() : null)
                                                            .setParameter("p_channelId", request.getChannelId())
                                                            .setParameter("p_channelType", request.getChannelTypeId())
                                                            .setParameter("p_channelName", request.getChannelName())
                                                            .setParameter("p_channelDescription", request.getChannelDescription())
                                                            .setParameter("p_Deleted", request.getDeleted());
        List<ChannelCUDResponse> result;
        try {
            List<Object[]> resultObjects = storedProcedure.getResultList();

            if (resultObjects == null) {
                result = new ArrayList();
            } else
                result = resultObjects.stream()
                                      .map(item -> new ChannelCUDResponse((Long) item[0], //id
                                                                               (String) item[1]))//message
                                      .collect(Collectors.toList());
        } finally {
            storedProcedure.unwrap(ProcedureOutputs.class).release();
        }
        return result;
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
