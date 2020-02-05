package com.example.dto;

import com.example.model.Review;

public class ReviewDto {

	private long id;

	private String commentsForAuthors;

	private String commentsForEditors;

	private String recommendation;

	private ArticleDto article;
	
	private AppUserDto appuser;

	public ReviewDto() { }
	
	public ReviewDto(Review review) {
		
		this.setId(review.getId());
		this.setCommentsForAuthors(review.getCommentsForAuthors());
		this.setCommentsForEditors(review.getCommentsForEditors());
		this.setAppuser(new AppUserDto(review.getAppuser()));
		this.setArticle(new ArticleDto(review.getArticle()));
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCommentsForAuthors() {
		return commentsForAuthors;
	}

	public void setCommentsForAuthors(String commentsForAuthors) {
		this.commentsForAuthors = commentsForAuthors;
	}

	public String getCommentsForEditors() {
		return commentsForEditors;
	}

	public void setCommentsForEditors(String commentsForEditors) {
		this.commentsForEditors = commentsForEditors;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	public ArticleDto getArticle() {
		return article;
	}

	public void setArticle(ArticleDto article) {
		this.article = article;
	}

	public AppUserDto getAppuser() {
		return appuser;
	}

	public void setAppuser(AppUserDto appuser) {
		this.appuser = appuser;
	}	
}
