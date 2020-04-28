package pl.example.components.offer.location.country;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CountryRepository extends JpaRepository<Country, Long> {

	Optional<Country> findByName(String name);
	
	@Query(value = "SELECT * FROM countries ORDER BY country_id LIMIT 12", nativeQuery = true)
	List<Country> findFirst12ById();
}
