package pl.example.components.offer.hotel.room.category;

public class RoomCategoryMapper {

	static RoomCategoryDto toDto(RoomCategory roomCategory) {
		RoomCategoryDto dto = new RoomCategoryDto();
		dto.setId(roomCategory.getId());
		dto.setName(roomCategory.getName());
		return dto;
	}

	static RoomCategory toEntity(RoomCategoryDto roomCategoryDto) {
		RoomCategory entity = new RoomCategory();
		entity.setId(roomCategoryDto.getId());
		entity.setName(roomCategoryDto.getName());
		return entity;
	}
}
