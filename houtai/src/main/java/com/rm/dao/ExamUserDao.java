package com.rm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rm.entity.ExamUser;

/**
 * 图书Dao接口
 * @author user
 *
 */
public interface ExamUserDao extends JpaRepository<ExamUser, Integer>{

	@Query(value = "select * from t_examuser where userid = :sid and examque = :biaoti limit 0,1 ",nativeQuery = true)
	public ExamUser findExamUser(@Param("sid") int userid, @Param("biaoti") String examque);
	
 
}
