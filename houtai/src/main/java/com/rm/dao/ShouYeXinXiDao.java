package com.rm.dao;




import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rm.entity.ShouYeXinXi;



/**
 * 图书Dao接口
 * @author user
 *
 */
public interface ShouYeXinXiDao extends JpaRepository<ShouYeXinXi, Integer>{
	@Query(value = "select count(*) from t_shouyexinxi where yxbz ='Y' ",nativeQuery = true)
	public int getCount();
	
	@Query(value = "select count(*) from t_shouyexinxi where szid = :sid and yxbz ='Y' ",nativeQuery = true)
	public int getCountbySz(@Param("sid") int ssid);
	
	@Query(value = "select * from t_shouyexinxi where yxbz ='Y' order by id",nativeQuery = true)
	public List<ShouYeXinXi> getzlbybtidfuyi(Pageable pageable);
	
	@Query(value = "select * from t_shouyexinxi where szid = :sid and yxbz ='Y' order by id",nativeQuery = true)
	public List<ShouYeXinXi> getzlbysz(Pageable pageable,@Param("sid") int ssid);
	
}
