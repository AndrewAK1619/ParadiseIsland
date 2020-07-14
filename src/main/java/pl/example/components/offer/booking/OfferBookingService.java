package pl.example.components.offer.booking;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import pl.example.components.user.User;
import pl.example.components.user.UserService;

@Service
public class OfferBookingService {

	private OfferBookingMapper offerBookingMapper;
	private OfferBookingRepository offerBookingRepository;
	private UserService userService;

	@Autowired
	public OfferBookingService(
			OfferBookingMapper offerBookingMapper, 
			OfferBookingRepository offerBookingRepository,
			UserService userService) {
		this.offerBookingMapper = offerBookingMapper;
		this.offerBookingRepository = offerBookingRepository;
		this.userService = userService;
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
	
	List<OfferBookingBasicInfDto> findAllOfferBookingBasicInf() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Optional<User> userOpt = userService.findUserByEmail(authentication.getName());
		
		List<OfferBooking> offerBookingList;
		if(userOpt.isPresent())
			offerBookingList = offerBookingRepository.findAllByUser(userOpt.get());
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
					"The user is not found");
		
		return offerBookingList
				.stream()
				.map(obl -> OfferBookingBasicInfMapper.toDto(obl))
				.collect(Collectors.toList());
	}
}
