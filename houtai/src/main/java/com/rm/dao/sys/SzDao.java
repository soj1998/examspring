package com.rm.dao.sys;





import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rm.entity.lieju.Sz;

/**
 * 图书Dao接口
 * @author user
 *
 */
public interface SzDao extends JpaRepository<Sz, Integer>{
	@Query(value = "select * from t_sz where szmc = :szmc limit 0,1",nativeQuery = true)
    public Sz findSzBymc(@Param("szmc") String mc);	
	
/**
	@Query(value = "from GuanLianXing where userId = :userId")
    public List<Book> findGuanLianXingByUserId(@Param("userId") int useId);
 
 @Query(value = "update GuanLianXing set userId = :userId where id = :id")
 @Modifying
 public Integer updateUserIdById(@Param("id") int id,@Param("userId") int userId);

 @Query(value = "select * from t_guanlianxing where user_id = :userId",nativeQuery = true)
  public List<Book> findGuanLianXingByUserIdNative(@Param("userId") int useId);
*/
 
}
