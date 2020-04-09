package br.com.lucasmancan.gap.repositories;

import br.com.lucasmancan.gap.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findAllByUserId(Long userId);
}
