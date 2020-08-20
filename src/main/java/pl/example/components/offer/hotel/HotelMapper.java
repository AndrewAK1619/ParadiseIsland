package pl.example.components.offer.hotel;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import pl.example.components.offer.hotel.advantages.HotelAdvantageMapper;
import pl.example.components.offer.location.city.City;
import pl.example.components.offer.location.city.CityRepository;
import pl.example.components.offer.location.country.Country;
import pl.example.components.offer.location.country.CountryRepository;
import pl.example.components.offer.location.region.Region;
import pl.example.components.offer.location.region.RegionRepository;

@Service
public class HotelMapper {

	private CountryRepository countryRepository;
	private RegionRepository regionRepository;
	private CityRepository cityRepository;

	public HotelMapper(
			CountryRepository countryRepository, 
			RegionRepository regionRepository, 
			CityRepository cityRepository) {
		this.countryRepository = countryRepository;
		this.regionRepository = regionRepository;
		this.cityRepository = cityRepository;
	}

	HotelDto toDto(Hotel hotel) {
		HotelDto dto = new HotelDto();
		dto.setId(hotel.getId());
		dto.setHotelName(hotel.getHotelName());
		dto.setDescription(hotel.getDescription());
		if (hotel.getHotelAdvantages() != null) {
			dto.setHotelAdvantageDto(hotel.getHotelAdvantages()
			.stream()
			.map(HotelAdvantageMapper::toDto)
			.collect(Collectors.toList()));
		}
		if (hotel.getCountry() != null) {
			dto.setCountry(hotel.getCountry().getName());
		}
		if (hotel.getRegion() != null) {
			dto.setRegion(hotel.getRegion().getName());
		}
		if (hotel.getCity() != null) {
			dto.setCity(hotel.getCity().getName());
		}
		return dto;
	}

	Hotel toEntity(HotelDto hotelDto) {
		Hotel entity = new Hotel();
		entity.setId(hotelDto.getId());
		entity.setHotelName(hotelDto.getHotelName());
		entity.setDescription(hotelDto.getDescription());

		Optional<Country> country = countryRepository
				.findByName(hotelDto.getCountry());
		Optional<Region> region = null;
		if (country.isPresent()) {
			entity.setCountry(country.get());
			region = regionRepository
					.findByNameAndCountry(hotelDto.getRegion(), country.get());
		}
		Optional<City> city = null;
		if (region.isPresent()) {
			entity.setRegion(region.get());
			city = cityRepository
					.findByNameAndRegion(hotelDto.getCity(), region.get());
		}
		city.ifPresent(entity::setCity);
		return entity;
	}
}
