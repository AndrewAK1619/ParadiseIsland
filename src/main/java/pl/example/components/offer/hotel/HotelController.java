package pl.example.components.offer.hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotels")
public class HotelController {
	
	HotelService hotelService;
	
	@Autowired
	public HotelController(HotelService hotelService) {
		this.hotelService = hotelService;
	}
}
