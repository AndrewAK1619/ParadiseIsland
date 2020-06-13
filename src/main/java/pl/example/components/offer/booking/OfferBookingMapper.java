package pl.example.components.offer.booking;

public class OfferBookingMapper {

	static OfferBookingDto toDto(OfferBooking offerBooking) {
		OfferBookingDto dto = new OfferBookingDto();
		dto.setId(offerBooking.getId());
		dto.setHotelBooking(offerBooking.getHotelBooking());
		dto.setAirlineOffer(offerBooking.getAirlineOffer());
		dto.setUser(offerBooking.getUser());
		dto.setTotalPrice(offerBooking.getTotalPrice());
		return dto;
	}

	static OfferBooking toEntity(OfferBookingDto offerBookingDto) {
		OfferBooking entity = new OfferBooking();
		entity.setId(offerBookingDto.getId());
		entity.setHotelBooking(offerBookingDto.getHotelBooking());
		entity.setAirlineOffer(offerBookingDto.getAirlineOffer());
		entity.setUser(offerBookingDto.getUser());
		entity.setTotalPrice(offerBookingDto.getTotalPrice());
		return entity;
	}
}
