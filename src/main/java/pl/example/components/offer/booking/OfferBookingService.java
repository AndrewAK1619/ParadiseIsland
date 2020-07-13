package pl.example.components.offer.booking;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class OfferBookingService {

	private OfferBookingMapper offerBookingMapper;
	private OfferBookingRepository offerBookingRepository;

	public OfferBookingService(
			OfferBookingMapper offerBookingMapper, 
			OfferBookingRepository offerBookingRepository) {
		this.offerBookingMapper = offerBookingMapper;
		this.offerBookingRepository = offerBookingRepository;
	}

	public OfferBookingDto saveOfferBooking(
			Long hotelBookingId, 
			String airlineOfferIdStr, 
			String totalPrice) {
			
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		OfferBookingDto dto = new OfferBookingDto();
		dto.setHotelBookingId(hotelBookingId);
		dto.setAirlineOfferId(Long.valueOf(airlineOfferIdStr));
		dto.setUserEmail(authentication.getName());
		dto.setTotalPrice(Float.valueOf(totalPrice));
		OfferBooking offerBookingEnity = offerBookingRepository
				.save(offerBookingMapper.toEntity(dto));
		
		return OfferBookingMapper.toDto(offerBookingEnity);
	}
}
