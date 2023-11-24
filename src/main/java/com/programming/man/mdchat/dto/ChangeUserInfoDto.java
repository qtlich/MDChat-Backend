package com.programming.man.mdchat.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChangeUserInfoDto
{
    @NotNull
    private Long userid;
    private String username;
    private String email;
    private String password;
}
