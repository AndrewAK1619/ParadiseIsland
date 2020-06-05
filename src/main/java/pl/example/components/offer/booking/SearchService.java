package pl.example.components.offer.booking;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.example.components.offer.hotel.HotelRepository;
import pl.example.components.offer.location.city.CityService;
import pl.example.components.offer.location.country.CountryService;
import pl.example.components.offer.location.region.RegionService;

@Service
public class SearchService {

	private HotelRepository hotelRepository;
	private CountryService countryService;
	private RegionService regionService;
	private CityService cityService;

	@Autowired
	public SearchService(HotelRepository hotelRepository, 
			CountryService countryService,
			RegionService regionService,
			CityService cityService) {
		this.hotelRepository = hotelRepository;
		this.countryService = countryService;
		this.regionService = regionService;
		this.cityService = cityService;
	}
	
	List<SearchHotelDto> findAllSearchHotels() {
		return hotelRepository.findAll()
				.stream()
                .map(SearchHotelMapper::toDto)
                .collect(Collectors.toList());
	}
	
	List<String> findAllCountriesNames() {
		return countryService.findAllNames();
	}
	
	List<String> findAllRegionsNames() {
		return regionService.findAllNames();
	}
	
	List<String> findAllCitiesNames() {
		return cityService.findAllNames();
	}
}
