package pl.example.components.offer.location.country.information;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CountryInformationService {

	private CountryInformationRepository countryInformationRepository;
	
	@Autowired
	public CountryInformationService(CountryInformationRepository countryInformationRepository) {
		this.countryInformationRepository = countryInformationRepository;
	}
	
	/* Metods only for put example data / Lorem Ipsum ect. */
	public List<CountryInformationDto> getDefaultLorepIpsumInformation() {
		return countryInformationRepository.findDefaultInformation()
				.stream()
				.map(CountryInformationMapper::toDto)
				.collect(Collectors.toList());
	}
}
