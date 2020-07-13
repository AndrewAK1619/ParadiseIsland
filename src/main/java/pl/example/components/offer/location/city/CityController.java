package pl.example.components.offer.location.city;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cities")
public class CityController {

	private CityService cityService;

	@Autowired
	public CityController(CityService cityService) {
		this.cityService = cityService;
	}
	
    @GetMapping("/names")
    public List<String> findAllNames() {
        return cityService.findAllNames();
    }

    @GetMapping("/names/country")
    public List<String> findAllNamesByCountry(
    		@RequestParam(name = "country") String country) {

		return cityService.findAllNamesByCountry(country);
    }
}
