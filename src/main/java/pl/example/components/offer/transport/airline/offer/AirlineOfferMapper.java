package pl.example.components.offer.transport.airline.offer;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.example.components.offer.transport.airline.Airline;
import pl.example.components.offer.transport.airline.AirlineRepository;

@Service
public class AirlineOfferMapper {

	AirlineRepository airlineRepository;

	@Autowired
	public AirlineOfferMapper(AirlineRepository airlineRepository) {
		this.airlineRepository = airlineRepository;
	}

	AirlineOfferDto toDto(AirlineOffer airlineOffer) {
		AirlineOfferDto dto = new AirlineOfferDto();
		dto.setId(airlineOffer.getId());
		if (airlineOffer.getAriline() != null) {
			dto.setArilineId(airlineOffer.getId());;
		}
		dto.setDeparture(airlineOffer.getDeparture());
		dto.setReturnTrip(airlineOffer.getReturnTrip());
		dto.setFlightPrice(airlineOffer.getFlightPrice());
		return dto;
	}

	AirlineOffer toEntity(AirlineOfferDto airlineOfferDto) {
		AirlineOffer entity = new AirlineOffer();
		entity.setId(airlineOfferDto.getId());
		Optional<Airline> airline = airlineRepository.findById(airlineOfferDto.getArilineId());
		airline.ifPresent(entity::setAriline);
		entity.setDeparture(airlineOfferDto.getDeparture());
		entity.setReturnTrip(airlineOfferDto.getReturnTrip());
		entity.setFlightPrice(airlineOfferDto.getFlightPrice());
		return entity;
	}
}
