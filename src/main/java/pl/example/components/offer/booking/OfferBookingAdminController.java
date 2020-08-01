package pl.example.components.offer.booking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/user/reservations")
public class OfferBookingAdminController {

	private OfferBookingService offerBookingService;

	@Autowired
	public OfferBookingAdminController(OfferBookingService offerBookingService) {
		this.offerBookingService = offerBookingService;
	}

	@GetMapping("/{userId}")
	public List<OfferBookingBasicInfDto> findAllOfferBookingByUser(
			@PathVariable Long userId) {

		return offerBookingService.findAllOfferBookingByUser(userId);
	}
}
