package br.com.lucasmancan.gap.services.interfaces;


import br.com.lucasmancan.gap.models.AppUser;
import br.com.lucasmancan.gap.models.Token;
import org.springframework.stereotype.Service;

public interface TokenService  {

    Token save(AppUser user, String token);

    Token findByToken(String token);

}
