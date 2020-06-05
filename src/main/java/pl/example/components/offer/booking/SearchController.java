package pl.example.components.offer.booking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SearchController {

	private SearchService searchService;

	@Autowired
    public SearchController(SearchService searchService) {
		this.searchService = searchService;
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
