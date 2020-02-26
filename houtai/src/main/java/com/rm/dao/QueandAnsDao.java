package com.rm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.rm.entity.QueandAns;

/**
 * 图书Dao接口
 * @author user
 *
 */
public interface QueandAnsDao extends JpaRepository<QueandAns, Integer>{
	@Query(value = "select * from t_xueke x1,t_queans q where x1.id = q.xue_ke_id",nativeQuery = true)
	  public List<Object> getXueKeByQuesNative();
	 
}
