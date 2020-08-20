package pl.example.components.offer.hotel.room.image;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.example.components.offer.hotel.room.Room;
import pl.example.components.offer.hotel.room.RoomRepository;

@Service
public class RoomImageMapper {

	RoomRepository roomRepository;
	
	@Autowired
	public RoomImageMapper(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	static RoomImageDto toDto(RoomImage roomImage) {
		RoomImageDto dto = new RoomImageDto();
		dto.setId(roomImage.getId());
		dto.setImagePath(roomImage.getImagePath());
		dto.setMainImage(roomImage.isMainImage());
		return dto;
	}

	RoomImage toEntity(RoomImageDto roomImageDto) {
		RoomImage entity = new RoomImage();
		entity.setId(roomImageDto.getId());
		entity.setImagePath(roomImageDto.getImagePath());
		entity.setMainImage(roomImageDto.isMainImage());
		Optional<Room> room = roomRepository
				.findById(roomImageDto.getRoomId());
		room.ifPresent(entity::setRoom);
		return entity;
	}
}
