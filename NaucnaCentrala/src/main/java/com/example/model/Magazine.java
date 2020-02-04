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

	private int sciArea_id;

	private String title;

	//bi-directional many-to-one association to Appuser
	@ManyToOne
	private Appuser appuser;

	//bi-directional many-to-many association to ScientificField
	@ManyToMany(mappedBy="magazines", fetch=FetchType.EAGER)
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
	private Set<Appuser> appusers;

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

	public int getSciArea_id() {
		return this.sciArea_id;
	}

	public void setSciArea_id(int sciArea_id) {
		this.sciArea_id = sciArea_id;
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