package pl.example.components.offer.hotel.advantages;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelAdvantageService {

	HotelAdvantageRepository advantageRepository;

	@Autowired
	public HotelAdvantageService(HotelAdvantageRepository advantageRepository) {
		this.advantageRepository = advantageRepository;
	}

	/* Metods only for put example data / Lorem Ipsum ect. */
	public List<HotelAdvantageDto> getDefaultLorepIpsumAdvantages() {
		return advantageRepository.findDefaultAdvantages()
				.stream()
				.map(HotelAdvantageMapper::toDto)
				.collect(Collectors.toList());
	}
}
