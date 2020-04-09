package br.com.lucasmancan.gap.models.dto;


import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserDTO implements AppDTO {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String email;

}
