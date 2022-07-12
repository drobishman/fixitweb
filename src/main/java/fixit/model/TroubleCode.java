package fixit.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.validation.constraints.NotEmpty;

/**
*
*@author adrian
*/

@Entity
@Table(name="trouble_code")
public class TroubleCode implements Serializable{
  
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
   private Integer id; 
  
   @NotEmpty
   @Column(name="number", length=10, nullable=false)
   private String number;
  
   @NotEmpty
   @Column(name="fault_location", length=100, nullable=false)
   private String faultLocation;
  
  @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        return result;
    }
 
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof TroubleCode))
            return false;
        TroubleCode other = (TroubleCode) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (number == null) {
            if (other.number != null)
                return false;
        } else if (!number.equals(other.number))
            return false;
        return true;
    }
 
    @Override
    public String toString() {
        return "Trouble code [id=" + id + ", number=" + number + ", faultLocation="+ faultLocation +"]";
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getFaultLocation() {
		return faultLocation;
	}

	public void setFaultLocation(String faultLocation) {
		this.faultLocation = faultLocation;
	}

    
    
}
