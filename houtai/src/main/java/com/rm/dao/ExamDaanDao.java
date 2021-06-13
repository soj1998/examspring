package com.rm.dao;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rm.entity.ExamDaan;



/**
 * 图书Dao接口
 * @author user
 *
 */
public interface ExamDaanDao extends JpaRepository<ExamDaan, Integer>{
	@Query(value = "select id,daan,examqueid from t_examdaansjk where examqueid = :wid",nativeQuery = true)
	public ExamDaan getExamDaanByQue(@Param("wid") int wid);
}
