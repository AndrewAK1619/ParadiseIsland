package pl.example.components.offer.location.country.information;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CountryInformationRepository extends JpaRepository<CountryInformation, Long> {

	
	/* Metods only for put example data / Lorem Ipsum ect. */
	
	@Query(value = "SELECT * FROM country_informations WHERE country_id IS NULL ORDER BY country_information_id LIMIT 6", nativeQuery = true)
	List<CountryInformation> findDefaultInformation();
}
