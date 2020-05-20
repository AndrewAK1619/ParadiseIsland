package pl.example.components.offer.location.region;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.example.components.offer.location.country.Country;

public interface RegionRepository extends JpaRepository<Region, Long> {

	Optional<Region> findByName(String name);
	Optional<Region> findByNameAndCountry(String name, Country country);
	List<Region> findAllByCountry(Country country);
}
