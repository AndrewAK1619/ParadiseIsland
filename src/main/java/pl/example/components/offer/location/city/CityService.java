package pl.example.components.offer.location.city;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pl.example.components.offer.location.country.Country;
import pl.example.components.offer.location.country.CountryRepository;
import pl.example.components.offer.location.region.Region;
import pl.example.components.offer.location.region.RegionRepository;

@Service
public class CityService {

	private CityRepository cityRepository;
	private RegionRepository regionRepository;
	private CountryRepository countryRepository;
	
	@Autowired
    public CityService(CityRepository cityRepository,
    		RegionRepository regionRepository,
    		CountryRepository countryRepository) {
		this.cityRepository = cityRepository;
		this.regionRepository = regionRepository;
		this.countryRepository = countryRepository;
	}

	public List<String> findAllNames() {
        return cityRepository.findAll()
                .stream()
                .map(City::getName)
                .collect(Collectors.toList());
    }
    
	List<String> findAllNamesByCountry(String countryName) {
		Country country = null;
		Optional<Country> countryOptional = countryRepository.findByName(countryName);
		if (countryOptional.isPresent()) {
			country = countryOptional.get();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
					"Country not found");
		}
		List<Region> regionList = regionRepository.findAllByCountry(country);
		List<String> cityNames = new ArrayList<>();
		
		for (Region region : regionList) {
			cityNames.addAll((cityRepository.findAllByRegion(region)
					.stream()
					.map(City::getName)
					.map(name -> name + " (" + region.getName() + ")")
					.collect(Collectors.toList())));
		}
		return cityNames;
	}
}
