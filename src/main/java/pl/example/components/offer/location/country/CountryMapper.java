package pl.example.components.offer.location.country;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import pl.example.components.offer.location.country.information.CountryInformationMapper;

@Service
public class CountryMapper {
	
	private CountryInformationMapper countryInformationMapper;
	
	public CountryMapper(CountryInformationMapper countryInformationMapper) {
		this.countryInformationMapper = countryInformationMapper;
	}
	
	CountryDto toDto(Country country) {
		CountryDto dto = new CountryDto();
		dto.setId(country.getId());
		dto.setAlpha2Code(country.getAlpha2Code());
		dto.setName(country.getName());
		dto.setPhonecode(country.getPhonecode());
		dto.setCountryInformationDto(country.getCountryInformation()
				.stream()
				.map(countryInformationMapper::toDto)
				.collect(Collectors.toList()));
		return dto;
	}
	
	Country toEntity(CountryDto countryDto) {
		Country entity = new Country();
		entity.setId(countryDto.getId());
		entity.setAlpha2Code(countryDto.getAlpha2Code());
		entity.setName(countryDto.getName());
		entity.setPhonecode(countryDto.getPhonecode());
		return entity;
	}
}
