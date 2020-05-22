package pl.example.components.offer.hotel;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import pl.example.components.offer.hotel.advantages.HotelAdvantageDto;

public class HotelDto {

	private Long id;
	@NotBlank(message = "{offer.hotel.hotelName.NotBlank}")
	@Size(max = 30, message = "{offer.hotel.hotelName.Size}")
	private String hotelName;
	@Size(max = 150, message = "{offer.hotel.description.Size}")
	private String description;
	private Long mainImageId;
	private List<HotelAdvantageDto> hotelAdvantageDto;
	@NotBlank(message = "{offer.hotel.country.NotBlank}")
	private String country;
	@NotBlank(message = "{offer.hotel.region.NotBlank}")
	private String region;
	@NotBlank(message = "{offer.hotel.city.NotBlank}")
	private String city;

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
	public Long getMainImageId() {
		return mainImageId;
	}
	public void setMainImageId(Long mainImageId) {
		this.mainImageId = mainImageId;
	}
	public List<HotelAdvantageDto> getHotelAdvantageDto() {
		return hotelAdvantageDto;
	}
	public void setHotelAdvantageDto(List<HotelAdvantageDto> hotelAdvantageDto) {
		this.hotelAdvantageDto = hotelAdvantageDto;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
