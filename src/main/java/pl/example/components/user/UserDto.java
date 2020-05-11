package pl.example.components.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDto {

	private Long id;
	@NotBlank(message="{pl.example.components.user.firstName.NotBlank}")
	@Size(max = 20, message="{pl.example.components.user.firstName.Size}")
	private String firstName;
	@NotBlank(message="{pl.example.components.user.lastName.NotBlank}")
	@Size(max = 20, message="{pl.example.components.user.lastName.Size}")
	private String lastName;
	@NotBlank(message="{pl.example.components.user.mobilePhone.NotBlank}")
	@Size(max = 20, message="{pl.example.components.user.mobilePhone.Size}")
	@Pattern(regexp = "(?!\\w<)(\\(?(\\+|00)?48\\)?)?[ -]?\\d{3}[ -]?\\d{3}[ -]?\\d{3}(?!\\w)",
			message="{pl.example.components.user.mobilePhone.Pattern}")
	private String mobilePhone;
	@NotBlank(message="{pl.example.components.user.email.NotBlank}")
	@Email(message="{pl.example.components.user.email.Email}")
	private String email;
	@NotBlank(message="{pl.example.components.user.password.NotBlank}")
	@Size(min = 8, message="{pl.example.components.user.password.Size}")
	private String password;
	@Size(max = 150, message="{pl.example.components.user.details.Size}")
	private String details;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
}
