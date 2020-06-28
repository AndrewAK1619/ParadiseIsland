package pl.example.components.offer.booking;

public class OfferBookingDto {

	private Long id;
	private Long hotelBookingId;
	private Long airlineOfferId;
	private String userEmail;
	private float totalPrice;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getHotelBookingId() {
		return hotelBookingId;
	}
	public void setHotelBookingId(Long hotelBookingId) {
		this.hotelBookingId = hotelBookingId;
	}
	public Long getAirlineOfferId() {
		return airlineOfferId;
	}
	public void setAirlineOfferId(Long airlineOfferId) {
		this.airlineOfferId = airlineOfferId;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
}
