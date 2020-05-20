package pl.example.components.offer.transport.airline.offer;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import pl.example.components.offer.transport.airline.Airline;

@Entity
@Table(name = "airline_offer")
public class AirlineOffer implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "airline_id")
	private Long id;
	@OneToOne
	private Airline ariline;
	private LocalDateTime departure;
	@Column(name = "return_trip")
	private LocalDateTime returnTrip;
	@Column(name = "flight_price")
	private float flightPrice;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Airline getAriline() {
		return ariline;
	}
	public void setAriline(Airline ariline) {
		this.ariline = ariline;
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ariline == null) ? 0 : ariline.hashCode());
		result = prime * result + ((departure == null) ? 0 : departure.hashCode());
		result = prime * result + Float.floatToIntBits(flightPrice);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((returnTrip == null) ? 0 : returnTrip.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AirlineOffer other = (AirlineOffer) obj;
		if (ariline == null) {
			if (other.ariline != null)
				return false;
		} else if (!ariline.equals(other.ariline))
			return false;
		if (departure == null) {
			if (other.departure != null)
				return false;
		} else if (!departure.equals(other.departure))
			return false;
		if (Float.floatToIntBits(flightPrice) != Float.floatToIntBits(other.flightPrice))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (returnTrip == null) {
			if (other.returnTrip != null)
				return false;
		} else if (!returnTrip.equals(other.returnTrip))
			return false;
		return true;
	}
}
