package pl.example.components.offer.hotel.room.category;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RoomCategoryDto {

	private Long id;
	@NotBlank(message = "{offer.hotel.room.category.name.NotBlank}")
	@Size(max = 20, message = "{offer.hotel.room.category.name.Size}")
	private String name;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
