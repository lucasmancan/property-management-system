package br.com.lucasmancan.pms.models.dto;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserDTO implements AppDTO {

    private Long id;

    @NotNull(message = "E-mail must not be null")
    private String name;

    @NotNull(message = "E-mail must not be null")
    @Email(message = "E-mail must be valid")
    private String email;

}
