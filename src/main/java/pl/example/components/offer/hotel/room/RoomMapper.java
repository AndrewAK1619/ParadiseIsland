package pl.example.components.offer.hotel.room;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.example.components.offer.hotel.Hotel;
import pl.example.components.offer.hotel.HotelRepository;
import pl.example.components.offer.hotel.room.category.RoomCategory;
import pl.example.components.offer.hotel.room.category.RoomCategoryRepository;
import pl.example.components.offer.hotel.room.image.RoomImageRepository;

@Service
public class RoomMapper {

	RoomRepository roomRepository;
	RoomCategoryRepository roomCategoryRepository;
	RoomImageRepository roomImageRepository;
	HotelRepository hotelRepository;

	@Autowired
	public RoomMapper(RoomCategoryRepository roomCategoryRepository, 
			RoomImageRepository roomImageRepository,
			RoomRepository roomRepository, 
			HotelRepository hotelRepository) {
		this.roomCategoryRepository = roomCategoryRepository;
		this.roomImageRepository = roomImageRepository;
		this.roomRepository = roomRepository;
		this.hotelRepository = hotelRepository;
	}

	public RoomDto toDto(Room room) {
		RoomDto dto = new RoomDto();
		dto.setId(room.getId());
		dto.setNumberOfDoubleBeds(room.getNumberOfDoubleBeds());
		dto.setNumberOfSingleBeds(room.getNumberOfSingleBeds());
		dto.setRoomPrice(room.getRoomPrice());
		if (room.getRoomCategory() != null) {
			dto.setRoomCategory(room.getRoomCategory().getName());
		}
		if (room.getHotel().getId() != null) {
			dto.setHotelId(room.getHotel().getId());
		}
		return dto;
	}

	Room toEntity(RoomDto roomDto) {
		Room entity = new Room();
		entity.setId(roomDto.getId());
		entity.setNumberOfDoubleBeds(roomDto.getNumberOfDoubleBeds());
		entity.setNumberOfSingleBeds(roomDto.getNumberOfSingleBeds());
		entity.setRoomPrice(roomDto.getRoomPrice());
		Optional<RoomCategory> roomCategory = roomCategoryRepository
				.findByName(roomDto.getRoomCategory().trim());
		roomCategory.ifPresent(entity::setRoomCategory);
		Optional<Hotel> hotel = hotelRepository.findById(roomDto.getHotelId());
		hotel.ifPresent(entity::setHotel);
		return entity;
	}
}
