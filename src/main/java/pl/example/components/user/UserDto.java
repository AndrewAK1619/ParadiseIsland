package pl.example.components.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDto {

	private Long id;
	@NotBlank(message="{pl.example.components.user.firstName.NotBlank}")
	@Size(max = 30, message="{pl.example.components.user.firstName.Size}")
	private String firstName;
	@NotBlank(message="{pl.example.components.user.lastName.NotBlank}")
	@Size(max = 30, message="{pl.example.components.user.lastName.Size}")
	private String lastName;
	@NotBlank(message="{pl.example.components.user.mobilePhone.NotBlank}")
	@Size(max = 15, message="{pl.example.components.user.mobilePhone.Size}")
	private String mobilePhone;
	@NotBlank(message="{pl.example.components.user.email.NotBlank}")
	@Email(message="{pl.example.components.user.email.Email}")
	private String email;
	private String pesel;
	@NotBlank(message="{pl.example.components.user.password.NotBlank}")
	@Size(min = 6, message="{pl.example.components.user.password.Size}")
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
	public String getPesel() {
		return pesel;
	}
	public void setPesel(String pesel) {
		this.pesel = pesel;
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
