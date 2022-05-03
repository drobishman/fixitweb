package fixit.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;
/**
 * @author adrian
 *
 */
@Entity
@Table(name="car")
public class Car implements Serializable {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty
	@Column(name="registration_number", unique=true, nullable=false)
	private String registrationNumber;

	@NotEmpty
	@Column(name="chasis_number", nullable=false)
	private String chasisNumber;

	@NotEmpty
	@Column(name="brand", nullable=false)
	private String brand;

	@NotEmpty
	@Column(name="model", nullable=false)
	private String model;

	@OneToMany(mappedBy ="car", fetch = FetchType.EAGER)
	private Set<CarTroubleCode> carTroubleCodes = new HashSet<CarTroubleCode>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "car_trouble_code", 
	joinColumns = { @JoinColumn(name = "car_id") }, 
	inverseJoinColumns = { @JoinColumn(name = "trouble_code_id") })
	private Set<TroubleCode> troubleCodes = new HashSet<TroubleCode>();

	public Set<TroubleCode> getTroubleCodes() {
		return troubleCodes;
	}

	public void setTroubleCodes(Set<TroubleCode> troubleCodes) {
		this.troubleCodes = troubleCodes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((registrationNumber == null) ? 0 : registrationNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Car))
			return false;
		Car other = (Car) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (registrationNumber == null) {
			if (other.registrationNumber != null)
				return false;
		} else if (!registrationNumber.equals(other.registrationNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", registrationNumber=" + registrationNumber + ", chasisNumber="+ chasisNumber +", brand=" + brand +", model="+ model + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public String getChasisNumber() {
		return chasisNumber;
	}

	public void setChasisNumber(String chasisNumber) {
		this.chasisNumber = chasisNumber;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Set<CarTroubleCode> getCarTroubleCodes() {
		return carTroubleCodes;
	}

	public void setCarTroubleCodes(Set<CarTroubleCode> carTroubleCodes) {
		this.carTroubleCodes = carTroubleCodes;
	}

}
