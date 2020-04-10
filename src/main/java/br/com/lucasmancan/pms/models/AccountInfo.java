package br.com.lucasmancan.pms.models;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class AccountInfo {


    @NotNull(message = "Name must not be null")
    private @NotNull String name;

    @NotNull(message = "E-mail must not be null")
    @Email(message = "E-mail must be valid")
    private String email;


    @Length(min = 6, message = "Password must be bigger than 5 digits")
//    @Pattern(regexp = "((?=.*[a-z])(?=.*d)(?=.*[@#$%])(?=.*[A-Z]).{6,16})", message = "Password must have at least one lowercase letter, one digit i.e. 0-9, one special character, one capital letter, minimum 6 letters to maximum 16 letters")
    @NotNull(message = "Password must not be null")
    private  String password;


    @NotNull(message = "Confirmation must not be null")
    private  String passwordConfirmation;
}
