package com.example.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the magazine database table.
 * 
 */
@Entity
@NamedQuery(name="Magazine.findAll", query="SELECT m FROM Magazine m")
public class Magazine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String description;

	private boolean isopenaccess;

	private String title;

	//bi-directional many-to-one association to Appuser
	@ManyToOne
	private Appuser appuser;

	//bi-directional many-to-many association to ScientificField
	@ManyToMany(fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinTable(
		name="magazine_scifield"
		, joinColumns={
			@JoinColumn(name="magazine_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="scientific_field_id")
			}
		)
	private Set<ScientificField> scientificFields;

	//bi-directional many-to-one association to Article
	@OneToMany(mappedBy="magazine", fetch=FetchType.EAGER)
	private Set<Article> articles;

	//bi-directional many-to-many association to Appuser
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="magazine_reviewer"
		, joinColumns={
			@JoinColumn(name="magazine_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="appuser_id")
			}
		)
	private Set<Appuser> reviewers;
	
	public Set<Appuser> getReviewers() {
		return reviewers;
	}

	public void setReviewers(Set<Appuser> reviewers) {
		this.reviewers = reviewers;
	}

		//bi-directional many-to-many association to Appuser
		@ManyToMany(fetch=FetchType.EAGER)
		@JoinTable(
			name="magazine_editor"
			, joinColumns={
				@JoinColumn(name="magazine_id")
				}
			, inverseJoinColumns={
				@JoinColumn(name="appuser_id")
				}
			)
		private Set<Appuser> editors;
		
	//bi-directional many-to-one association to Subscription
	@OneToMany(mappedBy="magazine", fetch=FetchType.EAGER)
	private Set<Subscription> subscriptions;

	public Magazine() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean getIsopenaccess() {
		return this.isopenaccess;
	}

	public void setIsopenaccess(boolean isopenaccess) {
		this.isopenaccess = isopenaccess;
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

	public Set<ScientificField> getScientificFields() {
		return this.scientificFields;
	}

	public void setScientificFields(Set<ScientificField> scientificFields) {
		this.scientificFields = scientificFields;
	}

	public Set<Article> getArticles() {
		return this.articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	public Article addArticle(Article article) {
		getArticles().add(article);
		article.setMagazine(this);

		return article;
	}

	public Article removeArticle(Article article) {
		getArticles().remove(article);
		article.setMagazine(null);

		return article;
	}

	public Set<Subscription> getSubscriptions() {
		return this.subscriptions;
	}

	public void setSubscriptions(Set<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public Subscription addSubscription(Subscription subscription) {
		getSubscriptions().add(subscription);
		subscription.setMagazine(this);

		return subscription;
	}

	public Subscription removeSubscription(Subscription subscription) {
		getSubscriptions().remove(subscription);
		subscription.setMagazine(null);

		return subscription;
	}

}