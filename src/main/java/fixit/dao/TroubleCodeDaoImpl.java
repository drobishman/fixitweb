package fixit.dao;

import java.util.List;

import fixit.model.TroubleCode;
import fixit.model.User;

public class TroubleCodeDaoImpl extends AbstractDao<Integer, TroubleCode> implements TroubleCodeDao{

	@Override
	public TroubleCode findById(int id){
		TroubleCode troubleCode = getByKey(id);
        	return troubleCode;
	}

	@Override
	public User findByNumber(String number) {
		TroubleCode troubleCode = getByNumber(id);
        	return troubleCode;
	}

	@Override
	public void save(TroubleCode troubleCode) {
		persist(troubleCode);
		
	}

	@Override
	public void deleteByNumber(String number) {
		Criteria crit = createEntityCriteria();
        	crit.add(Restrictions.eq("number", number));
       		TroubleCode troubleCode = (TroubleCode)crit.uniqueResult();
      		delete(troubleCode);
		
	}

	@Override
	public List<TroubleCode> findAllTroubleCodes() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("number"));
        	criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
        	List<TroubleCode> troubleCodes = (List<TroubleCodes>) criteria.list();
        	return troubleCodes;
	}

}
