package pl.example.components.offer.location.city;

import java.util.Optional;

import org.springframework.stereotype.Service;

import pl.example.components.offer.location.region.Region;
import pl.example.components.offer.location.region.RegionRepository;

@Service
public class CityMapper {

	RegionRepository regionRepository;
	
	public CityMapper(RegionRepository regionRepository) {
		this.regionRepository = regionRepository;
	}
	
	CityDto toDto(City city) {
		CityDto dto = new CityDto();
		dto.setId(city.getId());
		dto.setName(city.getName());
		if(city.getRegion() != null) {
			dto.setRegionName(city.getRegion().getName());
		}
		return dto;
	}
	
	City toEntity(CityDto cityDto) {
		City entity = new City();
		entity.setId(cityDto.getId());
		entity.setName(cityDto.getName());
		Optional<Region> region = regionRepository.findByName(cityDto.getRegionName());
		region.ifPresent(entity::setRegion);
		return entity;
	}
}
