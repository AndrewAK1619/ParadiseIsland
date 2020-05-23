package pl.example.components.offer.transport.airline.offer;

import java.time.LocalDateTime;

public class AirlineOfferDto {

	private Long id;
	private Long arilineId;
	private LocalDateTime departure;
	private LocalDateTime returnTrip;
	private float flightPrice;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getArilineId() {
		return arilineId;
	}
	public void setArilineId(Long arilineId) {
		this.arilineId = arilineId;
	}
	public LocalDateTime getDeparture() {
		return departure;
	}
	public void setDeparture(LocalDateTime departure) {
		this.departure = departure;
	}
	public LocalDateTime getReturnTrip() {
		return returnTrip;
	}
	public void setReturnTrip(LocalDateTime returnTrip) {
		this.returnTrip = returnTrip;
	}
	public float getFlightPrice() {
		return flightPrice;
	}
	public void setFlightPrice(float flightPrice) {
		this.flightPrice = flightPrice;
	}
}
