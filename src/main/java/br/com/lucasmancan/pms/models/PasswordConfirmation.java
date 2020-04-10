package br.com.lucasmancan.pms.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
public class PasswordConfirmation {

    @NotNull(message = "Confirmation must not be null")
    private String confirmation;

    @Length(min = 6, message = "Password must be bigger than 5 digits")
//    @Pattern(regexp = "((?=.*[a-z])(?=.*d)(?=.*[@#$%])(?=.*[A-Z]).{6,16})", message = "Password must have at least one lowercase letter, one digit i.e. 0-9, one special character, one capital letter, minimum 6 letters to maximum 16 letters")
    @NotNull(message = "Password must not be null")
    private  String password;


    private String token;

    public void setToken(String token){
        this.token = StringUtils.trim(token);
    }

    public void setPassword(String token){
        this.password = StringUtils.trim(token);
    }

    public void setConfirmation(String token){
        this.confirmation = StringUtils.trim(token);
    }


    public boolean matchPassword(){
        return password.equals(confirmation);
    }
}
