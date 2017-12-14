package fixit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fixit.dao.CarDao;
import fixit.model.Car;
import fixit.model.TroubleCode;
 
 
@Service("carService")
@Transactional
public class CarServiceImpl implements CarService{

    @Autowired
    private CarDao dao;

    public Car findById(int id){
        return dao.findById(id);
    }
     
    public void saveCar(Car car){
        dao.save(car);
    }
     
     
     /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends. 
     */
    public void updateCar(Car car){
       Car entity = dao.findById(car.getId());
        if(entity!=null){
            entity.setRegistrationNumber(car.getRegistrationNumber());
            entity.setChasisNumber(car.getChasisNumber());
            entity.setBrand(car.getBrand());
            entity.setModel(car.getModel());
        }
    }
     
    public void deleteCarById(Integer id){
        dao.deleteById(id);
    }

	@Override
	public List<Car> findAllCars() {
		return dao.findAllCars();
	}

}
