package pl.example.components.offer.location.country.information;

public class CountryInformationMapper {

	public static CountryInformationDto toDto(CountryInformation countryInformation) {
		CountryInformationDto dto = new CountryInformationDto();
		dto.setId(countryInformation.getId());
		dto.setInformation(countryInformation.getInformation());
		return dto;
	}
	
	static CountryInformation toEntity(CountryInformationDto countryInformationDto) {
		CountryInformation entity = new CountryInformation();
		entity.setId(countryInformationDto.getId());
		entity.setInformation(countryInformationDto.getInformation());
		return entity;
	}
}
