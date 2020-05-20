package pl.example.components.offer.location.city;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.example.components.offer.location.region.Region;

public interface CityRepository extends JpaRepository<City, Long> {

	Optional<City> findByName(String name);
	Optional<City> findByNameAndRegion(String name, Region region);
	List<City> findAllByRegion(Region region);
}
