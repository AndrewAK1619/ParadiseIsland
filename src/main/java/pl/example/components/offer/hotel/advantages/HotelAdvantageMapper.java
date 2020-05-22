package pl.example.components.offer.hotel.advantages;

public class HotelAdvantageMapper {

	public static HotelAdvantageDto toDto(HotelAdvantage advantage) {
		HotelAdvantageDto dto = new HotelAdvantageDto();
		dto.setId(advantage.getId());
		dto.setDescriptionAdvantage(advantage.getDescriptionAdvantage());
		return dto;
	}

	public static HotelAdvantage toEntity(HotelAdvantageDto advantageDto) {
		HotelAdvantage entity = new HotelAdvantage();
		entity.setId(advantageDto.getId());
		entity.setDescriptionAdvantage(advantageDto.getDescriptionAdvantage());
		return entity;
	}
}
