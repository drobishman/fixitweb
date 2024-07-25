package fixit.model;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.NotEmpty;
/**
 * @author adrian
 *
 */


@Entity
@Table(name="car_trouble_code")
public class CarTroubleCode implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	
	@NotNull
	@Column(name="car_id", nullable=false, insertable = false, updatable = false)
	private int carId;

	
	@NotNull
	@Column(name="trouble_code_id", nullable=false, insertable = false, updatable = false)
	private Integer troubleCodeId;

	@NotEmpty
	@Column(name="job", nullable=false)
	private String job;
	
	@NotNull
	@Column(name="lon", nullable=false)
	private double lon;
	
	@NotNull
	@Column(name="lat", nullable=false)
	private double lat;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "car_id")
	private Car car;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "trouble_code_id")
	private TroubleCode troubleCode;

	public CarTroubleCode(int id, int carId, int troubleCodeId, Car car) {
		this.id = id;
		this.carId = carId;
		this.troubleCodeId = troubleCodeId;
		this.car = car;
	}

	public CarTroubleCode(){

	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getCarId() {
		return carId;
	}

	public void setCarID(int carId) {
		this.carId = carId;
	}

	public int getTroubleCodeId() {
		return troubleCodeId;
	}

	public void setTroubleCodeId(int troubleCodeId) {
		this.troubleCodeId = troubleCodeId;
	}
	
	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}
	
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
	
	public TroubleCode getTroubleCode() {
		return troubleCode;
	}

	public void setTroubleCode(TroubleCode troubleCode) {
		this.troubleCode = troubleCode;
	}
	
	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}
	
	@Override
	public String toString () {
		return "CarTroubleCode [id=" + id + ", carId=" + carId + ", troubleCodeId="+ troubleCodeId +", job=" + job + "]";
	}

}
