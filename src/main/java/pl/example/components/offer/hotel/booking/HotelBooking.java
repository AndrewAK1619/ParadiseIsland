package pl.example.components.offer.hotel.booking;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import pl.example.components.offer.booking.OfferBooking;
import pl.example.components.offer.hotel.Hotel;
import pl.example.components.offer.hotel.room.Room;

@Entity
@Table(name = "hotel_booking")
public class HotelBooking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hotel_offer_id")
	private Long id;
	@ManyToOne
	@JoinColumn(name = "hotel_id")
	private Hotel hotel;
	@ManyToOne
	@JoinColumn(name = "room_id")
	private Room room;
	@Column(name = "start_booking_room")
	private LocalDateTime startBookingRoom;
	@Column(name = "end_booking_room")
	private LocalDateTime endBookingRoom;
	@Column(name = "booking_price")
	private float bookingPrice;
	@OneToMany(mappedBy = "hotelBooking", 
			cascade = { CascadeType.REMOVE })
	private List<OfferBooking> bookingOffers = new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
	public LocalDateTime getStartBookingRoom() {
		return startBookingRoom;
	}
	public void setStartBookingRoom(LocalDateTime startBookingRoom) {
		this.startBookingRoom = startBookingRoom;
	}
	public LocalDateTime getEndBookingRoom() {
		return endBookingRoom;
	}
	public void setEndBookingRoom(LocalDateTime endBookingRoom) {
		this.endBookingRoom = endBookingRoom;
	}
	public float getBookingPrice() {
		return bookingPrice;
	}
	public void setBookingPrice(float bookingPrice) {
		this.bookingPrice = bookingPrice;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(bookingPrice);
		result = prime * result + ((endBookingRoom == null) ? 0 : endBookingRoom.hashCode());
		result = prime * result + ((hotel == null) ? 0 : hotel.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((room == null) ? 0 : room.hashCode());
		result = prime * result + ((startBookingRoom == null) ? 0 : startBookingRoom.hashCode());
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
		HotelBooking other = (HotelBooking) obj;
		if (Float.floatToIntBits(bookingPrice) != Float.floatToIntBits(other.bookingPrice))
			return false;
		if (endBookingRoom == null) {
			if (other.endBookingRoom != null)
				return false;
		} else if (!endBookingRoom.equals(other.endBookingRoom))
			return false;
		if (hotel == null) {
			if (other.hotel != null)
				return false;
		} else if (!hotel.equals(other.hotel))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (room == null) {
			if (other.room != null)
				return false;
		} else if (!room.equals(other.room))
			return false;
		if (startBookingRoom == null) {
			if (other.startBookingRoom != null)
				return false;
		} else if (!startBookingRoom.equals(other.startBookingRoom))
			return false;
		return true;
	}
}
