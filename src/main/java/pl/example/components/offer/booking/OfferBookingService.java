package pl.example.components.offer.booking;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;

import pl.example.components.offer.hotel.HotelDto;
import pl.example.components.offer.hotel.HotelService;
import pl.example.components.offer.hotel.room.RoomDto;
import pl.example.components.offer.hotel.room.RoomService;
import pl.example.components.offer.transport.airline.offer.AirlineOfferDto;
import pl.example.components.offer.transport.airline.offer.AirlineOfferService;
import pl.example.components.user.User;
import pl.example.components.user.UserService;

@Service
public class OfferBookingService {

	private OfferBookingMapper offerBookingMapper;
	private OfferBookingRepository offerBookingRepository;
	private UserService userService;
	private HotelService hotelService;
	private RoomService roomService;
	private AirlineOfferService airlineOfferService;

	@Autowired
	public OfferBookingService(
			OfferBookingMapper offerBookingMapper, 
			OfferBookingRepository offerBookingRepository,
			UserService userService, 
			HotelService hotelService,
			RoomService roomService, 
			AirlineOfferService airlineOfferService) {
		this.offerBookingMapper = offerBookingMapper;
		this.offerBookingRepository = offerBookingRepository;
		this.userService = userService;
		this.hotelService = hotelService;
		this.roomService = roomService;
		this.airlineOfferService = airlineOfferService;
	}

	public OfferBookingDto saveOfferBooking(
			Long hotelBookingId, 
			String airlineOfferIdStr, 
			int numberOfPersons,
			String totalPrice) {
			
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		OfferBookingDto dto = new OfferBookingDto();
		dto.setHotelBookingId(hotelBookingId);
		dto.setAirlineOfferId(Long.valueOf(airlineOfferIdStr));
		dto.setUserEmail(authentication.getName());
		dto.setNumberOfPersons(numberOfPersons);
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
	
	MultiValueMap<String, Object> findOfferBookingData(Long offerBookingId) throws IOException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Optional<User> userOpt = userService.findUserByEmail(authentication.getName());
		
		Optional<OfferBooking> offerBookingOpt;
		if(userOpt.isPresent())
			offerBookingOpt = offerBookingRepository.findByIdAndUser(offerBookingId, userOpt.get());
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
					"The user is not found");
		
		OfferBooking offerBooking;
		if(offerBookingOpt.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, 
					"Reservation is not found");
		else
			offerBooking = offerBookingOpt.get();

		return getOfferBookingDataByMultiValueMap(offerBooking);
	}

	private MultiValueMap<String, Object> getOfferBookingDataByMultiValueMap(OfferBooking offerBooking) 
			throws IOException {
		HotelDto hotelDto = hotelService.getHotelDtoByOfferBooking(offerBooking);
		byte[] mainImg = hotelService.getMainImageInByteFromHotel(hotelDto.getId());
		RoomDto roomDto = roomService.getRoomDtoByOfferBooking(offerBooking); 
		AirlineOfferDto airlineOfferDto = airlineOfferService.getAirlineDtoByOfferBooking(offerBooking);
		
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		formData.add("offerBooking", OfferBookingMapper.toDto(offerBooking));
		formData.add("hotel", hotelDto);
		formData.add("mainImg", mainImg);
		formData.add("room", roomDto);
		formData.add("airlineOffer", airlineOfferDto);
		
		return formData;
	}
}
