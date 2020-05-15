package pl.example.components.offer.location.country;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pl.example.components.offer.location.country.image.CountryImage;
import pl.example.components.offer.location.country.image.CountryImageService;

@Service
public class CountryService {

	CountryRepository countryRepository;
	CountryMapper countryMapper;
	CountryImageService countryImageService;
	
	@Autowired
	public CountryService(CountryRepository countryRepository,
			CountryMapper countryMapper,
			CountryImageService countryImageService) {
		this.countryRepository = countryRepository;
		this.countryMapper = countryMapper;
		this.countryImageService = countryImageService;
	}
	
	List<CountryDto> findPopular() {
		return countryRepository.findPopular()
				.stream()
				.map(countryMapper::toDto)
				.collect(Collectors.toList());
	}
	
	List<CountryDto> findRadnom12Records() {
		return countryRepository.findRadnom12Records()
				.stream()
				.map(countryMapper::toDto)
				.collect(Collectors.toList());
	}
	
    byte[] getMainImageInByteFromCountry(Long countryId) throws IOException {
		File file = new File(findMainImagePathFromCountry(countryId));
		byte[] bytes = Files.readAllBytes(file.toPath());
    	return bytes;
    }
    
    private String findMainImagePathFromCountry(Long countryId) {
    	Optional<Country> country = countryRepository.findById(countryId);
    	List<CountryImage> countryImages = new ArrayList<>();

        if(country.isPresent()) {
        	countryImages = country.get().getCountryImage();
        	countryImages = countryImages.stream()
        			.filter(countryImg -> countryImg.isMainImage() == true)
        			.collect(Collectors.toList());
        } else
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Downloading object failed");
        
    	String imagePath = "";
    	
    	if (countryImages.size() > 1) {
    		throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "Too many main image downloaded");
    	} else if (countryImages.size() == 1) {
		List<CountryImage> mainImage = countryImages.stream()
			.filter(findMainImage -> findMainImage.isMainImage() == true)
			.collect(Collectors.toList());
			imagePath = mainImage.get(0).getImagePath();
		} else {
			/* Metods only for put example data */
			imagePath = countryImageService.getPathToRandomExampleImg();
		}
    	return imagePath;
    }
}
