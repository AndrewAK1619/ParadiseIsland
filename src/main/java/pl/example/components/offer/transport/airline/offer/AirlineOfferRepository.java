package pl.example.components.offer.transport.airline.offer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AirlineOfferRepository extends JpaRepository<AirlineOffer, Long> {
	
	Optional<AirlineOffer> findById(long airlineOfferId);
	
	@Query(value = "SELECT * FROM airline_offer ao									\r\n" + 
			"WHERE ao.departure BETWEEN :departureDayStart AND :departureDayEnd		\r\n" + 
			"AND ao.return_trip BETWEEN :returnTripDayStart AND :returnTripDayEnd ;", 
			nativeQuery = true)
	List<AirlineOffer> findAllByDepartureAndReturnTrip(
			LocalDateTime departureDayStart, LocalDateTime departureDayEnd, 
			LocalDateTime returnTripDayStart, LocalDateTime returnTripDayEnd);
	
	@Query(value = "SELECT * FROM airline_offer ao									\r\n" + 
			"WHERE ao.departure BETWEEN :departureDayStart AND :departureDayEnd		\r\n" + 
			"AND ao.return_trip BETWEEN :returnTripDayStart AND :returnTripDayEnd	\r\n" + 
			"ORDER BY ao.flight_price;", nativeQuery = true)
	List<AirlineOffer> findAllByDepartureAndReturnTripOrderByFlightPrice(
			LocalDateTime departureDayStart, LocalDateTime departureDayEnd, 
			LocalDateTime returnTripDayStart, LocalDateTime returnTripDayEnd);
}
