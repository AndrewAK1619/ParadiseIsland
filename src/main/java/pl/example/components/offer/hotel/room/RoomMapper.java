package pl.example.components.offer.hotel.room;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.example.components.offer.hotel.room.category.RoomCategory;
import pl.example.components.offer.hotel.room.category.RoomCategoryRepository;

@Service
public class RoomMapper {

	RoomCategoryRepository roomCategoryRepository;

	@Autowired
	public RoomMapper(RoomCategoryRepository roomCategoryRepository) {
		this.roomCategoryRepository = roomCategoryRepository;
	}

	RoomDto toDto(Room room) {
		RoomDto dto = new RoomDto();
		dto.setId(room.getId());
		dto.setNumberOfDoubleBeds(room.getNumberOfDoubleBeds());
		dto.setNumberOfSingleBeds(room.getNumberOfSingleBeds());
		dto.setRoomPrice(room.getRoomPrice());
		if (room.getRoomCategory() != null) {
			dto.setRoomCategory(room.getRoomCategory().getName());
		}
		return dto;
	}

	Room toEntity(RoomDto roomDto) {
		Room entity = new Room();
		entity.setId(roomDto.getId());
		entity.setNumberOfDoubleBeds(roomDto.getNumberOfDoubleBeds());
		entity.setNumberOfSingleBeds(roomDto.getNumberOfSingleBeds());
		entity.setRoomPrice(roomDto.getRoomPrice());
		Optional<RoomCategory> roomCategory = roomCategoryRepository.findByName(roomDto.getRoomCategory());
		roomCategory.ifPresent(entity::setRoomCategory);
		return entity;
	}
}
