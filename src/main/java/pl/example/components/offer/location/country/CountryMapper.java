package pl.example.components.offer.location.country;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryMapper {
	
	CountryRepository countryRepository;
	
	@Autowired
	public CountryMapper(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}
	
	CountryDto toDto(Country country) {
		CountryDto dto = new CountryDto();
		dto.setId(country.getId());
		dto.setAlpha2Code(country.getAlpha2Code());
		dto.setName(country.getName());
		dto.setPhonecode(country.getPhonecode());
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
