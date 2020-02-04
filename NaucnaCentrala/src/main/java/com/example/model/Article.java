package com.example.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the article database table.
 * 
 */
@Entity
@NamedQuery(name="Article.findAll", query="SELECT a FROM Article a")
public class Article implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="abstract")
	private String abstract_;

	@Column(name="key_words")
	private String keyWords;

	private String pdflocation;

	private String pdfurl;

	private String title;

	//bi-directional many-to-one association to Magazine
	@ManyToOne
	private Magazine magazine;

	//bi-directional many-to-one association to Appuser
	@ManyToOne
	private Appuser appuser;

	//bi-directional many-to-one association to Review
	@OneToMany(mappedBy="article", fetch=FetchType.EAGER)
	private Set<Review> reviews;

	public Article() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAbstract_() {
		return this.abstract_;
	}

	public void setAbstract_(String abstract_) {
		this.abstract_ = abstract_;
	}

	public String getKeyWords() {
		return this.keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getPdflocation() {
		return this.pdflocation;
	}

	public void setPdflocation(String pdflocation) {
		this.pdflocation = pdflocation;
	}

	public String getPdfurl() {
		return this.pdfurl;
	}

	public void setPdfurl(String pdfurl) {
		this.pdfurl = pdfurl;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Appuser getAppuser() {
		return this.appuser;
	}

	public void setAppuser(Appuser appuser) {
		this.appuser = appuser;
	}

	public Magazine getMagazine() {
		return this.magazine;
	}

	public void setMagazine(Magazine magazine) {
		this.magazine = magazine;
	}

	public Set<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	public Review addReview(Review review) {
		getReviews().add(review);
		review.setArticle(this);

		return review;
	}

	public Review removeReview(Review review) {
		getReviews().remove(review);
		review.setArticle(null);

		return review;
	}

}