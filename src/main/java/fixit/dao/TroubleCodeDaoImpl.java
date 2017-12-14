package fixit.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fixit.model.TroubleCode;

@Repository("troubleCodeDao")
public class TroubleCodeDaoImpl extends AbstractDao<Integer, TroubleCode> implements TroubleCodeDao{

	@Override
	public TroubleCode findById(int id){
		TroubleCode troubleCode = getByKey(id);
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
        	List<TroubleCode> troubleCodes = (List<TroubleCode>) criteria.list();
        	return troubleCodes;
	}

}
