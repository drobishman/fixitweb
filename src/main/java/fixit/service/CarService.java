
package fixit.service;

import java.util.List;

import fixit.model.*;
 
 
public interface CarService {
     
    Car findById(int id);
     
    void saveCar(Car car);
     
    void updateCar(Car car);
     
    void deleteCarById(Integer id);
 
    List<Car> findAllCars(); 
 
}
