package pl.example.components.offer.transport.airline;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AirlineService {

	private AirlineRepository airlineRepository;

	@Autowired
	public AirlineService(AirlineRepository airlineRepository) {
		this.airlineRepository = airlineRepository;
	}

	Optional<AirlineDto> findById(Long id) {
		return airlineRepository
				.findById(id)
				.map(AirlineMapper::toDto);
	}

	List<AirlineDto> findAll() {
		return airlineRepository.findAllByOrderByIdAsc()
				.stream()
				.map(AirlineMapper::toDto)
				.collect(Collectors.toList());
	}

	List<AirlineDto> findByAirlineName(String airlineName) {
		return airlineRepository
				.findAllByAirlineNameContainingIgnoreCaseOrderByIdAsc(airlineName)
				.stream()
				.map(AirlineMapper::toDto)
				.collect(Collectors.toList());
	}

	AirlineDto save(AirlineDto airline) {
		Optional<Airline> airlineByEmail = airlineRepository
				.findByAirlineName(airline.getAirlineName());
		airlineByEmail.ifPresent(u -> {
			throw new ResponseStatusException(HttpStatus.CONFLICT, 
					"A airline with this name already exists");
		});
		return mapAndSaveAirline(airline);
	}

	AirlineDto update(AirlineDto airline) {
		Optional<Airline> airlineByAirlineName = airlineRepository
				.findByAirlineName(airline.getAirlineName());
		airlineByAirlineName.ifPresent(u -> {
			if (!u.getId().equals(airline.getId()))
				throw new ResponseStatusException(HttpStatus.CONFLICT, 
						"A airline with this name already exists");
		});
		return mapAndSaveAirline(airline);
	}

	private AirlineDto mapAndSaveAirline(AirlineDto airline) {
		Airline airlineEntity = AirlineMapper.toEntity(airline);
		Airline savedAirline = airlineRepository.save(airlineEntity);
		return AirlineMapper.toDto(savedAirline);
	}
}
