package pl.example.components.offer.hotel.image;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.example.components.offer.hotel.Hotel;
import pl.example.components.offer.hotel.HotelRepository;

@Service
public class HotelImageMapper {

	private HotelRepository hotelRepository;

	@Autowired
	public HotelImageMapper(HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}

	static HotelImageDto toDto(HotelImage hotelImage) {
		HotelImageDto dto = new HotelImageDto();
		dto.setId(hotelImage.getId());
		dto.setImagePath(hotelImage.getImagePath());
		dto.setMainImage(hotelImage.isMainImage());
		return dto;
	}

	HotelImage toEntity(HotelImageDto hotelImageDto) {
		HotelImage entity = new HotelImage();
		entity.setId(hotelImageDto.getId());
		entity.setImagePath(hotelImageDto.getImagePath());
		entity.setMainImage(hotelImageDto.isMainImage());
		Optional<Hotel> hotel = hotelRepository
				.findById(hotelImageDto.getHotelId());
		hotel.ifPresent(entity::setHotel);
		return entity;
	}
}
