package com.rm.dao;




import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rm.entity.ZhuanLanZhengGe;



/**
 * 图书Dao接口
 * @author user
 *
 */
public interface ZhuanLanZgDao extends JpaRepository<ZhuanLanZhengGe, Integer>{
	@Query(value = "select count(*) from t_zhuanlanzg ",nativeQuery = true)
	public int getCount();
	
	@Query(value = "select count(*) from t_zhuanlanzg where szid = :sid and yxbz ='Y' ",nativeQuery = true)
	public int getCountbySz(@Param("sid") int ssid);
	
	@Query(value = "select * from t_zhuanlanzg where order by id",nativeQuery = true)
	public List<ZhuanLanZhengGe> getzlbybtidfuyi(Pageable pageable);
	
	@Query(value = "select * from t_zhuanlanzg where (btid = -1 or btid= -100) and szid = :sid and yxbz ='Y' order by id",nativeQuery = true)
	public List<ZhuanLanZhengGe> getzlbysz(Pageable pageable,@Param("sid") int ssid);
	
}
