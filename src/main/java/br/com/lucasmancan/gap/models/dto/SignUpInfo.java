package br.com.lucasmancan.gap.models.dto;

import lombok.Data;

@Data
public class SignUpInfo {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String passwordConfirmation;
}
