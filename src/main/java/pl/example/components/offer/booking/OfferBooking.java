package pl.example.components.offer.booking;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import pl.example.components.offer.hotel.booking.HotelBooking;
import pl.example.components.offer.transport.airline.offer.AirlineOffer;
import pl.example.components.user.User;

@Entity
@Table(name = "offer_booking")
public class OfferBooking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "offer_booking_id")
	private Long id;
	@ManyToOne
	@JoinColumn(name = "hotel_booking_id")
	private HotelBooking hotelBooking;
	@ManyToOne
	@JoinColumn(name = "airline_offer_id")
	private AirlineOffer airlineOffer;
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	@Column(name = "total_price")
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((airlineOffer == null) ? 0 : airlineOffer.hashCode());
		result = prime * result + ((hotelBooking == null) ? 0 : hotelBooking.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + Float.floatToIntBits(totalPrice);
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		OfferBooking other = (OfferBooking) obj;
		if (airlineOffer == null) {
			if (other.airlineOffer != null)
				return false;
		} else if (!airlineOffer.equals(other.airlineOffer))
			return false;
		if (hotelBooking == null) {
			if (other.hotelBooking != null)
				return false;
		} else if (!hotelBooking.equals(other.hotelBooking))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Float.floatToIntBits(totalPrice) != Float.floatToIntBits(other.totalPrice))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
}
