package fixit.dao;

import fixit.model.Car;
import fixit.model.CarTroubleCode;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("carTroubleCode")
public class CarTroubleCodeDaoImpl extends AbstractDao<Integer, CarTroubleCode> implements CarTroubleCodeDao {
	
	static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	@Override
	public CarTroubleCode findById(int id) {
		
		System.out.println("id = " + id + "CarTroubleCodeDao");
		
		CarTroubleCode carTroubleCode = getByKey(id);
		
		if(carTroubleCode!=null){
            Hibernate.initialize(carTroubleCode.getCar());
        }
		
		System.out.println(carTroubleCode.toString());
		
        return carTroubleCode;
	}

	@Override
	public void save(CarTroubleCode carTroubleCode) {
		
		persist(carTroubleCode);
		
	}
	
	@Override
	public void deleteById(int id) {
		Criteria crit = createEntityCriteria();
        	crit.add(Restrictions.eq("id", id));
       		CarTroubleCode carTroubleCode = (CarTroubleCode)crit.uniqueResult();
      		delete(carTroubleCode);
		
	}

	@Override
	public List<CarTroubleCode> findAllCarTroubleCodes() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("carId"));
    	criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
    	List<CarTroubleCode> cars = (List<CarTroubleCode>) criteria.list();
     
    // No need to fetch troubleCodes since we are not showing them on list page. Let them lazy load. 
    // Uncomment below lines for eagerly fetching of carTroubleCodes if you want.
    /*
    for(Car car : cars){
        Hibernate.initialize(car.getTroubleCodes());
    }*/
    return cars;
    
	}

	@Override
	public List<CarTroubleCode> findByCarId(int carId) {
		logger.info("carId : {}", carId);
        Criteria crit = createEntityCriteria();
        crit.add(Restrictions.eq("carId", carId));
        List<CarTroubleCode> carTroubleCodes = (List<CarTroubleCode>)crit.list();
        return carTroubleCodes;
	}
}
