package fixit.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fixit.model.TroubleCode;

@Repository("troubleCodeDao")
public class TroubleCodeDaoImpl extends AbstractDao<Integer, TroubleCode> implements TroubleCodeDao{

	@Autowired
	private SessionFactory sessionFactory;

	private Session openSession() {
		return sessionFactory.getCurrentSession();
	}

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
	public void deleteById(int id) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
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


	@Override
	public TroubleCode findByNumber(String number) {
		Session session = this.sessionFactory.getCurrentSession();

		List<TroubleCode> troubleCodeList = new ArrayList<TroubleCode>();

		Query query = session.createQuery("select tc from TroubleCode tc where tc.number =:number");
		query.setParameter("number", number);

		troubleCodeList = query.list();

		if(troubleCodeList.isEmpty()) 
			return null;
		else
			return (TroubleCode) troubleCodeList.get(0);
	}

}
