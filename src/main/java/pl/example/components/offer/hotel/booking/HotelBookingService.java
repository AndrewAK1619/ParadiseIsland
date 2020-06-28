package pl.example.components.offer.hotel.booking;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class HotelBookingService {

	private HotelBookingRepository hotelBookingRepository;
	private HotelBookingMapper hotelBookingMapper;

	public HotelBookingService(
			HotelBookingRepository hotelBookingRepository,
			HotelBookingMapper hotelBookingMapper) {
		this.hotelBookingRepository = hotelBookingRepository;
		this.hotelBookingMapper = hotelBookingMapper;
	}

	public HotelBooking saveHotelBooking(
			String hotelIdString,
			String roomIdString,
			String hotelTotalPriceString,
			String startBookingDateString,
			String endBookingDateString) {
		
		LocalDateTime startBookingDate = convertStringToDate(startBookingDateString, 14);
		LocalDateTime endBookingDate = convertStringToDate(endBookingDateString, 12);
		
		HotelBookingDto dto = new HotelBookingDto();
		dto.setHotelId(Long.valueOf(hotelIdString));
		dto.setRoomId(Long.valueOf(roomIdString));
		dto.setStartBookingRoom(startBookingDate);
		dto.setEndBookingRoom(endBookingDate);
		dto.setBookingPrice(Float.valueOf(hotelTotalPriceString));
		
		return hotelBookingRepository.save(hotelBookingMapper.toEntity(dto));
	}
	
	private LocalDateTime convertStringToDate(String strDate, int hour) {
		String[] strDateArray = strDate.split("-");
		int year = Integer.valueOf(strDateArray[0]);
		int month = Integer.valueOf(strDateArray[1]);
		int day = Integer.valueOf(strDateArray[2]);
		
		return LocalDateTime.of(year, month, day, hour, 00, 00);
	}
}
