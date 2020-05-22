package pl.example.onlyForExampleData;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.example.components.offer.hotel.HotelDto;
import pl.example.components.offer.hotel.advantages.HotelAdvantageService;

/* Class only for put example data / Lorem Ipsum. */

@Service
public class hotelExampleDataService {

	@Autowired
	private HotelAdvantageService hotelAdvantageService;

	public void metodOnlyToSetDefaultHotelAdvantage(
			List<HotelDto> hotelDtoList) {
		hotelDtoList.stream()
			.map(hotelDto -> {
				if (hotelDto.getHotelAdvantageDto().size() != 0) {
					return hotelDtoList;
				} else {
					hotelDto.setHotelAdvantageDto(
							hotelAdvantageService.getDefaultLorepIpsumAdvantages());
				}
				return hotelDtoList;
			}).collect(Collectors.toList());
	}
}
