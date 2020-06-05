package pl.example.components.offer.booking;

import pl.example.components.offer.hotel.Hotel;

public class SearchHotelMapper {
	
	static SearchHotelDto toDto(Hotel hotel) {
		SearchHotelDto dto = new SearchHotelDto();
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
