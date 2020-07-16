package pl.example.components.offer.booking;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("/reservations/{offerBookingId}")
	public ResponseEntity<MultiValueMap<String, Object>> getDetailsData(
			@PathVariable Long offerBookingId) throws IOException {

		return ResponseEntity.ok(offerBookingService.findOfferBookingData(offerBookingId));
	}
}
