package com.rm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rm.entity.TreeNodeSjk;
import com.rm.entity.XueKeBaoCun;

/**
 * 图书Dao接口
 * @author user
 *
 */
public interface AtcDao extends JpaRepository<TreeNodeSjk, Integer>{

	 @Query(value = "select * from t_myatc1 where parentid = :pid",nativeQuery = true)
	  public List<TreeNodeSjk> getTreeByParentid(@Param("pid") int parentid);
	 
	 
}
