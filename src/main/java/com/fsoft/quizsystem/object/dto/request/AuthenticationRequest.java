package com.fsoft.quizsystem.object.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthenticationRequest {

    @NotBlank(message = "blank")
    private String username;

    @NotBlank(message = "blank")
    private String password;
}
