package pl.example.components.offer.booking;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.example.components.offer.hotel.HotelDto;
import pl.example.components.offer.hotel.room.RoomDto;
import pl.example.components.offer.transport.airline.offer.AirlineOffer;

@RestController
@RequestMapping("/search-result/details")
public class OfferBookingController {

	private OfferBookingService offerBookingService;
	private SearchService searchService;

	public OfferBookingController(OfferBookingService offerBookingService,
			SearchService searchService) {
		this.offerBookingService = offerBookingService;
		this.searchService = searchService;
	}

	@GetMapping("/{hotelId}")
	public ResponseEntity<MultiValueMap<String, Object>> getDetailsData(
			@CookieValue(value = "searchDataMap", required = false) String searchDataMapString,
			@PathVariable Long hotelId) throws IOException{
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> searchDataMap = new HashMap<>();

		if (searchDataMapString != null) {
			try {
				searchDataMap = mapper.readValue(
						searchDataMapString, new TypeReference<Map<String, String>>() {});
			} catch (IOException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, 
						"Search data not found");
			}
		}
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		HotelDto hotelDto = offerBookingService.getHotelById(hotelId);
		byte[] mainImg = offerBookingService.getMainImageInByteFromHotel(hotelDto.getId());
		RoomDto minCostRoomList = offerBookingService.getRoomWhereIsMinimalCost(searchDataMap, hotelDto);
		AirlineOffer airlineOffer = searchService.getAirlineOfferWhereIsMinimalCost(searchDataMap);
		
		formData.add("hotelList", hotelDto);
		formData.add("mainImg", mainImg);
		formData.add("room", minCostRoomList);
		formData.add("airlineOffer", airlineOffer);
		
		return ResponseEntity.ok(formData);
	}
}
