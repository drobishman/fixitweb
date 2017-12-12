package fixit.dao;

import java.util.List;

public interface CarDao {

   Car findById(int id);
  
   void save(Car car);
  
   void deleteById(Integer id)
    
   List<Car> findAllCars();
}
