package fixit.dao;

import java.util.List;

import fixit.model.*;

public interface TroubleCodeDao {
  
    TroubleCode findById(int id);
     
    void save(TroubleCode troubleCode);
     
    void deleteById(int id);
     
    List<TroubleCode> findAllTroubleCodes();

}
