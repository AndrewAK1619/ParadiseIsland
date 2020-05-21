package pl.example.components.offer.hotel.room.image;

public class RoomImageMapper {

	static RoomImageDto toDto(RoomImage roomImage) {
		RoomImageDto roomImageDto = new RoomImageDto();
		roomImageDto.setId(roomImage.getId());
		roomImageDto.setImagePath(roomImage.getImagePath());
		roomImageDto.setMainImage(roomImage.isMainImage());
		return roomImageDto;
	}

	static RoomImage toEntity(RoomImageDto roomImageDto) {
		RoomImage roomImage = new RoomImage();
		roomImage.setId(roomImageDto.getId());
		roomImage.setImagePath(roomImageDto.getImagePath());
		roomImage.setMainImage(roomImageDto.isMainImage());
		return roomImage;
	}
}
