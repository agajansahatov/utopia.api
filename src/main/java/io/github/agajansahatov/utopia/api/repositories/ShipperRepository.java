package io.github.agajansahatov.utopia.api.repositories;

import io.github.agajansahatov.utopia.api.entities.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipperRepository extends JpaRepository<Shipper, Long> {
}
