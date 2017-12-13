
package fixit.service;

import java.util.List;

import fixit.model.*;
 
 
public interface CarService {
     
    Car findById(int id);
     
    Car findBySSO(String sso);
     
    void saveCar(User user);
     
    void updateCar(User user);
     
    void deleteCarBySSO(String sso);
 
    List<Car> findAllCars(); 
 
}
