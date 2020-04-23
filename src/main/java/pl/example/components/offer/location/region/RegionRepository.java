package pl.example.components.offer.location.region;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Long> {

	Optional<Region> findByName(String name);
}
