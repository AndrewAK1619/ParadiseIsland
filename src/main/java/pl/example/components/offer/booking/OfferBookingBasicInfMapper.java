package pl.example.components.offer.booking;

public class OfferBookingBasicInfMapper {

	static OfferBookingBasicInfDto toDto(OfferBooking offerBooking) {
		OfferBookingBasicInfDto dto = new OfferBookingBasicInfDto();
		dto.setId(offerBooking.getId());
		dto.setDestination(generateStringDestination(offerBooking));
		dto.setDepartureDate(offerBooking.getHotelBooking().getStartBookingRoom());
		dto.setReturnDate(offerBooking.getHotelBooking().getEndBookingRoom());
		dto.setTotalPrice(offerBooking.getTotalPrice());
		return dto;
	}
	
	private static String generateStringDestination(OfferBooking offerBooking) {
		String countryName = offerBooking.getHotelBooking()
				.getHotel().getCountry().getName();
		String regionName = offerBooking.getHotelBooking()
				.getHotel().getRegion().getName();
		String cityName = offerBooking.getHotelBooking()
				.getHotel().getCity().getName();

		String destination = countryName + " / " + regionName + " / " + cityName;
		return destination;
	}
}
