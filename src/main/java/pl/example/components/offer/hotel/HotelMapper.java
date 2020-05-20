package pl.example.components.offer.hotel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import pl.example.components.offer.hotel.image.HotelImage;
import pl.example.components.offer.hotel.image.HotelImageRepository;
import pl.example.components.offer.location.city.City;
import pl.example.components.offer.location.city.CityRepository;
import pl.example.components.offer.location.country.Country;
import pl.example.components.offer.location.country.CountryRepository;
import pl.example.components.offer.location.region.Region;
import pl.example.components.offer.location.region.RegionRepository;

@Service
public class HotelMapper {

	private HotelRepository hotelRepository;
	private HotelImageRepository hotelImageRepository;
	private CountryRepository countryRepository;
	private RegionRepository regionRepository;
	private CityRepository cityRepository;

	public HotelMapper(HotelRepository hotelRepository, 
			HotelImageRepository hotelImageRepository,
			CountryRepository countryRepository,
			RegionRepository regionRepository,
			CityRepository cityRepository) {
		this.hotelRepository = hotelRepository;
		this.hotelImageRepository = hotelImageRepository;
		this.countryRepository = countryRepository;
		this.regionRepository = regionRepository;
		this.cityRepository = cityRepository;
	}

	HotelDto toDto(Hotel hotel) {
		HotelDto dto = new HotelDto();
		dto.setId(hotel.getId());
		dto.setHotelName(hotel.getHotelName());
		dto.setDescription(hotel.getDescription());
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

		List<HotelImage> hotelImages = addCorrectHotelImageList(hotelDto);

		entity.setHotelImages(hotelImages);
		Optional<Country> country = countryRepository.findByName(hotelDto.getCountry());
		Optional<Region> region = null;
		if (country.isPresent()) {
			entity.setCountry(country.get());
			region = regionRepository.findByNameAndCountry(hotelDto.getRegion(), country.get());
		}
		Optional<City> city = null;
		if (region.isPresent()) {
			entity.setRegion(region.get());
			city = cityRepository.findByNameAndRegion(hotelDto.getCity(), region.get());
		}
		city.ifPresent(entity::setCity);
		return entity;
	}

	private List<HotelImage> addCorrectHotelImageList(HotelDto hotelDto) {
		List<HotelImage> hotelImages = new ArrayList<>();
		if (hotelDto.getId() != null && hotelDto.getMainImageId() != null) {
			hotelImages = getHotelImageFromHotel(hotelDto, hotelImages);
			hotelImages.stream().forEach(hotelImg -> hotelImg.setMainImage(false));
		} else if (hotelDto.getId() != null && hotelDto.getMainImageId() == null) {
			hotelImages = getHotelImageFromHotel(hotelDto, hotelImages);
		}
		if (hotelDto.getMainImageId() != null) {
			Optional<HotelImage> mainHotelImage = hotelImageRepository
					.findById(hotelDto.getMainImageId());
			if (mainHotelImage.isPresent())
				hotelImages.add(mainHotelImage.get());
		}
		return hotelImages;
	}

	private List<HotelImage> getHotelImageFromHotel(HotelDto hotelDto, 
			List<HotelImage> hotelImages) {
		Optional<Hotel> hotelBeforeUpdated = hotelRepository.findById(hotelDto.getId());
		if (hotelBeforeUpdated.isPresent()) {
			hotelImages = hotelBeforeUpdated.get().getHotelImages();
		}
		return hotelImages;
	}
}
