package fixit.dao;

import java.util.List;

import fixit.model.*;

public interface CarDao {

   Car findById(int id);
   
   Car findByChasisNumber(String chasisNumber);
  
   void save(Car car);
  
   void deleteById(Integer id);
    
   List<Car> findAllCars();
}
