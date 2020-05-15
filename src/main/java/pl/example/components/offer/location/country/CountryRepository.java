package pl.example.components.offer.location.country;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CountryRepository extends JpaRepository<Country, Long> {

	Optional<Country> findByName(String name);
	
	@Query(value = "SELECT * FROM countries c WHERE c.popular_destination = true LIMIT 12;", nativeQuery = true)
	List<Country> findPopular();
	
	@Query(value = "SELECT * FROM (					\r\n" + 
			"			SELECT * FROM countries		\r\n" + 
			"			ORDER BY RAND()				\r\n" + 
			"			LIMIT 12					\r\n" + 
			"		) c								\r\n" + 
			"		ORDER BY name", nativeQuery = true)
	List<Country> findRadnom12Records();
}
