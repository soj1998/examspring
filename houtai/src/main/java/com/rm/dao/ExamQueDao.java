package com.rm.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import com.rm.entity.ExamQue;



/**
 * 图书Dao接口
 * @author user
 *
 */
public interface ExamQueDao extends JpaRepository<ExamQue, Integer>{
	
}
