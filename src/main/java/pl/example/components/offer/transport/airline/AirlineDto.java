package pl.example.components.offer.transport.airline;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AirlineDto {

	private Long id;
	@NotBlank(message="{offer.transport.airline.airlineName.NotBlank}")
	@Size(max = 30, message="{offer.transport.airline.airlineName.Size}")
	private String airlineName;
	@Size(max = 150, message="{offer.transport.airline.details.Size}")
	private String details;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAirlineName() {
		return airlineName;
	}
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
}