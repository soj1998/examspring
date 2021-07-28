package com.rm.dao;



import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.rm.entity.ExamQue;


/**
 * 图书Dao接口
 * @author user
 *
 */
public interface ExamQueDao extends JpaRepository<ExamQue, Integer>{
	@Query(value = "select * from t_examquesjk",nativeQuery = true)
	public List<ExamQue> buyaoyonggetExamQueList(@Param("startIndex") int sindex,@Param("pageSize") int pages);
	
	@Query(value = "select * from t_examquesjk where szid = :sid and examtype = :tmlx and yxbz ='Y' order by id",nativeQuery = true)
	public List<ExamQue> getexamquezhanshi(Pageable pageable, @Param("sid") int ssid, @Param("tmlx") String timuleixing);
	
	@Query(value = "select count(*) from t_examquesjk where szid = :sid and examtype = :tmlx and yxbz ='Y' ",nativeQuery = true)
	public int gettmleixingsl(@Param("sid") int ssid, @Param("tmlx") String timuleixing);
	
	@Query(value = "select * from t_examquesjk where szid = :sid and examtype = :tmlx and yxbz ='Y' ",nativeQuery = true)
	public List<ExamQue> getallbytmleixing(@Param("sid") int ssid, @Param("tmlx") String timuleixing);
	
	@Query(value = "select * from t_examquesjk where szid = :sid and biaotiid = :biaoti and yxbz ='Y' order by examtype,lrsj desc",nativeQuery = true)
	public List<ExamQue> getallbybiaoti(@Param("sid") int ssid, @Param("biaoti") int biaoti);
	
	@Query(value = "select count(*) from t_examquesjk where szid = :sid and biaoti = :biaoti and yxbz ='Y' ",nativeQuery = true)
	public int getallbybiaoticount(@Param("sid") int ssid, @Param("biaoti") String biaoti);
	
	
	@Query(value = "select * from t_examquesjk where yxbz ='Y' order by id",nativeQuery = true)
	public List<ExamQue> getall(Pageable pageable);
	
	@Query(value = "select * from t_examquesjk where szid = :sid and yxbz ='Y' order by id",nativeQuery = true)
	public List<ExamQue> getallbyszid(@Param("sid") int ssid, Pageable pageable);
	
	@Query(value = "select * from t_examquesjk where yxbz ='Y' order by lrsj",nativeQuery = true)
	public List<ExamQue> getallorderbysj(Pageable pageable);
	
}
