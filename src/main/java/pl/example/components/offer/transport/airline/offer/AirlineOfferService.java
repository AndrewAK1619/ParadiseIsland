package pl.example.components.offer.transport.airline.offer;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AirlineOfferService {

	AirlineOfferRepository airlineOfferRepository;

	public AirlineOfferService(AirlineOfferRepository airlineOfferRepository) {
		this.airlineOfferRepository = airlineOfferRepository;
	}

	public List<AirlineOffer> getAirlineOfferByDepartureAndReturnDateOrderByFlightPrice(
			LocalDate departure, LocalDate returnTrip) {
		return airlineOfferRepository.findAllByDepartureAndReturnTripOrderByFlightPrice(
				departure.atStartOfDay(), departure.plusDays(1).atStartOfDay(),
				returnTrip.atStartOfDay(), returnTrip.plusDays(1).atStartOfDay());
	}
	
	public void saveAirlineOffer(AirlineOffer airlineOffer) {
		airlineOfferRepository.save(airlineOffer);
	}
}
