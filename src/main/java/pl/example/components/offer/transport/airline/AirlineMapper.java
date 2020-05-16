package pl.example.components.offer.transport.airline;

public class AirlineMapper {

	static AirlineDto toDto(Airline airline) {
		AirlineDto dto = new AirlineDto();
		dto.setId(airline.getId());
		dto.setAirlineName(airline.getAirlineName());
		dto.setDetails(airline.getDetails());
		return dto;
	}

	static Airline toEntity(AirlineDto airlineDto) {
		Airline entity = new Airline();
		entity.setId(airlineDto.getId());
		entity.setAirlineName(airlineDto.getAirlineName());
		entity.setDetails(airlineDto.getDetails());
		return entity;
	}
}
