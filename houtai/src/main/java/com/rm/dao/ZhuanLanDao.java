package com.rm.dao;




import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rm.entity.ZhuanLan;



/**
 * 图书Dao接口
 * @author user
 *
 */
public interface ZhuanLanDao extends JpaRepository<ZhuanLan, Integer>{
	@Query(value = "select count(*) from t_zhuanlan where btid = -1 or btid= -100",nativeQuery = true)
	public int getCountbybtidfuyi();
	
	@Query(value = "select count(*) from t_zhuanlan where (btid = -1 or btid= -100) and szid = :sid and yxbz ='Y' ",nativeQuery = true)
	public int getCountbybtidfuyizhanshi(@Param("sid") int ssid);
	
	@Query(value = "select * from t_zhuanlan where btid = -1 or btid= -100 order by id",nativeQuery = true)
	public List<ZhuanLan> getzlbybtidfuyi(Pageable pageable);
	
	@Query(value = "select * from t_zhuanlan where btid = -1 or btid= -100 order by lrsj",nativeQuery = true)
	public List<ZhuanLan> getzlorderbysj(Pageable pageable);
	
	@Query(value = "select * from t_zhuanlan where (btid = -1 or btid= -100) and szid = :sid and yxbz ='Y' order by id",nativeQuery = true)
	public List<ZhuanLan> getzlbybtidfuyizhanshi(Pageable pageable,@Param("sid") int ssid);
	
	@Query(value = "select * from t_zhuanlan where btid = :rid",nativeQuery = true)
	public List<ZhuanLan> getzlbyid(@Param("rid") int rootid);
}
