package com.programming.man.mdchat.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponseDto {
    @Id
    private Long id;
    private String email;
    private String username;
    private String description;
    private String created;
    private String modified;
    private Boolean enabled;
}
