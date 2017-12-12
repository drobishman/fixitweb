package fixit.dao;

import java.util.List;

import fixit.model.Car;

public class CarDaoImpl extends AbstractDao<Integer, Car> implements CarDao {
	
	static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	@Override
	public Car findById(int id) {
		Car car = getByKey(id);
        if(car!=null){
            Hibernate.initialize(car.getTroubleCodes);
        }
        return user;
	}

	@Override
	public void save(Car car) {
		persist(car);
	}

	@Override
	public void deleteById(Integer id) {
		Criteria crit = createEntityCriteria();
        	crit.add(Restrictions.eq("id", id));
       		Car car = (Car)crit.uniqueResult();
      		delete(car);
		
	}

	@Override
	public List<Car> findAllCars() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("registrationNumber"));
        	criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        	List<Car> cars = (List<Car>) criteria.list();
         
        // No need to fetch troubleCodes since we are not showing them on list page. Let them lazy load. 
        // Uncomment below lines for eagerly fetching of userProfiles if you want.
        /*
        for(Car car : cars){
            Hibernate.initialize(car.getTroubleCodes());
        }*/
        return users;
	}

}
