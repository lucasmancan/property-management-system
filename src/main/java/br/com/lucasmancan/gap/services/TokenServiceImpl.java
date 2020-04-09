package br.com.lucasmancan.gap.services;


import br.com.lucasmancan.gap.models.AppUser;
import br.com.lucasmancan.gap.models.Token;
import br.com.lucasmancan.gap.repositories.TokenRepository;
import br.com.lucasmancan.gap.services.interfaces.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class TokenServiceImpl implements TokenService {


    private TokenRepository repository;

    @Autowired
    public TokenServiceImpl(TokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public Token save(AppUser user, String tokenValue) {

        Token token = new Token();

        token.setToken(tokenValue);
        token.setUser(user);

        token.setExpiresAt(LocalDateTime.now().plusMinutes(60L));

        return repository.save(token);
    }

    @Override
    public Token findByToken(String token) {
        return repository.findByToken(token);
    }
}
