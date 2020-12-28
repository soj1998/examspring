package com.rm.entity.linshi;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "article")
public class Article {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    private Long id;
   
    
    @Column(nullable = false, length = 50) // 映射为字段，值不能为空
    private String title;
   
    @Column(nullable = false) // 映射为字段，值不能为空
    private String content;//文章全文内容
    
    //可选属性optional=false,表示author不能为空。删除文章，不影响用户
    @ManyToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH},optional=false)
    @JoinColumn(name="author_id")//设置在article表中的关联字段(外键)
    private Author author;//所属作者

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Article{ id='"+id+"'}";
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	} 
	
	
}
