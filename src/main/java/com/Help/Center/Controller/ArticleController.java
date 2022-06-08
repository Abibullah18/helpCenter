package com.Help.Center.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Help.Center.Models.Articles;
import com.Help.Center.Models.Company;
import com.Help.Center.Models.Tags;
import com.Help.Center.Models.Users;
import com.Help.Center.Repository.ArticleRepo;
import com.Help.Center.Repository.CompanyRepo;
import com.Help.Center.Repository.TagRepo;
import com.Help.Center.Repository.UsersRepo;


@RestController
@RequestMapping("/api")
@Slf4j
@CrossOrigin(origins = "*")
public class ArticleController {
	@Autowired
	public ArticleRepo articleRepo;
	
	@Autowired
	public TagRepo tagRepo;
	
	@Autowired
	public UsersRepo usersRepo;
	
	@Autowired
	public CompanyRepo companyRepo;
	
	@PostMapping("/article")
	ResponseEntity<Articles> createArticle(@RequestBody Articles articles) {
		log.info("running post article");
		articleRepo.save(articles);
		articles.setDeleted(false);
		return ResponseEntity.ok().body(articles);
	}
	@GetMapping("/article")
	List<Articles>GetAllArticle(){
	List<Articles> articles= articleRepo.findAll();
		Collection<String> titles = new ArrayList<>();
	articles.forEach(article->
	{
		titles.add(article.getTitle());
	});
	System.out.println(titles);
		return articles;
	}
	@PutMapping("/UpdateArticle/{id}")
	Articles  UpdateArticle(@PathVariable (value = "id") Integer articleId,@RequestBody Articles articlesDetails ){
		Articles articles=articleRepo.findById(articleId).orElseThrow(()->new RuntimeException());
		articles.setTitle(articlesDetails.getTitle());
		articles.setDescription(articlesDetails.getDescription());
		articles.setKeywords(articlesDetails.getKeywords());
		articles.setLikes(articlesDetails.getLikes());
		articles.setDislike(articlesDetails.getDislike());
		articles.setDeleted(false);
		return  articleRepo.save(articles);
	}
	@GetMapping("/article/{id}")
		Articles ArticlesById(@PathVariable (value = "id")Integer artid){
		Articles articles=articleRepo.findById(artid).orElseThrow(()->new RuntimeException());
		return articles;

	}
	@DeleteMapping("/article/{id}")
	Articles deleteArticle(@PathVariable (value = "id") Integer artId){
		Articles articles=articleRepo.findById(artId).orElseThrow(()->new RuntimeException());
		articles.setDeleted(true);
		return articleRepo.save(articles);
	}



//Mapping Routes
	@PostMapping("/AddTagsToArticles")
	Articles AddTagsToArticles(@RequestBody SampleArticle sample) {
		String title=sample.getTitle();
		String tagName=sample.getTagName();
		Articles article=articleRepo.findByTitle(title);
		Tags tags=tagRepo.findByTagName(tagName);
		
		article.setTags(tags);
		return articleRepo.save(article);
		
	}
	@PostMapping("/AddUserToArticle")
	Articles AddUserToArticle(@RequestBody  SampleArticle sample) {
		String title=sample.getTitle();
		String userName=sample.getUserName();
		Articles article=articleRepo.findByTitle(title);
		Users users=usersRepo.findByUserName(userName);
		article.setUsers(users);
		return articleRepo.save(article);
		
	}
	@PostMapping("/AddCompanyToArticle")
	Articles AddCompanyToArticle(@RequestBody SampleArticle sample) {
		String title=sample.getTitle();
		String companyName=sample.getCompanyName();
		Articles articles=articleRepo.findByTitle(title);
		Company company=companyRepo.findBycompanyName(companyName);
		articles.setCompany(company);
		return articleRepo.save(articles);
	}
	
}
class SampleArticle{
	private String Title;
	private String tagName;
	private String companyName;
	
	private String userName;
	public String getTitle() {
		return Title;
	}
	public void setTitle(String Title) {
		this.Title = Title;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
}
