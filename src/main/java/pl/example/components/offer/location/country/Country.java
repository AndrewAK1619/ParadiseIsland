package pl.example.components.offer.location.country;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import pl.example.components.offer.location.country.image.CountryImage;
import pl.example.components.offer.location.country.information.CountryInformation;

@Entity
@Table(name = "countries")
public class Country implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "country_id", nullable = false)
	private Long id;
	@Column(name = "alpha2_code")
	private String alpha2Code;
	@NotNull
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "phonecode")
	private int phonecode;
	@Column(name = "popular_destination")
	private boolean popularDestination;
	@OneToMany
	@JoinColumn(name = "country_id", referencedColumnName = "country_id")
	private List<CountryImage> countryImage;
	@OneToMany
	@JoinColumn(name = "country_id", referencedColumnName = "country_id")
	private List<CountryInformation> countryInformation;
	
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
	public boolean isPopularDestination() {
		return popularDestination;
	}
	public void setPopularDestination(boolean popularDestination) {
		this.popularDestination = popularDestination;
	}
	public List<CountryImage> getCountryImage() {
		return countryImage;
	}
	public void setCountryImage(List<CountryImage> countryImage) {
		this.countryImage = countryImage;
	}
	public List<CountryInformation> getCountryInformation() {
		return countryInformation;
	}
	public void setCountryInformation(List<CountryInformation> countryInformation) {
		this.countryInformation = countryInformation;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alpha2Code == null) ? 0 : alpha2Code.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + phonecode;
		result = prime * result + (popularDestination ? 1231 : 1237);
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
		Country other = (Country) obj;
		if (alpha2Code == null) {
			if (other.alpha2Code != null)
				return false;
		} else if (!alpha2Code.equals(other.alpha2Code))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phonecode != other.phonecode)
			return false;
		if (popularDestination != other.popularDestination)
			return false;
		return true;
	}
}
