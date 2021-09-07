package com.busleiman.products.persistance;

import com.busleiman.products.domain.entities.Factory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FactoryRepository extends JpaRepository<Factory, Long> {

    Optional<Factory> findFactoryByProductId(Long productId);
}
