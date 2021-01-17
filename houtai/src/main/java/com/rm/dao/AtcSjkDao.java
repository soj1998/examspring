package com.rm.dao;


import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rm.entity.AtcSjk;



/**
 * 图书Dao接口
 * @author user
 *
 */
public interface AtcSjkDao extends JpaRepository<AtcSjk, Integer>{
	@Query(value = "select * from t_atcsjk where szid = :szid and wzlxid = :wzlxid and yxbz = 'Y' ORDER BY lrsj DESC limit 0,1",nativeQuery = true)
	public List<AtcSjk> getBanbenBySzWzlx(@Param("szid") int szid,@Param("wzlxid") int wzlxid);
	
	@Transactional
	@Modifying
	@Query(value = "update t_atcsjk set yxbz = 'N' where szid = :szid and wzlxid = :wzlxid and yxbz = 'Y'",nativeQuery = true)
	public int updateBySzWzlx(@Param("szid") int szid,@Param("wzlxid") int wzlxid);
	 
}
