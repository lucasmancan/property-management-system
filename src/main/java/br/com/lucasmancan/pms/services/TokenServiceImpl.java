package br.com.lucasmancan.pms.services;


import br.com.lucasmancan.pms.models.AppUser;
import br.com.lucasmancan.pms.models.Token;
import br.com.lucasmancan.pms.repositories.TokenRepository;
import br.com.lucasmancan.pms.services.interfaces.TokenService;
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


        repository.findByUserId(user.getId()).forEach(token -> {
            token.setExpiresAt(LocalDateTime.now());
            repository.save(token);
        });

        Token token = new Token();

        token.setToken(tokenValue);
        token.setUser(user);

        token.setExpiresAt(LocalDateTime.now().plusMinutes(60L));

        return repository.save(token);
    }

    @Override
    public Token findByToken(String token) {
        return repository.findByToken(token, LocalDateTime.now());
    }
}
