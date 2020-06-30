package pl.example.components.offer.booking;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.example.components.offer.hotel.HotelDto;
import pl.example.components.offer.hotel.booking.HotelBooking;
import pl.example.components.offer.hotel.booking.HotelBookingService;
import pl.example.components.offer.hotel.room.RoomDto;
import pl.example.components.offer.transport.airline.offer.AirlineOfferDto;

@RestController
@RequestMapping("/search-result/details/{hotelId}")
public class OfferBookingController {

	private OfferBookingService offerBookingService;
	private HotelBookingService hotelBookingService;

	@Autowired
	public OfferBookingController(
			OfferBookingService offerBookingService,
			HotelBookingService hotelBookingService) {
		this.offerBookingService = offerBookingService;
		this.hotelBookingService = hotelBookingService;
	}

	@GetMapping("")
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
		RoomDto minCostRoom = offerBookingService.getRoomWhereIsMinimalCost(searchDataMap, hotelDto);
		AirlineOfferDto airlineOffer = offerBookingService.getAirlineOfferWhereIsMinimalCost(searchDataMap);
		
		formData.add("hotelList", hotelDto);
		formData.add("mainImg", mainImg);
		formData.add("room", minCostRoom);
		formData.add("airlineOffer", airlineOffer);
		
		return ResponseEntity.ok(formData);
	}
	
	@GetMapping("/rooms")
	public ResponseEntity<MultiValueMap<String, Object>> getRoomsData(
			@CookieValue(value = "searchDataMap", required = false) String searchDataMapString,
			@PathVariable Long hotelId,
			@RequestParam(name = "roomCategoryName", required = false) String roomCategoryName) throws IOException{
		
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
		
		List<RoomDto> availableRooms;
		if(roomCategoryName != null) {
			availableRooms = offerBookingService.getAllAvailableRooms(searchDataMap, hotelDto, roomCategoryName);
		} else
			availableRooms = offerBookingService.getAllAvailableRooms(searchDataMap, hotelDto);
		
		List<byte[]> mainImgList = offerBookingService.getMainImgListInByteByRoomDtoList(availableRooms);
		
		formData.add("roomList", availableRooms);
		formData.add("fileList", mainImgList);
		
		return ResponseEntity.ok(formData);
	}
	
	@GetMapping("/airlines")
	public ResponseEntity<List<AirlineOfferDto>> getAirlinesOfferByDate(
			@CookieValue(value = "searchDataMap", required = false) String searchDataMapString,
			@RequestParam(required = false) String airlineName){
			
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
		List<AirlineOfferDto> airlineOffersList;
		if(airlineName != null)
			airlineOffersList = offerBookingService
				.getAllAirlineOfferByDateAndAirlineName(searchDataMap, airlineName);
		else
			airlineOffersList = offerBookingService
					.getAllAirlineOfferByDate(searchDataMap);
		
		return ResponseEntity.ok(airlineOffersList);
	}
	
	@PostMapping("/bookTrip")
	public ResponseEntity<?> bookTripOffer(
			@PathVariable String hotelId,
			@RequestPart("roomId") String roomId,
			@RequestPart("hotelTotalPrice") String hotelTotalPrice,
			@RequestPart("departure") String departure,
			@RequestPart("returnDate") String returnDate,
			@RequestPart("airlineOfferId") String airlineOfferId,
			@RequestPart("totalPrice") String totalPrice) {
	
		HotelBooking hotelBookingEntity = hotelBookingService.saveHotelBooking(
				hotelId,
				roomId,
				hotelTotalPrice,
				departure,
				returnDate);
				
		OfferBookingDto offerBookingDto = offerBookingService.saveOfferBooking(
				hotelBookingEntity.getId(),
				airlineOfferId, 
				totalPrice);
		
		return ResponseEntity.ok(offerBookingDto);
	}
}
