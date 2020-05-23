package pl.example.components.offer.hotel.booking;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.example.components.offer.hotel.Hotel;
import pl.example.components.offer.hotel.HotelRepository;
import pl.example.components.offer.hotel.room.Room;
import pl.example.components.offer.hotel.room.RoomRepository;

@Service
public class HotelBookingMapper {

	HotelRepository hotelRepository;
	RoomRepository roomRepository;

	@Autowired
	HotelBookingMapper(HotelRepository hotelRepository, 
			RoomRepository roomRepository) {
		this.hotelRepository = hotelRepository;
		this.roomRepository = roomRepository;
	}

	HotelBookingDto toDto(HotelBooking hotelBooking) {
		HotelBookingDto dto = new HotelBookingDto();
		dto.setId(hotelBooking.getId());
		if (hotelBooking.getHotel() != null) {
			dto.setHotelId(hotelBooking.getHotel().getId());
		}
		if (hotelBooking.getRoom() != null) {
			dto.setRoomId(hotelBooking.getRoom().getId());
		}
		dto.setStartBookingRoom(hotelBooking.getStartBookingRoom());
		dto.setEndBookingRoom(hotelBooking.getEndBookingRoom());
		dto.setBookingPrice(hotelBooking.getBookingPrice());
		return dto;
	}
	
	HotelBooking toEntity(HotelBookingDto hotelBookingDto) {
		HotelBooking entity = new HotelBooking();
		entity.setId(hotelBookingDto.getId());
		Optional<Hotel> hotel = hotelRepository.findById(hotelBookingDto.getHotelId());
		hotel.ifPresent(entity::setHotel);
		Optional<Room> room = roomRepository.findById(hotelBookingDto.getRoomId());
		room.ifPresent(entity::setRoom);
		entity.setStartBookingRoom(hotelBookingDto.getStartBookingRoom());
		entity.setEndBookingRoom(hotelBookingDto.getEndBookingRoom());
		entity.setBookingPrice(hotelBookingDto.getBookingPrice());
		return entity;
	}
}
