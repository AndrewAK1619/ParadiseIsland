package pl.example.components.offer.location.country.information;

import org.springframework.stereotype.Service;

@Service
public class CountryInformationMapper {

	public CountryInformationDto toDto(CountryInformation countryInformation) {
		CountryInformationDto dto = new CountryInformationDto();
		dto.setId(countryInformation.getId());
		dto.setInformation(countryInformation.getInformation());
		return dto;
	}
	
	CountryInformation toEntity(CountryInformationDto countryInformationDto) {
		CountryInformation entity = new CountryInformation();
		entity.setId(countryInformationDto.getId());
		entity.setInformation(countryInformationDto.getInformation());
		return entity;
	}
}
