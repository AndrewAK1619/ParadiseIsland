package pl.example.onlyForExampleData;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.example.components.offer.location.country.CountryDto;
import pl.example.components.offer.location.country.information.CountryInformationService;

/* Class only for put example data / Lorem Ipsum. */

@Service
public class destinationExampleDataService {

	@Autowired
	CountryInformationService countryInformationService;

	public void metodOnlyToSetDefaultInformationCountry(
			List<CountryDto> countryDtoList) {
		countryDtoList.stream()
			.map(countryDto -> {
				if (countryDto.getCountryInformationDto().size() != 0) {
					return countryDtoList;
				} else {
					countryDto.setCountryInformationDto(
							countryInformationService.getDefaultLorepIpsumInformation());
				}
				return countryDtoList;
			}).collect(Collectors.toList());
	}
}
