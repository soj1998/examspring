package com.rm.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.rm.entity.GuanLianXing;

/**
 * 图书Dao接口
 * @author user
 *
 */
@Repository("guanLianXingSpecDao")  
public interface IguanLianXingSpecDao extends JpaSpecificationExecutor<GuanLianXing>{

	
 
}
