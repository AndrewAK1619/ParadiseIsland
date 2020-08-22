package pl.example.components.offer.location.country;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;

import pl.example.ParadiseIslandApplication;
import pl.example.components.offer.location.country.image.CountryImage;
import pl.example.components.offer.location.country.image.CountryImageService;

@Service
public class CountryService {

	CountryRepository countryRepository;
	CountryImageService countryImageService;
	
	@Autowired
	public CountryService(CountryRepository countryRepository,
			CountryImageService countryImageService) {
		this.countryRepository = countryRepository;
		this.countryImageService = countryImageService;
	}
	
	public List<String> findAllNames() {
        return countryRepository.findAll()
                .stream()
                .map(Country::getName)
                .collect(Collectors.toList());
    }
    
	public Optional<Country> findByName(String name) {
		return countryRepository.findByName(name);
	}
	
	List<CountryDto> findPopular() {
		return countryRepository.findPopular()
				.stream()
				.map(CountryMapper::toDto)
				.collect(Collectors.toList());
	}
	
	List<CountryDto> findRadnom12Records() {
		return countryRepository.findRadnom12Records()
				.stream()
				.map(CountryMapper::toDto)
				.collect(Collectors.toList());
	}
	
	public MultiValueMap<String, Object> getDestinationDetailsData(Long id) throws IOException {
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		CountryDto countryDto;
		Optional<Country> countryDtoOpt = countryRepository.findById(id);
		if(countryDtoOpt.isPresent())
			countryDto = CountryMapper.toDto(countryDtoOpt.get());
		else 
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
					"Destination details are not found");

		byte[] mainImg = getMainImageInByteFromCountry(countryDto.getId());
		formData.add("country", countryDto);
		formData.add("file", mainImg);
		return formData;
	}
	
    byte[] getMainImageInByteFromCountry(Long countryId) throws IOException {
		String mainImagePathByCountryId = findMainImagePathFromCountry(countryId);
		String partOfPathToCheckLocation = mainImagePathByCountryId.substring(1, 7);
		File file;		
		if("static".equals(partOfPathToCheckLocation)) {
			ClassPathResource classPathResource = new ClassPathResource(mainImagePathByCountryId);
			InputStream inputStream = classPathResource.getInputStream();
			file = File.createTempFile("test", ".jpg");
			FileUtils.copyInputStreamToFile(inputStream, file);
		} else {
			ApplicationHome home = new ApplicationHome(ParadiseIslandApplication.class);
			String homeDir = home.getDir().getPath();
			String fullPathToSlashReplace = homeDir + mainImagePathByCountryId;
			String fullPath = fullPathToSlashReplace.replace("\\", "/");
			file = new File(fullPath);
		}
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
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
        			"Downloading object failed");
        
    	String imagePath = "";
    	
    	if (countryImages.size() > 1) {
    		throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, 
    				"Too many main image downloaded");
    	} else if (countryImages.size() == 1) {
		List<CountryImage> mainImage = countryImages.stream()
			.filter(findMainImage -> findMainImage.isMainImage() == true)
			.collect(Collectors.toList());
			imagePath = mainImage.get(0).getImagePath();
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
					"Downloading object failed");
		}
    	return imagePath;
    }
}
