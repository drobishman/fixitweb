package fixit.dao;

import java.util.List;

import fixit.model.*;

public interface CarTroubleCodeDao {
	
	CarTroubleCode findById (int id);
	
	void save (CarTroubleCode carTroubleCode);
	
	void deleteById(int id);

	List<CarTroubleCode> findAllCarTroubleCodes();

	List<CarTroubleCode> findByCarId(int carId);

}
