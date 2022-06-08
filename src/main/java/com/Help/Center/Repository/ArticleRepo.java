 package com.Help.Center.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Help.Center.Models.Articles;

public interface ArticleRepo  extends JpaRepository<Articles, Integer>{
	Articles findByTitle(String title);

}
