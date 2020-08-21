package pl.example.components.offer.hotel.advantages;

import java.util.Optional;

import org.springframework.stereotype.Service;

import pl.example.components.offer.hotel.Hotel;
import pl.example.components.offer.hotel.HotelRepository;

@Service
public class HotelAdvantageMapper {

	HotelRepository hotelRepository;

	public HotelAdvantageMapper(HotelRepository hotelRepository) {
		this.hotelRepository = hotelRepository;
	}

	public static HotelAdvantageDto toDto(HotelAdvantage advantage) {
		HotelAdvantageDto dto = new HotelAdvantageDto();
		dto.setId(advantage.getId());
		dto.setDescriptionAdvantage(advantage.getDescriptionAdvantage());
		dto.setHotelId(advantage.getHotel().getId());
		return dto;
	}

	HotelAdvantage toEntity(HotelAdvantageDto advantageDto) {
		HotelAdvantage entity = new HotelAdvantage();
		entity.setId(advantageDto.getId());
		entity.setDescriptionAdvantage(advantageDto.getDescriptionAdvantage());
		Optional<Hotel> hotel = hotelRepository.findById(advantageDto.getHotelId());
		hotel.ifPresent(entity::setHotel);
		return entity;
	}
}
