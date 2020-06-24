package pl.example.components.offer.booking;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pl.example.components.offer.hotel.HotelDto;
import pl.example.components.offer.hotel.HotelService;
import pl.example.components.offer.hotel.room.Room;
import pl.example.components.offer.hotel.room.RoomDto;
import pl.example.components.offer.hotel.room.RoomMapper;
import pl.example.components.offer.hotel.room.RoomRepository;
import pl.example.components.offer.hotel.room.RoomService;
import pl.example.components.offer.hotel.room.category.RoomCategory;
import pl.example.components.offer.hotel.room.category.RoomCategoryRepository;
import pl.example.components.offer.transport.airline.offer.AirlineOfferDto;

@Service
public class OfferBookingService {

	private static final String DEPARTURE_STRING = "departure";
	private static final String RETURN_DATE_STRING = "returnDate";
	private static final String PERSONS_STRING = "persons";

	private HotelService hotelService;
	private RoomRepository roomRepository;
	private RoomMapper roomMapper;
	private RoomService roomService;
	private RoomCategoryRepository roomCategoryRepository;
	private SearchService searchService;

	public OfferBookingService(HotelService hotelService,
			RoomRepository roomRepository,
			RoomMapper roomMapper,
			RoomService roomService,
			RoomCategoryRepository roomCategoryRepository,
			SearchService searchService) {
		this.hotelService = hotelService;
		this.roomRepository = roomRepository;
		this.roomMapper = roomMapper;
		this.roomService = roomService;
		this.roomCategoryRepository = roomCategoryRepository;
		this.searchService = searchService;
	}
	
	HotelDto getHotelById(Long hotelId) {
		Optional<HotelDto> hotelDto = hotelService.findById(hotelId);
		if(hotelDto.isPresent()) {
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
			Map<String, String> searchDataMap, 
			HotelDto hotelDto) {

		List<RoomDto> rooms = getAllAvailableRooms(searchDataMap, hotelDto);

		if(rooms.isEmpty()) {
			return null;
		} else {
			return rooms.get(0);
		}
	}
	
	List<RoomDto> getAllAvailableRooms(
			Map<String, String> searchDataMap, 
			HotelDto hotelDto) {
		final LocalDate departure = getCookieDeparture(searchDataMap);
		final LocalDate returnDate = getCookieReturnDate(searchDataMap);
		final Integer persons = getNumberOfPersons(searchDataMap);

		return roomRepository.findAvailableRooms(
					hotelDto.getId(), 
					departure.plusDays(1), 
					returnDate.plusDays(1), 
					persons)
				.stream()
				.map(r -> roomMapper.toDto(r))
				.collect(Collectors.toList());
	}
	
	List<RoomDto> getAllAvailableRooms(
			Map<String, String> searchDataMap, 
			HotelDto hotelDto,
			String roomCategoryName) {
		final LocalDate departure = getCookieDeparture(searchDataMap);
		final LocalDate returnDate = getCookieReturnDate(searchDataMap);
		final Integer persons = getNumberOfPersons(searchDataMap);

		List<RoomCategory> roomCategoryList = roomCategoryRepository
				.findAllByNameContainingIgnoreCaseOrderByIdAsc(roomCategoryName);
		List<Room> listRoom = new ArrayList<>();

		for (RoomCategory roomCategory : roomCategoryList) {
			listRoom.addAll(roomRepository
					.findAvailableRoomsByCategory(
							hotelDto.getId(),
							departure.plusDays(1),
							returnDate.plusDays(1),
							persons,
							roomCategory.getId()));
		}
		return listRoom.stream()
				.map(r -> roomMapper.toDto(r))
				.collect(Collectors.toList());
	}
	
	AirlineOfferDto getAirlineOfferWhereIsMinimalCost(Map<String, String> searchDataMap) {
		return searchService.getAirlineOfferByDateWhereIsMinimalCost(searchDataMap);
	}
	
	List<AirlineOfferDto> getAllAirlineOfferByDate(Map<String, String> searchDataMap) {
		return searchService.getAllAirlineOfferByDate(searchDataMap);
	}
	
	private LocalDate getCookieDeparture(Map<String, String> searchDataMap) {
		LocalDate departureVar = LocalDate.now();
		if (searchDataMap.get(DEPARTURE_STRING) != null)
			departureVar = parseDate(searchDataMap.get(DEPARTURE_STRING));
		return departureVar;
	}
	
	private LocalDate getCookieReturnDate(Map<String, String> searchDataMap) {
		LocalDate returnDateVar =  LocalDate.now().plusDays(7);
		if (searchDataMap.get(RETURN_DATE_STRING) != null)
			returnDateVar = parseDate(searchDataMap.get(RETURN_DATE_STRING));
		return returnDateVar;
	}
	
	private Integer getNumberOfPersons(Map<String, String> searchDataMap) {
		Integer personsVar = 2;
		if (searchDataMap.get(PERSONS_STRING) != null)
			personsVar = Integer.valueOf(searchDataMap.get(PERSONS_STRING));
		return personsVar;
	}
	
	List<byte[]> getMainImgListInByteByRoomDtoList(List<RoomDto> roomDtoList) {
		return roomService.getMainImgListInByteByRoomDtoList(roomDtoList);
	}
	
	private LocalDate parseDate(String date) {
		DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(date, datePattern);
	}
}
