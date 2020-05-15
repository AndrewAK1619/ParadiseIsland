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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import pl.example.components.offer.location.country.information.CountryInformationService;

@RestController
@RequestMapping("/destinations")
public class CountryController {

	private CountryService countryService;
	private CountryInformationService countryInformationService;
	
	@Autowired
	public CountryController(CountryService countryService,
			CountryInformationService countryInformationService) {
		this.countryService = countryService;
		this.countryInformationService = countryInformationService;
	}
	
	@GetMapping("/popular")
	public ResponseEntity<MultiValueMap<String, Object>> findPopular() {
		
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		List<CountryDto> countryDtoList = countryService.findPopular();
		
		metodOnlyToSetDefaultInformationCountry(countryDtoList);
		
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
		
		metodOnlyToSetDefaultInformationCountry(countryDtoList);
		
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
	
	/* Metods only for put example data / Lorem Ipsum. */
	
	private List<CountryDto> metodOnlyToSetDefaultInformationCountry(List<CountryDto> countryDtoList) {
		countryDtoList.stream()
			.map(countryDto -> {
				if(countryDto.getCountryInformationDto().size() != 0) {
					return countryDtoList;
				} else {
					countryDto.setCountryInformationDto(countryInformationService.getDefaultLorepIpsumInformation());
				}
				return countryDtoList;
			})
			.collect(Collectors.toList());
		
		return countryDtoList;
	}
}
