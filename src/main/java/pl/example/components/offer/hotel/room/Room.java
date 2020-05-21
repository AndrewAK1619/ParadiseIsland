package pl.example.components.offer.hotel.room;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import pl.example.components.offer.hotel.Hotel;
import pl.example.components.offer.hotel.room.category.RoomCategory;
import pl.example.components.offer.hotel.room.image.RoomImage;

@Entity
@Table(name = "rooms")
public class Room implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	private Long id;
	@Column(name = "number_of_single_beds")
	private int numberOfSingleBeds;
	@Column(name = "number_of_double_beds")
	private int numberOfDoubleBeds;
	@Column(name = "room_price")
	private int roomPrice;
	@ManyToOne
	@JoinColumn(name = "room_category_id")
	private RoomCategory roomCategory;
	@OneToMany
	@JoinColumn(name = "room_id", referencedColumnName = "room_id")
	private List<RoomImage> roomImages;
	@ManyToOne
	@JoinColumn(name = "hotel_id")
	private Hotel hotel;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getNumberOfSingleBeds() {
		return numberOfSingleBeds;
	}
	public void setNumberOfSingleBeds(int numberOfSingleBeds) {
		this.numberOfSingleBeds = numberOfSingleBeds;
	}
	public int getNumberOfDoubleBeds() {
		return numberOfDoubleBeds;
	}
	public void setNumberOfDoubleBeds(int numberOfDoubleBeds) {
		this.numberOfDoubleBeds = numberOfDoubleBeds;
	}
	public int getRoomPrice() {
		return roomPrice;
	}
	public void setRoomPrice(int roomPrice) {
		this.roomPrice = roomPrice;
	}
	public RoomCategory getRoomCategory() {
		return roomCategory;
	}
	public void setRoomCategory(RoomCategory roomCategory) {
		this.roomCategory = roomCategory;
	}
	public List<RoomImage> getRoomImages() {
		return roomImages;
	}
	public void setRoomImages(List<RoomImage> roomImages) {
		this.roomImages = roomImages;
	}
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + numberOfDoubleBeds;
		result = prime * result + numberOfSingleBeds;
		result = prime * result + roomPrice;
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
		Room other = (Room) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (numberOfDoubleBeds != other.numberOfDoubleBeds)
			return false;
		if (numberOfSingleBeds != other.numberOfSingleBeds)
			return false;
		if (roomPrice != other.roomPrice)
			return false;
		return true;
	}
}
