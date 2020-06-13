package pl.example.components.offer.booking;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import pl.example.components.offer.hotel.HotelService;
import pl.example.components.offer.hotel.room.RoomDto;

@RestController
@RequestMapping("/")
public class SearchController {

	private SearchService searchService;
	private HotelService hotelService;

	@Autowired
	public SearchController(SearchService searchService, 
			HotelService hotelService) {
		this.searchService = searchService;
		this.hotelService = hotelService;
	}

	@GetMapping("/search-result/page/{pageNumber}")
	public ResponseEntity<MultiValueMap<String, Object>> getAllSearchData(
			@CookieValue(value = "searchDataMap", required = false) String searchDataMapString,
			@PathVariable(required = false) Optional<Integer> pageNumber) {

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
		Page<HotelDto> hotelDtoList;

		if (pageNumber.isPresent()) {
			hotelDtoList = searchService.findBySearchData(searchDataMap, pageNumber.get() - 1);
		} else {
			hotelDtoList = searchService.findBySearchData(searchDataMap, 0);
		}
		List<byte[]> mainImgList = hotelService.getMainImgListInByteByHotelDtoList(hotelDtoList);
		List<RoomDto> minCostRoomList = searchService.getRoomWhereIsMinimalCost(searchDataMap, hotelDtoList);

		formData.add("hotelList", hotelDtoList);
		formData.add("fileList", mainImgList);
		formData.add("roomList", minCostRoomList);

		return ResponseEntity.ok(formData);
	}

	@GetMapping("/all/hotels")
	public List<SearchHotelDto> findAllSearchHotels() {
		return searchService.findAllSearchHotels();
	}

	@GetMapping("/all/countries")
	public List<String> findAllCountries() {
		return searchService.findAllCountriesNames();
	}

	@GetMapping("/all/regions")
	public List<String> findAllRegions() {
		return searchService.findAllRegionsNames();
	}

	@GetMapping("/all/cities")
	public List<String> findAllCities() {
		return searchService.findAllCitiesNames();
	}
}
