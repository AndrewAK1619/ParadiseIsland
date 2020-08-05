package pl.example.components.offer.location.country;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/destinations")
public class DestinationsController {

	private CountryService countryService;
	
	@Autowired
	public DestinationsController(CountryService countryService) {
		this.countryService = countryService;
	}

	@GetMapping("/popular")
	public ResponseEntity<MultiValueMap<String, Object>> findPopular() {
		
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		List<CountryDto> countryDtoList = countryService.findPopular();
		
		List<byte[]> mainImgList = countryDtoList.stream()
				.map(countryDto -> {
					try {
						return countryService.getMainImageInByteFromCountry(countryDto.getId());
					} catch (IOException e) {
						throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
								"Downloading object failed");
					}
				})
				.collect(Collectors.toList());
		
		formData.add("countryList", countryDtoList);
		formData.add("fileList", mainImgList);
		
		return ResponseEntity.ok(formData);
	}
	
	@GetMapping("/random")
	public ResponseEntity<MultiValueMap<String, Object>> findRadnom() {
		
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		List<CountryDto> countryDtoList = countryService.findRadnom12Records();

		List<byte[]> mainImgList = countryDtoList.stream()
				.map(countryDto -> {
					try {
						return countryService.getMainImageInByteFromCountry(countryDto.getId());
					} catch (IOException e) {
						throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
								"Downloading object failed");
					}
				})
				.collect(Collectors.toList());
		
		formData.add("countryList", countryDtoList);
		formData.add("fileList", mainImgList);
		
		return ResponseEntity.ok(formData);
	}

	@GetMapping("/details/{id}")
	public ResponseEntity<MultiValueMap<String, Object>> findDestinationDetails(
			@PathVariable Long id) throws IOException {
			
		MultiValueMap<String, Object> formData = countryService.getDestinationDetailsData(id);
		return ResponseEntity.ok(formData);
	}
}
