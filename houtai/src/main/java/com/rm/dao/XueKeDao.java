package com.rm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rm.entity.XueKeBaoCun;

/**
 * 图书Dao接口
 * @author user
 *
 */
public interface XueKeDao extends JpaRepository<XueKeBaoCun, Integer>{

	 @Query(value = "select distinct(xue_ke) from t_xueke",nativeQuery = true)
	  public List<String> getXueKeNative();
	 
	 @Query(value = "select distinct(er_ji_fen_lei) from t_xueke where xue_ke = :xueke",nativeQuery = true)
	  public List<String> getErJiFenLeiByXueKeNative(@Param("xueke") String xueke);
 
	 @Query(value = "select * from t_xueke where xue_ke = :xueke and er_ji_fen_lei= :erjifenlei",nativeQuery = true)
	  public List<XueKeBaoCun> getZhangJieByXueKeNative(@Param("xueke") String xueke,@Param("erjifenlei") String erjifenlei);
}
