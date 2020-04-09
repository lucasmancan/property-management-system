package br.com.lucasmancan.gap.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;


@Data
@AllArgsConstructor
public class PasswordConfirmation {
    private String password;
    private String confirmation;

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
