package com.example.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the review database table.
 * 
 */
@Entity
@NamedQuery(name="Review.findAll", query="SELECT r FROM Review r")
public class Review implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@Column(name="comments_for_authors")
	private String commentsForAuthors;

	@Column(name="comments_for_editors")
	private String commentsForEditors;

	private String recommendation;

	//bi-directional many-to-one association to Article
	@ManyToOne
	private Article article;

	//bi-directional many-to-one association to Appuser
	@ManyToOne
	private Appuser appuser;

	public Review() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCommentsForAuthors() {
		return this.commentsForAuthors;
	}

	public void setCommentsForAuthors(String commentsForAuthors) {
		this.commentsForAuthors = commentsForAuthors;
	}

	public String getCommentsForEditors() {
		return this.commentsForEditors;
	}

	public void setCommentsForEditors(String commentsForEditors) {
		this.commentsForEditors = commentsForEditors;
	}

	public String getRecommendation() {
		return this.recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	public Article getArticle() {
		return this.article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public Appuser getAppuser() {
		return this.appuser;
	}

	public void setAppuser(Appuser appuser) {
		this.appuser = appuser;
	}

}