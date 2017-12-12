package fixit.dao;

import java.util.List;

public interface TroubleCodeDao {
  
    TroubleCode findById(int id);
     
    User findByNumber(String number);
     
    void save(TroubleCode);
     
    void deleteByNumber(String number);
     
    List<TroubleCode> findAllTroubleCodes();

}
