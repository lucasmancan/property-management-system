package br.com.lucasmancan.pms.repositories;

import br.com.lucasmancan.pms.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByEmail(String email);
}
