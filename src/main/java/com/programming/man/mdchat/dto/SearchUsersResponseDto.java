package com.programming.man.mdchat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchUsersResponseDto {
    private Long id;
    private String userName;
    private String description;
    private String email;
    private String created;
    private String modified;
    private Boolean enabled;
    private String elapsedSinceRegistration;
}
