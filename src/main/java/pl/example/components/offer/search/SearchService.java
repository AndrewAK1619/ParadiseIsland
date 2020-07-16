package pl.example.components.offer.search;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.example.components.offer.booking.OfferBookingDto;
import pl.example.components.offer.booking.OfferBookingService;
import pl.example.components.offer.hotel.HotelDto;
import pl.example.components.offer.hotel.HotelSearchDto;
import pl.example.components.offer.hotel.HotelService;
import pl.example.components.offer.hotel.room.RoomDto;
import pl.example.components.offer.hotel.room.RoomService;
import pl.example.components.offer.hotel.room.category.RoomCategory;
import pl.example.components.offer.hotel.room.category.RoomCategoryRepository;
import pl.example.components.offer.location.city.City;
import pl.example.components.offer.location.city.CityService;
import pl.example.components.offer.location.country.Country;
import pl.example.components.offer.location.country.CountryService;
import pl.example.components.offer.location.region.Region;
import pl.example.components.offer.location.region.RegionService;
import pl.example.components.offer.transport.airline.Airline;
import pl.example.components.offer.transport.airline.AirlineRepository;
import pl.example.components.offer.transport.airline.offer.AirlineOffer;
import pl.example.components.offer.transport.airline.offer.AirlineOfferDto;
import pl.example.components.offer.transport.airline.offer.AirlineOfferService;

@Service
public class SearchService {

	private static final String CHOSEN_DESTINATION = "chosenDestination";
	private static final String DATA_TYPE = "dataType";
	private static final String DEPARTURE_STRING = "departure";
	private static final String RETURN_DATE_STRING = "returnDate";
	private static final String PERSONS_STRING = "persons";

	private HotelService hotelService;
	private RoomService roomService;
	private RoomCategoryRepository roomCategoryRepository;

	private CountryService countryService;
	private RegionService regionService;
	private CityService cityService;

	private AirlineOfferService airlineOfferService;
	private AirlineRepository airlineRepository;
	private OfferBookingService offerBookingService;

	@Autowired
	public SearchService(
			HotelService hotelService, 
			RoomService roomService,
			RoomCategoryRepository roomCategoryRepository, 
			CountryService countryService, 
			RegionService regionService,
			CityService cityService, 
			AirlineOfferService airlineOfferService, 
			AirlineRepository airlineRepository,
			OfferBookingService offerBookingService) 
	{
		this.hotelService = hotelService;
		this.roomService = roomService;
		this.roomCategoryRepository = roomCategoryRepository;
		this.countryService = countryService;
		this.regionService = regionService;
		this.cityService = cityService;
		this.airlineOfferService = airlineOfferService;
		this.airlineRepository = airlineRepository;
		this.offerBookingService = offerBookingService;
	}

	Map<String, String> convertStringToStringMap(String stringToConvertToMap) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> stringMap = new HashMap<>();

