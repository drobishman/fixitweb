package fixit.service;

import java.util.List;

import fixit.model.*;

public interface CarTroubleCodeService {
	
	List<CarTroubleCode> findByCarId (int carId);
	
	CarTroubleCode findById (int id);
	
	void saveCarTroubleCode (CarTroubleCode carTroubleCode);
	
	void deleteById(int id);
	
	void updateCarTroubleCode (CarTroubleCode carTroubleCode);
	
	List<CarTroubleCode> findAllCarTroubleCodes ();

}
