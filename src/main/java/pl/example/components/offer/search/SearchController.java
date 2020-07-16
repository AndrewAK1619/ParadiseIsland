package pl.example.components.offer.search;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import pl.example.components.offer.booking.OfferBookingDto;
import pl.example.components.offer.hotel.HotelDto;
import pl.example.components.offer.hotel.HotelSearchDto;
import pl.example.components.offer.hotel.booking.HotelBooking;
import pl.example.components.offer.hotel.booking.HotelBookingService;
import pl.example.components.offer.hotel.room.RoomDto;
import pl.example.components.offer.transport.airline.offer.AirlineOfferDto;

@RestController
@RequestMapping("/search-result")
public class SearchController {

	private SearchService searchService;
	private HotelBookingService hotelBookingService;

	@Autowired
	public SearchController(SearchService searchService, 
			HotelBookingService hotelBookingService) {
		this.searchService = searchService;
		this.hotelBookingService = hotelBookingService;
	}

	@GetMapping("/page/{pageNumber}")
	public ResponseEntity<MultiValueMap<String, Object>> getAllSearchData(
			@CookieValue(value = "searchDataMap", required = false) String searchDataMapString,
			@PathVariable(required = false) Optional<Integer> pageNumber) {

		Map<String, String> searchDataMap = searchService.convertStringToStringMap(searchDataMapString);
		Page<HotelDto> hotelDtoList;
		if (pageNumber.isPresent()) {
			hotelDtoList = searchService.findBySearchData(searchDataMap, pageNumber.get() - 1);
		} else {
			hotelDtoList = searchService.findBySearchData(searchDataMap, 0);
		}
		List<byte[]> mainImgList = searchService.getMainImgListInByteByHotelDtoList(hotelDtoList);
		List<RoomDto> minCostRoomList = searchService
				.getAllRoomWhereIsMinimalCostBySearchData(searchDataMap,hotelDtoList);
		AirlineOfferDto airlineOffer = searchService
				.getAirlineOfferByDateWhereIsMinimalCost(searchDataMap);

		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		formData.add("hotelList", hotelDtoList);
		formData.add("fileList", mainImgList);
		formData.add("roomList", minCostRoomList);
		formData.add("airlineOffer", airlineOffer);

		return ResponseEntity.ok(formData);
	}

	@GetMapping("/details/{hotelId}")
	public ResponseEntity<MultiValueMap<String, Object>> getDetailsData(
			@CookieValue(value = "searchDataMap", required = false) String searchDataMapString,
			@PathVariable Long hotelId) throws IOException {

		Map<String, String> searchDataMap = searchService.convertStringToStringMap(searchDataMapString);
		HotelDto hotelDto = searchService.getHotelById(hotelId);
		byte[] mainImg = searchService.getMainImageInByteFromHotel(hotelDto.getId());
		RoomDto minCostRoom = searchService
				.getRoomWhereIsMinimalCost(searchDataMap, hotelDto);
		AirlineOfferDto airlineOffer = searchService
				.getAirlineOfferByDateWhereIsMinimalCost(searchDataMap);

		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		formData.add("hotel", hotelDto);
		formData.add("mainImg", mainImg);
		formData.add("room", minCostRoom);
		formData.add("airlineOffer", airlineOffer);

		return ResponseEntity.ok(formData);
	}

	@GetMapping("/details/{hotelId}/rooms")
	public ResponseEntity<MultiValueMap<String, Object>> getRoomsData(
			@CookieValue(value = "searchDataMap", required = false) String searchDataMapString,
			@PathVariable Long hotelId,
			@RequestParam(name = "roomCategoryName", required = false) String roomCategoryName) 
			throws IOException {

		Map<String, String> searchDataMap = searchService.convertStringToStringMap(searchDataMapString);
		HotelDto hotelDto = searchService.getHotelById(hotelId);

		List<RoomDto> availableRooms;
		if (roomCategoryName != null) {
			availableRooms = searchService.getAllAvailableRooms(searchDataMap, hotelDto, roomCategoryName);
		} else {
			availableRooms = searchService.getAllAvailableRooms(searchDataMap, hotelDto);
		}
		List<byte[]> mainImgList = searchService.getMainImgListInByteByRoomDtoList(availableRooms);

		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		formData.add("roomList", availableRooms);
		formData.add("fileList", mainImgList);

		return ResponseEntity.ok(formData);
	}

	@GetMapping("/details/{hotelId}/airlines")
	public ResponseEntity<List<AirlineOfferDto>> getAirlinesOfferByDate(
			@CookieValue(value = "searchDataMap", required = false) String searchDataMapString,
			@RequestParam(required = false) String airlineName) {

		Map<String, String> searchDataMap = searchService.convertStringToStringMap(searchDataMapString);
		List<AirlineOfferDto> airlineOffersList;
		if (airlineName != null)
			airlineOffersList = searchService
				.getAllAirlineOfferByDateAndAirlineName(searchDataMap, airlineName);
		else
			airlineOffersList = searchService
				.getAllAirlineOfferByDate(searchDataMap);

		return ResponseEntity.ok(airlineOffersList);
	}

	@PostMapping("/details/{hotelId}/bookTrip")
	public ResponseEntity<?> bookTripOffer(
			@PathVariable String hotelId, 
			@RequestPart("roomId") String roomId,
			@RequestPart("hotelTotalPrice") String hotelTotalPrice, 
			@RequestPart("departure") String departure,
			@RequestPart("returnDate") String returnDate, 
			@RequestPart("airlineOfferId") String airlineOfferId,
			@RequestPart("numberOfPersons") String numberOfPersons,
			@RequestPart("totalPrice") String totalPrice) {
		
		HotelBooking hotelBookingEntity = hotelBookingService
				.saveHotelBooking(hotelId, roomId, hotelTotalPrice, departure, returnDate);

		OfferBookingDto offerBookingDto = searchService
				.saveOfferBooking(hotelBookingEntity.getId(), airlineOfferId, 
				Integer.parseInt(numberOfPersons), totalPrice);

		return ResponseEntity.ok(offerBookingDto);
	}

	@GetMapping("/all/hotels")
	public List<HotelSearchDto> findAllSearchHotels() {
		return searchService.findAllHotelsSearch();
	}
}
