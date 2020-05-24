package pl.example.components.offer.booking;

import pl.example.components.offer.hotel.booking.HotelBooking;
import pl.example.components.offer.transport.airline.offer.AirlineOffer;
import pl.example.components.user.User;

public class OfferBookingDto {

	private Long id;
	private HotelBooking hotelBooking;
	private AirlineOffer airlineOffer;
	private User user;
	private float totalPrice;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public HotelBooking getHotelBooking() {
		return hotelBooking;
	}
	public void setHotelBooking(HotelBooking hotelBooking) {
		this.hotelBooking = hotelBooking;
	}
	public AirlineOffer getAirlineOffer() {
		return airlineOffer;
	}
	public void setAirlineOffer(AirlineOffer airlineOffer) {
		this.airlineOffer = airlineOffer;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
}
