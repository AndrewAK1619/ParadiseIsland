package pl.example.components.offer.location.country.image;

import org.springframework.stereotype.Service;

@Service
public class CountryImageMapper {

	CountryImageDto toDto(CountryImage countryImage) {
		CountryImageDto dto = new CountryImageDto();
		dto.setId(countryImage.getId());
		dto.setImagePath(countryImage.getImagePath());
		dto.setMainImage(countryImage.isMainImage());
		return dto;
	}
	
	CountryImage toEntity(CountryImageDto countryImageDto) {
		CountryImage entity = new CountryImage();
		entity.setId(countryImageDto.getId());
		entity.setImagePath(countryImageDto.getImagePath());
		entity.setMainImage(countryImageDto.isMainImage());
		return entity;
	}
}
