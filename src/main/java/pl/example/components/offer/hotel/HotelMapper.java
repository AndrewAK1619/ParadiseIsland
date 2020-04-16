package pl.example.components.offer.hotel;

public class HotelMapper {

	HotelDto toDto(Hotel hotel) {
		HotelDto dto = new HotelDto();
		dto.setId(hotel.getId());
		dto.setHotelName(hotel.getHotelName());
		dto.setDescription(hotel.getDescription());
		dto.setRoom(hotel.getRoom());		
		return dto;
	}
	
	Hotel toEntity(HotelDto hotelDto) {
		Hotel entity = new Hotel();
		entity.setId(hotelDto.getId());
		entity.setHotelName(hotelDto.getHotelName());
		entity.setDescription(hotelDto.getDescription());
		entity.setRoom(hotelDto.getRoom());
		return entity;
	}
}
