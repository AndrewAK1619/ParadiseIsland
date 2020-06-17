package pl.example.components.offer.booking;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pl.example.components.offer.hotel.HotelDto;
import pl.example.components.offer.hotel.HotelService;
import pl.example.components.offer.hotel.room.Room;
import pl.example.components.offer.hotel.room.RoomDto;
import pl.example.components.offer.hotel.room.RoomMapper;
import pl.example.components.offer.hotel.room.RoomRepository;

@Service
public class OfferBookingService {

	private static final String DEPARTURE_STRING = "departure";
	private static final String RETURN_DATE_STRING = "returnDate";
	private static final String PERSONS_STRING = "persons";

	private HotelService hotelService;
	private RoomRepository roomRepository;
	private RoomMapper roomMapper;

	public OfferBookingService(HotelService hotelService,
			RoomRepository roomRepository,
			RoomMapper roomMapper) {
		this.hotelService = hotelService;
		this.roomRepository = roomRepository;
		this.roomMapper = roomMapper;
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
		List<Room> rooms = new ArrayList<>();

		rooms = roomRepository.findAvailableRooms(
					hotelDto.getId(), 
					departure.plusDays(1), 
					returnDate.plusDays(1), 
					persons);

		if(rooms.isEmpty()) {
			return null;
		} else {
			return roomMapper.toDto(rooms.get(0));
		}
	}
	
	private LocalDate parseDate(String date) {
		DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(date, datePattern);
	}
}