		if (stringToConvertToMap != null) {
			try {
				stringMap = mapper.readValue(
						stringToConvertToMap, new TypeReference<Map<String, String>>() {
				});
			} catch (IOException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
						"Search data not found");
			}
		}
		return stringMap;
	}

	Page<HotelDto> findBySearchData(Map<String, String> searchDataMap, Integer pageNumber) {
		String chosenDestination = searchDataMap.get(CHOSEN_DESTINATION);
		String dataType = searchDataMap.get(DATA_TYPE);

		if (dataType == null) {
			return hotelService.findAllWithPage(pageNumber);
		} else if (dataType.equals("country"))
			return searchHotelsByCountry(chosenDestination, pageNumber);
		else if (dataType.equals("region"))
			return searchHotelsByRegion(chosenDestination, pageNumber);
		else if (dataType.equals("city"))
			return searchHotelsByCity(chosenDestination, pageNumber);
		else if (dataType.equals("hotel")) {
			Long hotelId = Long.valueOf(chosenDestination);
			return hotelService.findByIdWithPage(hotelId, PageRequest.of(pageNumber, 10));
		} else {
			return hotelService.findAllWithPage(pageNumber);
		}
	}

	private Page<HotelDto> searchHotelsByCountry(
			String chosenDestination, Integer pageNumber) {
			
		Optional<Country> countryOpt = countryService.findByName(chosenDestination);
		Country country;
		if (countryOpt.isPresent())
			country = countryOpt.get();
		else
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					"The entered data is incorrect");

		return hotelService.findAllByCountryWithPage(country, pageNumber);
	}

	private Page<HotelDto> searchHotelsByRegion(
			String chosenDestination, Integer pageNumber) {
			
		Optional<Region> regionOpt = regionService.findByName(chosenDestination);
		Region region;
		if (regionOpt.isPresent())
			region = regionOpt.get();
		else
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					"The entered data is incorrect");

		return hotelService.findAllByRegionWithPage(region, pageNumber);
	}

	private Page<HotelDto> searchHotelsByCity(
			String chosenDestination, Integer pageNumber) {
			
		Optional<City> cityOpt = cityService.findByName(chosenDestination);
		City city;
		if (cityOpt.isPresent())
			city = cityOpt.get();
		else
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
					"The entered data is incorrect");

		return hotelService.findAllByCityWithPage(city, pageNumber);
	}

	List<byte[]> getMainImgListInByteByHotelDtoList(Page<HotelDto> hotelDtoList) {
		return hotelService.getMainImgListInByteByHotelDtoList(hotelDtoList);
	}

	List<RoomDto> getAllRoomWhereIsMinimalCostBySearchData(
			Map<String, String> searchDataMap, Page<HotelDto> hotelDtoList) {

		LocalDate departure = getCookieDeparture(searchDataMap);
		LocalDate returnDate = getCookieReturnDate(searchDataMap);
		Integer persons = getCookieNumberOfPersons(searchDataMap);

		return roomService.getAllRoomWhereIsMinimalCostBySearchData(
				hotelDtoList, departure, returnDate, persons);
	}

	AirlineOfferDto getAirlineOfferByDateWhereIsMinimalCost(
			Map<String, String> searchDataMap) {
			
		LocalDate departure = getCookieDeparture(searchDataMap);
		LocalDate returnDate = getCookieReturnDate(searchDataMap);

		List<AirlineOfferDto> airlinesOffer = airlineOfferService
				.getAllAirlineOfferByDepartureAndReturnDateOrderByFlightPrice(
				departure, returnDate);

		if (airlinesOffer.isEmpty()) {
			// method only to put example data
			generateExampleAirlineData(departure, returnDate);
			airlinesOffer = airlineOfferService
					.getAllAirlineOfferByDepartureAndReturnDateOrderByFlightPrice(
					departure, returnDate);
		}
		return airlinesOffer.get(0);
	}

	List<AirlineOfferDto> getAllAirlineOfferByDate(Map<String, String> searchDataMap) {
		LocalDate departure = getCookieDeparture(searchDataMap);
		LocalDate returnDate = getCookieReturnDate(searchDataMap);

		List<AirlineOfferDto> airlinesOffer = airlineOfferService
				.getAirlineOfferByDepartureAndReturnDate(departure, returnDate);

		if (airlinesOffer.isEmpty()) {
			// method only to put example data
			generateExampleAirlineData(departure, returnDate);
			airlinesOffer = airlineOfferService
					.getAirlineOfferByDepartureAndReturnDate(departure, returnDate);
		}
		return airlinesOffer;
	}

	HotelDto getHotelById(Long hotelId) {
		Optional<HotelDto> hotelDto = hotelService.findById(hotelId);
		if (hotelDto.isPresent()) {
			return hotelDto.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
					"Search data not found");
		}
	}

	byte[] getMainImageInByteFromHotel(Long hotelId) throws IOException {
		return hotelService.getMainImageInByteFromHotel(hotelId);
	}

	RoomDto getRoomWhereIsMinimalCost(
			Map<String, String> searchDataMap, HotelDto hotelDto) {

		List<RoomDto> rooms = getAllAvailableRooms(searchDataMap, hotelDto);
		if (rooms.isEmpty()) {
			return null;
		} else {
			return rooms.get(0);
		}
	}

	List<RoomDto> getAllAvailableRooms(
			Map<String, String> searchDataMap, HotelDto hotelDto) {
			
		LocalDate departure = getCookieDeparture(searchDataMap);
		LocalDate returnDate = getCookieReturnDate(searchDataMap);
		Integer persons = getCookieNumberOfPersons(searchDataMap);

		return roomService.findAvailableRooms(hotelDto, departure, returnDate, persons);
	}

	List<RoomDto> getAllAvailableRooms(
			Map<String, String> searchDataMap, 
			HotelDto hotelDto, 
			String roomCategoryName) {

		LocalDate departure = getCookieDeparture(searchDataMap);
		LocalDate returnDate = getCookieReturnDate(searchDataMap);
		Integer persons = getCookieNumberOfPersons(searchDataMap);

		List<RoomCategory> roomCategoryList = roomCategoryRepository
				.findAllByNameContainingIgnoreCaseOrderByIdAsc(roomCategoryName);
		List<RoomDto> listRoom = new ArrayList<>();

		for (RoomCategory roomCategory : roomCategoryList) {
			listRoom.addAll(roomService.findAvailableRooms(
					hotelDto, departure, returnDate, persons, roomCategory));
		}
		return listRoom;
	}

	List<byte[]> getMainImgListInByteByRoomDtoList(List<RoomDto> roomDtoList) {
		return roomService.getMainImgListInByteByRoomDtoList(roomDtoList);
	}

	List<AirlineOfferDto> getAllAirlineOfferByDateAndAirlineName(
			Map<String, String> searchDataMap, String airlineName) {
		LocalDate departure = getCookieDeparture(searchDataMap);
		LocalDate returnDate = getCookieReturnDate(searchDataMap);

		return airlineOfferService.getAllAirlineOfferByDepartureAndReturnDateAndAirlineName(
				departure, returnDate, airlineName);
	}

	private LocalDate getCookieDeparture(Map<String, String> searchDataMap) {
		LocalDate departureVar = LocalDate.now();
		if (searchDataMap.get(DEPARTURE_STRING) != null)
			departureVar = parseDate(searchDataMap.get(DEPARTURE_STRING));
		return departureVar;
	}

	private LocalDate getCookieReturnDate(Map<String, String> searchDataMap) {
		LocalDate returnDateVar = LocalDate.now().plusDays(7);
		if (searchDataMap.get(RETURN_DATE_STRING) != null)
			returnDateVar = parseDate(searchDataMap.get(RETURN_DATE_STRING));
		return returnDateVar;
	}

	private Integer getCookieNumberOfPersons(Map<String, String> searchDataMap) {
		Integer personsVar = 2;
		if (searchDataMap.get(PERSONS_STRING) != null)
			personsVar = Integer.valueOf(searchDataMap.get(PERSONS_STRING));
		return personsVar;
	}

	private LocalDate parseDate(String date) {
		DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(date, datePattern);
	}

	OfferBookingDto saveOfferBooking(
			Long hotelBookingId, 
			String airlineOfferIdStr, 
			int numberOfPersons,
			String totalPrice) {

		return offerBookingService.saveOfferBooking(
				hotelBookingId, airlineOfferIdStr, numberOfPersons, totalPrice);
	}

	List<HotelSearchDto> findAllHotelsSearch() {
		return hotelService.findAllHotelSearch();
	}

	// method only to put example data
	private void generateExampleAirlineData(LocalDate departure, LocalDate returnDate) {
		for (int i = 0; i < 3; i++) {
			AirlineOffer airlineOffer = new AirlineOffer();

			Optional<Airline> airlineOpt = airlineRepository.findRandomAirline();
			airlineOpt.ifPresent(a -> airlineOffer.setAriline(a));

			int departureHour = (int) (Math.random() * (13 - 5 + 1) + 5);
			int departureMin = ((int) (Math.random() * (5 - 0 + 1) + 0)) * 10;
			LocalDateTime departureDateTime = departure.atTime(departureHour, departureMin);
			airlineOffer.setDeparture(departureDateTime);

			int returnHour = (int) (Math.random() * (20 - 14 + 1) + 14);
			int returnMin = ((int) (Math.random() * (5 - 0 + 1) + 0)) * 10;
			LocalDateTime returnDateTime = returnDate.atTime(returnHour, returnMin);
			airlineOffer.setReturnTrip(returnDateTime);

			int flightPrice = (int) (Math.random() * (800 - 100 + 50) + 100);
			System.out.println("flightPrice - " + flightPrice);
			airlineOffer.setFlightPrice(flightPrice);

			airlineOfferService.saveAirlineOffer(airlineOffer);
		}
	}
}
