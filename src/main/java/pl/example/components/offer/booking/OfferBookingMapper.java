package pl.example.components.offer.booking;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.example.components.offer.hotel.booking.HotelBooking;
import pl.example.components.offer.hotel.booking.HotelBookingRepository;
import pl.example.components.offer.transport.airline.offer.AirlineOffer;
import pl.example.components.offer.transport.airline.offer.AirlineOfferRepository;
import pl.example.components.user.User;
import pl.example.components.user.UserRepository;

@Service
public class OfferBookingMapper {

	private HotelBookingRepository hotelBookingRepository;
	private AirlineOfferRepository airlineOfferRepository;
	private UserRepository userRepository;
	
	@Autowired
	public OfferBookingMapper(
			HotelBookingRepository hotelBookingRepository,
			AirlineOfferRepository airlineOfferRepository,
			UserRepository userRepository) {
		this.hotelBookingRepository = hotelBookingRepository;
		this.airlineOfferRepository = airlineOfferRepository;
		this.userRepository = userRepository;
	}

	public static OfferBookingDto toDto(OfferBooking offerBooking) {
		OfferBookingDto dto = new OfferBookingDto();
		dto.setId(offerBooking.getId());
		dto.setHotelBookingId(offerBooking.getHotelBooking().getId());
		dto.setAirlineOfferId(offerBooking.getAirlineOffer().getId());
		dto.setUserEmail(offerBooking.getUser().getEmail());
		dto.setNumberOfPersons(offerBooking.getNumberOfPersons());
		dto.setTotalPrice(offerBooking.getTotalPrice());
		return dto;
	}

	public OfferBooking toEntity(OfferBookingDto offerBookingDto) {
		OfferBooking entity = new OfferBooking();
		entity.setId(offerBookingDto.getId());
		Optional<HotelBooking> hotelBooking = hotelBookingRepository
				.findById(offerBookingDto.getHotelBookingId());
		hotelBooking.ifPresent(entity::setHotelBooking);
		Optional<AirlineOffer> airlineOffer = airlineOfferRepository
				.findById(offerBookingDto.getAirlineOfferId());
		airlineOffer.ifPresent(entity::setAirlineOffer);
		Optional<User> user = userRepository
				.findByEmail(offerBookingDto.getUserEmail());
		user.ifPresent(entity::setUser);
		entity.setNumberOfPersons(offerBookingDto.getNumberOfPersons());
		entity.setTotalPrice(offerBookingDto.getTotalPrice());
		return entity;
	}
}
