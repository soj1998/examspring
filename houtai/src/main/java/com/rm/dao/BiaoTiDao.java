package com.rm.dao;




import org.springframework.data.jpa.repository.JpaRepository;
import com.rm.entity.BiaoTi;


/**
 * 图书Dao接口
 * @author user
 *
 */
public interface BiaoTiDao extends JpaRepository<BiaoTi, Integer>{
	
	
}
