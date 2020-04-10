package br.com.lucasmancan.pms.services.interfaces;


import br.com.lucasmancan.pms.models.AppUser;
import br.com.lucasmancan.pms.models.Token;

public interface TokenService  {

    Token save(AppUser user, String token);

    Token findByToken(String token);

}
