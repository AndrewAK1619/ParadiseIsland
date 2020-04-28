package pl.example.components.offer.location.country;

import java.util.List;

import pl.example.components.offer.location.country.information.CountryInformationDto;

public class CountryDto {
	
	private Long id;
	private String alpha2Code;
	private String name;
	private int phonecode;
	private Long mainImageId;
	private List<CountryInformationDto> countryInformationDto;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAlpha2Code() {
		return alpha2Code;
	}
	public void setAlpha2Code(String alpha2Code) {
		this.alpha2Code = alpha2Code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPhonecode() {
		return phonecode;
	}
	public void setPhonecode(int phonecode) {
		this.phonecode = phonecode;
	}
	public Long getMainImageId() {
		return mainImageId;
	}
	public void setMainImageId(Long mainImageId) {
		this.mainImageId = mainImageId;
	}
	public List<CountryInformationDto> getCountryInformationDto() {
		return countryInformationDto;
	}
	public void setCountryInformationDto(List<CountryInformationDto> countryInformationDto) {
		this.countryInformationDto = countryInformationDto;
	}
}
