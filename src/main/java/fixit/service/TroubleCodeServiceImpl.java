package fixit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fixit.dao.TroubleCodeDao;
import fixit.model.TroubleCode;
 
 
@Service("troubleCodeService")
@Transactional
public class TroubleCodeServiceImpl implements UserService{
 
    @Autowired
    private TroubleCodeDao dao;
     
    public TroubleCode findById(int id) {
        return dao.findById(id);
    }
 
    public void saveTroubleCode(TroubleCode troubleCode) {
        dao.save(troubleCode);
    }
 
    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends. 
     */
    public void updateTroubleCode(TroubleCode troubleCode) {
        TroubleCode entity = dao.findById(user.getId());
        if(entity!=null){
            entity.setNumber(user.getNumber());
            entity.setFaultLocation(user.getFaultLocation());
        }
    }
 
     
    public void deleteTroubleCodeById(int id) {
        dao.deleteById(id);
    }
 
    public List<TroubleCode> findAllTroubleCodes() {
        return dao.findAllTroubleCodes();
    }   
}
