package pl.example.components.offer.location.region;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/region")
public class RegionController {

	private RegionService regionService;

	@Autowired
	public RegionController(RegionService regionService) {
		this.regionService = regionService;
	}
	
    @GetMapping("/names")
    public List<String> findAllNames() {
        return regionService.findAllNames();
    }
}
