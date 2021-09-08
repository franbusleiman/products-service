package com.busleiman.products.persistance;

import com.busleiman.products.domain.entities.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Long> {

    Optional<Section> findSectionByProductId(Long productId);


}
