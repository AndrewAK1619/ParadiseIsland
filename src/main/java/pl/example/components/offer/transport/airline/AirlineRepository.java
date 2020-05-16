package pl.example.components.offer.transport.airline;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AirlineRepository extends JpaRepository<Airline, Long> {

	Optional<Airline> findByAirlineName(String airlineName);
	List<Airline> findAllByOrderByIdAsc();
	List<Airline> findAllByAirlineNameContainingIgnoreCaseOrderByIdAsc(String airlineName);
}
