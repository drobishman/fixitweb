package fixit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fixit.dao.CarTroubleCodeDao;
import fixit.model.CarTroubleCode;

@Service("carTroubleCodeService")
@Transactional
public class CarTroubleCodeServiceImpl implements CarTroubleCodeService {

	@Autowired
	private CarTroubleCodeDao dao;

	public void updateCarTroubleCode(CarTroubleCode carTroubleCode){
		CarTroubleCode entity = dao.findById(carTroubleCode.getId());
		if(entity!=null){
			entity.setTroubleCodeId(carTroubleCode.getTroubleCodeId());
			entity.setCarID(carTroubleCode.getCarId());
			entity.setJob(carTroubleCode.getJob());
		}
	}
	
	 public void deleteById(int id){
	        dao.deleteById(id);
	    }

	@Override
	public CarTroubleCode findById(int id) {
		
		System.out.println("id = " + id + "CarTroubleCodeService");
		
		return dao.findById(id);
	}

	@Override
	public void saveCarTroubleCode(CarTroubleCode carTroubleCode) {
		dao.save(carTroubleCode);
		
	}

	@Override
	public List<CarTroubleCode> findAllCarTroubleCodes() {
		return dao.findAllCarTroubleCodes();
	}
}
