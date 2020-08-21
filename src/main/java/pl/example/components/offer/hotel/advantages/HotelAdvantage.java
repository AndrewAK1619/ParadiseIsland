package pl.example.components.offer.hotel.advantages;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import pl.example.components.offer.hotel.Hotel;

@Entity
@Table(name = "hotel_advantages")
public class HotelAdvantage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hotel_advantage_id", nullable = false)
	private Long id;
	@NotNull
	@Column(name = "description_advantage", nullable = false)
	private String descriptionAdvantage;
	@ManyToOne
	@JoinColumn(name = "hotel_id")
	private Hotel hotel;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescriptionAdvantage() {
		return descriptionAdvantage;
	}
	public void setDescriptionAdvantage(String descriptionAdvantage) {
		this.descriptionAdvantage = descriptionAdvantage;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descriptionAdvantage == null) ? 0 : descriptionAdvantage.hashCode());
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
		HotelAdvantage other = (HotelAdvantage) obj;
		if (descriptionAdvantage == null) {
			if (other.descriptionAdvantage != null)
				return false;
		} else if (!descriptionAdvantage.equals(other.descriptionAdvantage))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
