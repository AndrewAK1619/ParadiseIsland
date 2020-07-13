package pl.example.components.offer.transport.airline.offer;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class AirlineOfferService {

	private AirlineOfferMapper airlineOfferMapper;
	private AirlineOfferRepository airlineOfferRepository;

	public AirlineOfferService(
			AirlineOfferMapper airlineOfferMapper, 
			AirlineOfferRepository airlineOfferRepository) {
		this.airlineOfferMapper = airlineOfferMapper;
		this.airlineOfferRepository = airlineOfferRepository;
	}

	public List<AirlineOfferDto> getAirlineOfferByDepartureAndReturnDate(
			LocalDate departure, LocalDate returnTrip) {

		List<AirlineOffer> airlinesOffer = airlineOfferRepository.
				findAllByDepartureAndReturnTrip(
				departure.atStartOfDay(), departure.plusDays(1).atStartOfDay(), 
				returnTrip.atStartOfDay(), returnTrip.plusDays(1).atStartOfDay());

		return airlinesOffer
				.stream()
				.map(ao -> airlineOfferMapper.toDto(ao))
				.collect(Collectors.toList());
	}

	public List<AirlineOfferDto> getAllAirlineOfferByDepartureAndReturnDateOrderByFlightPrice(
			LocalDate departure, LocalDate returnTrip) {
			
		List<AirlineOffer> airlinesOffer = airlineOfferRepository
				.findAllByDepartureAndReturnTripOrderByFlightPrice(
				departure.atStartOfDay(), departure.plusDays(1).atStartOfDay(), 
				returnTrip.atStartOfDay(), returnTrip.plusDays(1).atStartOfDay());
				
		return airlinesOffer
				.stream()
				.map(ao -> airlineOfferMapper.toDto(ao))
				.collect(Collectors.toList());
	}

	public List<AirlineOfferDto> getAllAirlineOfferByDepartureAndReturnDateAndAirlineName(
			LocalDate departure, LocalDate returnTrip, String airlineName) {

		List<AirlineOffer> airlinesOffer = airlineOfferRepository
				.findAllByDepartureAndReturnTripAndAirlineName(
				departure.atStartOfDay(), departure.plusDays(1).atStartOfDay(), 
				returnTrip.atStartOfDay(), returnTrip.plusDays(1).atStartOfDay(), 
				airlineName);

		return airlinesOffer
				.stream()
				.map(ao -> airlineOfferMapper.toDto(ao))
				.collect(Collectors.toList());
	}

	public void saveAirlineOffer(AirlineOffer airlineOffer) {
		airlineOfferRepository.save(airlineOffer);
	}
}
