package br.com.lucasmancan.pms.repositories;

import br.com.lucasmancan.pms.models.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
    List<Brand> findAllByUserId(Long id);

    @Query(value = "SELECT b.* from brands b WHERE b.user_id =:userId and b.name like CONCAT('%',:name,'%') ", nativeQuery = true)
    List<Brand> findAllLike(Long userId, String name);

    @Query(value = "SELECT b FROM Brand b where b.user.id=:userId and b.id =:brandId ")
    Optional<Brand> findById(Long userId, Long brandId);

}
