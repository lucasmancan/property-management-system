package br.com.lucasmancan.gap.repositories;

import br.com.lucasmancan.gap.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByToken(String token);
}
