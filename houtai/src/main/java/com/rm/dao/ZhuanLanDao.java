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
	@Query(value = "select count(*) from t_zhuanlan where btid = -1 ",nativeQuery = true)
	public int getCountbybtidfuyi();
	
	@Query(value = "select * from t_zhuanlan where btid = -1  order by id",nativeQuery = true)
	public List<ZhuanLan> getzlbybtidfuyi(Pageable pageable);
	
	@Query(value = "select * from t_zhuanlan where btid = :rid",nativeQuery = true)
	public List<ZhuanLan> getzlbyid(@Param("rid") int rootid);
}
