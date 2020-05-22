package pl.example.components.offer.hotel;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import pl.example.components.offer.hotel.advantages.HotelAdvantage;
import pl.example.components.offer.hotel.image.HotelImage;
import pl.example.components.offer.hotel.room.Room;
import pl.example.components.offer.location.city.City;
import pl.example.components.offer.location.country.Country;
import pl.example.components.offer.location.region.Region;

@Entity
@Table(name = "hotels")
public class Hotel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hotel_id")
	private Long id;
	@Column(name = "hotel_name")
	private String hotelName;
	private String description;
	@OneToMany(mappedBy = "hotel")
	private Set<Room> room = new HashSet<>();
	@OneToMany
	@JoinColumn(name = "hotel_id", referencedColumnName = "hotel_id")
	private List<HotelImage> hotelImages;
	@OneToMany
	@JoinColumn(name = "hotel_id", referencedColumnName = "hotel_id")
	private List<HotelAdvantage> hotelAdvantages;
	@ManyToOne
	@JoinColumn(name = "country_id")
	private Country country;
	@ManyToOne
	@JoinColumn(name = "region_id")
	private Region region;
	@ManyToOne
	@JoinColumn(name = "city_id")
	private City city;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<Room> getRoom() {
		return room;
	}
	public void setRoom(Set<Room> room) {
		this.room = room;
	}
	public List<HotelImage> getHotelImages() {
		return hotelImages;
	}
	public void setHotelImages(List<HotelImage> hotelImages) {
		this.hotelImages = hotelImages;
	}
	public List<HotelAdvantage> getHotelAdvantages() {
		return hotelAdvantages;
	}
	public void setHotelAdvantages(List<HotelAdvantage> hotelAdvantages) {
		this.hotelAdvantages = hotelAdvantages;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {
		this.region = region;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((hotelName == null) ? 0 : hotelName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Hotel other = (Hotel) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (hotelName == null) {
			if (other.hotelName != null)
				return false;
		} else if (!hotelName.equals(other.hotelName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
