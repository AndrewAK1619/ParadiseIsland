package pl.example.components.offer.location.region;

import java.util.Optional;

import org.springframework.stereotype.Service;

import pl.example.components.offer.location.country.Country;
import pl.example.components.offer.location.country.CountryRepository;

@Service
public class RegionMapper {
	
	CountryRepository countryRepository;
	
	public RegionMapper(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	RegionDto toDto(Region region) {
		RegionDto dto = new RegionDto();
		dto.setId(region.getId());
		dto.setName(region.getName());
		if (region.getCountry() != null) {
			dto.setCountryName(region.getCountry().getName());
		}
		return dto;
	}
	
	Region toEntity(RegionDto regionDto) {
		Region entity = new Region();
		entity.setId(regionDto.getId());
		entity.setName(regionDto.getName());
		Optional<Country> country = countryRepository.findByName(regionDto.getCountryName());
		country.ifPresent(entity::setCountry);
		return entity;
	}
}
