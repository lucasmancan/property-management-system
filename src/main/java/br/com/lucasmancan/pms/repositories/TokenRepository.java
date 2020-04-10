package br.com.lucasmancan.pms.repositories;

import br.com.lucasmancan.pms.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("SELECT t FROM Token t where t.token =:token and (t.expiresAt is null or t.expiresAt >= :data ) ")
    Token findByToken(String token, LocalDateTime data);

    List<Token> findByUserId(Long id);
}
