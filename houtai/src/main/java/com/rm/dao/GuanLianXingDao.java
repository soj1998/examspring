package com.rm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rm.entity.GuanLianXing;

/**
 * 图书Dao接口
 * @author user
 *
 */
public interface GuanLianXingDao extends JpaRepository<GuanLianXing, Integer>, JpaSpecificationExecutor<GuanLianXing>{
	 @Query(value = "from GuanLianXing where userId = :userId")
	    public List<GuanLianXing> findGuanLianXingByUserId(@Param("userId") int useId);
	 
	 @Query(value = "update GuanLianXing set userId = :userId where id = :id")
	 @Modifying
	 public Integer updateUserIdById(@Param("id") int id,@Param("userId") int userId);

	 @Query(value = "select * from t_guanlianxing where user_id = :userId",nativeQuery = true)
	  public List<GuanLianXing> findGuanLianXingByUserIdNative(@Param("userId") int useId);

	     
 
}
