package pl.example.components.offer.hotel.advantages;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HotelAdvantageService {

	HotelAdvantageRepository advantageRepository;
	HotelAdvantageMapper hotelAdvantageMapper;

	@Autowired
	public HotelAdvantageService(
			HotelAdvantageRepository advantageRepository,
			HotelAdvantageMapper hotelAdvantageMapper) {
		this.advantageRepository = advantageRepository;
		this.hotelAdvantageMapper = hotelAdvantageMapper;
	}

	List<HotelAdvantageDto> findAllByHotelId(Long hotelId) {
		return advantageRepository.findAllByHotelId(hotelId)
				.stream()
				.map(HotelAdvantageMapper::toDto)
				.collect(Collectors.toList());
	}

	Optional<HotelAdvantageDto> findById(Long id) {
		return advantageRepository.findById(id)
				.map(HotelAdvantageMapper::toDto);
	}

	HotelAdvantageDto save(HotelAdvantageDto hotelAdvantageDto) {
		return mapAndSaveHotelAdvantage(hotelAdvantageDto);
	}

	HotelAdvantageDto update(HotelAdvantageDto hotelAdvantageDto) {
		return mapAndSaveHotelAdvantage(hotelAdvantageDto);
	}

	private HotelAdvantageDto mapAndSaveHotelAdvantage(HotelAdvantageDto hotelAdvantageDto) {
		HotelAdvantage hotelAdvantageEntity = hotelAdvantageMapper.toEntity(hotelAdvantageDto);
		HotelAdvantage savedHotelAdvantage = advantageRepository.save(hotelAdvantageEntity);
		return HotelAdvantageMapper.toDto(savedHotelAdvantage);
	}

	void delete(Long id) {
		advantageRepository.deleteById(id);
	}

	/* Metods only for put example data / Lorem Ipsum ect. */
	public List<HotelAdvantageDto> getDefaultLorepIpsumAdvantages() {
		return advantageRepository.findDefaultAdvantages()
				.stream()
				.map(HotelAdvantageMapper::toDto)
				.collect(Collectors.toList());
	}
}
