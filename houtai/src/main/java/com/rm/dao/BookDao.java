package com.rm.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rm.entity.Book;

/**
 * 图书Dao接口
 * @author user
 *
 */
public interface BookDao extends JpaRepository<Book, Integer>{

	
 
}
