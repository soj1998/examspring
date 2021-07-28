package com.rm.dao;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rm.entity.BiaoTi;


/**
 * 图书Dao接口
 * @author user
 *
 */
public interface BiaoTiDao extends JpaRepository<BiaoTi, Integer>{
	
	@Query(value = "select count(*) from t_biaoti where szid = :sid and biaoti = :biaoti and yxbz ='Y' ",nativeQuery = true)
	public int getallbybiaoticount(@Param("sid") int ssid, @Param("biaoti") String biaoti);
}
