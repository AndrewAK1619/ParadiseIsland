package pl.example.components.offer.booking;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pl.example.components.offer.hotel.Hotel;
import pl.example.components.offer.hotel.HotelDto;
import pl.example.components.offer.hotel.HotelMapper;
import pl.example.components.offer.hotel.HotelRepository;
import pl.example.components.offer.hotel.room.Room;
import pl.example.components.offer.hotel.room.RoomDto;
import pl.example.components.offer.hotel.room.RoomMapper;
import pl.example.components.offer.hotel.room.RoomRepository;
import pl.example.components.offer.location.city.City;
import pl.example.components.offer.location.city.CityService;
import pl.example.components.offer.location.country.Country;
import pl.example.components.offer.location.country.CountryService;
import pl.example.components.offer.location.region.Region;
import pl.example.components.offer.location.region.RegionService;

@Service
public class SearchService {

	private static final String CHOSEN_DESTINATION = "chosenDestination";
	private static final String DATA_TYPE = "dataType";
	private static final String DEPARTURE_STRING = "departure";
	private static final String RETURN_DATE_STRING = "returnDate";
	private static final String PERSONS_STRING = "persons";

	private HotelRepository hotelRepository;
	private HotelMapper hotelMapper;
	private RoomRepository roomRepository;
	private RoomMapper roomMapper;
	private CountryService countryService;
	private RegionService regionService;
	private CityService cityService;

	@Autowired
	public SearchService(HotelRepository hotelRepository, 
			HotelMapper hotelMapper, 
			RoomRepository roomRepository,
			RoomMapper roomMapper, 
			CountryService countryService, 
			RegionService regionService,
			CityService cityService) 
	{
		this.hotelRepository = hotelRepository;
		this.hotelMapper = hotelMapper;
		this.roomRepository = roomRepository;
		this.roomMapper = roomMapper;
		this.countryService = countryService;
		this.regionService = regionService;
		this.cityService = cityService;
	}

	List<SearchHotelDto> findAllSearchHotels() {
		return hotelRepository.findAll()
				.stream()
				.map(SearchHotelMapper::toDto)
				.collect(Collectors.toList());
	}

	List<String> findAllCountriesNames() {
		return countryService.findAllNames();
	}

	List<String> findAllRegionsNames() {
		return regionService.findAllNames();
	}

	List<String> findAllCitiesNames() {
		return cityService.findAllNames();
	}

	Page<HotelDto> findBySearchData(Map<String, String> searchDataMap, Integer pageNumber) {
		String chosenDestination = searchDataMap.get(CHOSEN_DESTINATION);
		String dataType = searchDataMap.get(DATA_TYPE);

		if (dataType == null) {
			Page<Hotel> hotelPage = hotelRepository
					.findAll(PageRequest.of(pageNumber, 10));
			return hotelPage.map(hotelMapper::toDto);
		} else if (dataType.equals("country"))
			return searchHotelsByCountry(chosenDestination, pageNumber);
		else if (dataType.equals("region"))
			return searchHotelsByRegion(chosenDestination, pageNumber);
		else if (dataType.equals("city"))
			return searchHotelsByCity(chosenDestination, pageNumber);
		else if (dataType.equals("hotel")) {
			Long hotelId = Long.valueOf(chosenDestination);
			Page<Hotel> hotelPage = hotelRepository
					.findById(hotelId, PageRequest.of(pageNumber, 10));
			return hotelPage.map(hotelMapper::toDto);
		} else {
			Page<Hotel> hotelPage = hotelRepository
					.findAll(PageRequest.of(pageNumber, 10));
			return hotelPage.map(hotelMapper::toDto);
		}
	}

	private Page<HotelDto> searchHotelsByCountry(String chosenDestination, Integer pageNumber) {
		Optional<Country> countryOpt = countryService.findByName(chosenDestination);
		Country country;
		if (countryOpt.isPresent())
			country = countryOpt.get();
		else
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					"The entered data is incorrect");
					
		Page<Hotel> hotelPage = hotelRepository
				.findAllByCountry(country, PageRequest.of(pageNumber, 10));
		return hotelPage.map(hotelMapper::toDto);
	}

	private Page<HotelDto> searchHotelsByRegion(String chosenDestination, Integer pageNumber) {
		Optional<Region> regionOpt = regionService.findByName(chosenDestination);
		Region region;
		if (regionOpt.isPresent())
			region = regionOpt.get();
		else
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					"The entered data is incorrect");
					
		Page<Hotel> hotelPage = hotelRepository
				.findAllByRegion(region, PageRequest.of(pageNumber, 10));
		return hotelPage.map(hotelMapper::toDto);
	}

	private Page<HotelDto> searchHotelsByCity(String chosenDestination, Integer pageNumber) {
		Optional<City> cityOpt = cityService.findByName(chosenDestination);
		City city;
		if (cityOpt.isPresent())
			city = cityOpt.get();
		else
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
			"The entered data is incorrect");
			
		Page<Hotel> hotelPage = hotelRepository
				.findAllByCity(city, PageRequest.of(pageNumber, 10));
				
		return hotelPage.map(hotelMapper::toDto);
	}

	List<RoomDto> getRoomWhereIsMinimalCost(
			Map<String, String> searchDataMap, 
			Page<HotelDto> hotelDtoList) {
		LocalDate departureVar = LocalDate.now();
		LocalDate returnDateVar = departureVar.plusDays(7);
		Integer personsVar = 2;

		if (searchDataMap.get(DEPARTURE_STRING) != null)
			departureVar = parseDate(searchDataMap.get(DEPARTURE_STRING));
		if (searchDataMap.get(RETURN_DATE_STRING) != null)
			returnDateVar = parseDate(searchDataMap.get(RETURN_DATE_STRING));
		if (searchDataMap.get(PERSONS_STRING) != null)
			personsVar = Integer.valueOf(searchDataMap.get(PERSONS_STRING));

		final LocalDate departure = departureVar;
		final LocalDate returnDate = returnDateVar;
		final Integer persons = personsVar;
		List<List<Room>> rooms = new ArrayList<>();

		rooms = hotelDtoList
				.stream()
				.map(h -> roomRepository.findAvailableRooms(
								h.getId(), 
								departure.plusDays(1), 
								returnDate.plusDays(1), 
								persons))
				.collect(Collectors.toList());

		return rooms.stream()
				.map(rs -> {
					if(rs.isEmpty()) {
						return null;
					} else {
						return rs.get(0);
					}
				})
				.map(r -> {
					if(r == null) {
						return null;
					} else {
						return roomMapper.toDto(r);
					}
				})
				.collect(Collectors.toList());
	}

	private LocalDate parseDate(String date) {
		DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(date, datePattern);
	}
}
