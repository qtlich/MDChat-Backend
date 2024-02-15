package com.programming.man.mdchat.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChannelCUDResponse {
    @Id
    @NotNull
    private Long id;
    @NotNull
    private String message;
}
