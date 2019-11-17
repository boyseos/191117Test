package com.ship.web.brd;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleMapper {
	
	@Select("select count(id) from ${artname}")
	public int countArticle(Map<?, ?> map);
	
	@Insert("insert into cgv(content) VALUES (#{content})")
	public void insertArticle(Article param);
	
	@Select("SELECT id AS id, A.* FROM\n" + 
			"				(SELECT *\n" + 
			"				 FROM ${pxy.artname}\n" + 
			"	 			 ORDER BY id DESC) A\n" + 
			" 			LIMIT #{pxy.startRow},#{pxy.pageSize}")
	public List<Article> selectList(Map<?,?> map);
	
	@Select("create table IF NOT EXISTS ${tabname}(id int primary key auto_increment, content varchar(100))")
	public void createArticle(Map<?,?> map);

	@Select("drop table IF EXISTS ${tabname}")
	public void dropArticle(Map<?,?> map);
}
