package pl.example.components.offer.hotel;

public class HotelSearchMapper {

	static HotelSearchDto toDto(Hotel hotel) {
		HotelSearchDto dto = new HotelSearchDto();
		dto.setId(hotel.getId());
		dto.setHotelName(hotel.getHotelName());
		if (hotel.getCountry() != null) {
			dto.setCountry(hotel.getCountry().getName());
		}
		if (hotel.getRegion() != null) {
			dto.setRegion(hotel.getRegion().getName());
		}
		if (hotel.getCity() != null) {
			dto.setCity(hotel.getCity().getName());
		}
		return dto;
	}
}
