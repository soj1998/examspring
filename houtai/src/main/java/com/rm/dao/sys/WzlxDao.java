package com.rm.dao.sys;



import org.springframework.data.jpa.repository.JpaRepository;
import com.rm.entity.lieju.Wzlx;

/**
 * 图书Dao接口
 * @author user
 *
 */
public interface WzlxDao extends JpaRepository<Wzlx, Integer>{
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
