package com.rm.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import com.rm.entity.ZhuanLan;



/**
 * 图书Dao接口
 * @author user
 *
 */
public interface ZhuanLanDao extends JpaRepository<ZhuanLan, Integer>{
	
}
