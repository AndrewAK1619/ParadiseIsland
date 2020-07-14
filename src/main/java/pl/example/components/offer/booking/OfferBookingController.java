package pl.example.components.offer.booking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account/profile")
public class OfferBookingController {

	private OfferBookingService offerBookingService;
	
	@Autowired
	public OfferBookingController(OfferBookingService offerBookingService) {
		this.offerBookingService = offerBookingService;
	}

	@GetMapping("/reservations")
	public List<OfferBookingBasicInfDto> findAllOfferBookingBasicInf(
			@RequestParam(required = false) String country) {

			return offerBookingService.findAllOfferBookingBasicInf();
	}
}
