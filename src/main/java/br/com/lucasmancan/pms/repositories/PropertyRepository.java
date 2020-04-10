package br.com.lucasmancan.pms.repositories;

import br.com.lucasmancan.pms.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findAllByUserId(Long userId);

    @Query(value = "SELECT b.* from properties b WHERE b.user_id =:userId and b.code like CONCAT('%',:code,'%') ", nativeQuery = true)
    List<Property> findAllLike(Long userId, String code);

    @Query(value = "SELECT b FROM Property b where b.user.id=:userId and b.id =:propertyId ")
    Optional<Property> findById(Long userId, Long propertyId);
}
