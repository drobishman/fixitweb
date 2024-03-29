
package fixit.service;

import java.util.List;

import fixit.model.TroubleCode;
 
 
public interface TroubleCodeService {
     
    TroubleCode findById(int id);
    
    TroubleCode findByNumber(String number);
     
    void saveTroubleCode(TroubleCode troubleCode);
     
    void updateTroubleCode(TroubleCode troubleCode);
     
    void deleteTroubleCodeById(int id);
 
    List<TroubleCode> findAllTroubleCodes(); 
 
}
