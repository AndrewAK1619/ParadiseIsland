package pl.example.components.offer.transport.airline;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AirlineRepository extends JpaRepository<Airline, Long> {

	Optional<Airline> findByAirlineName(String airlineName);
	List<Airline> findAllByOrderByIdAsc();
	List<Airline> findAllByAirlineNameContainingIgnoreCaseOrderByIdAsc(String airlineName);
	
	@Query(value = "SELECT * FROM airlines		\r\n" + 
			" ORDER BY RAND()					\r\n" + 
			" LIMIT 1;", nativeQuery = true)
	Optional<Airline> findRandomAirline();
}
