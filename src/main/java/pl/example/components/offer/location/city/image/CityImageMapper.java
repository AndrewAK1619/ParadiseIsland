package pl.example.components.offer.location.city.image;

public class CityImageMapper {

	static CityImageDto toDto(CityImage cityImage) {
		CityImageDto dto = new CityImageDto();
		dto.setId(cityImage.getId());
		dto.setImagePath(cityImage.getImagePath());
		dto.setMainImage(cityImage.isMainImage());
		return dto;
	}
	
	static CityImage toEntity(CityImageDto cityImageDto) {
		CityImage entity = new CityImage();
		entity.setId(cityImageDto.getId());
		entity.setImagePath(cityImageDto.getImagePath());
		entity.setMainImage(cityImageDto.isMainImage());
		return entity;
	}
}
