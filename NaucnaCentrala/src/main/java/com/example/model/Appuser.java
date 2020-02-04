package com.example.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the appuser database table.
 * 
 */
@Entity
@NamedQuery(name="Appuser.findAll", query="SELECT a FROM Appuser a")
public class Appuser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String city;

	private String country;

	private String email;

	private String name;

	private String password;

	private String role;

	private String surname;

	private String username;

	//bi-directional many-to-many association to ScientificField
	@ManyToMany(mappedBy="appusers", fetch=FetchType.EAGER)
	private Set<ScientificField> scientificFields;

	//bi-directional many-to-one association to Article
	@OneToMany(mappedBy="appuser", fetch=FetchType.EAGER)
	private Set<Article> articles;

	//bi-directional many-to-many association to Magazine
	@ManyToMany(mappedBy="appusers", fetch=FetchType.EAGER)
	private Set<Magazine> magazines;

	//bi-directional many-to-one association to Review
	@OneToMany(mappedBy="appuser", fetch=FetchType.EAGER)
	private Set<Review> reviews;

	//bi-directional many-to-one association to Subscription
	@OneToMany(mappedBy="appuser", fetch=FetchType.EAGER)
	private Set<Subscription> subscriptions;

	public Appuser() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
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
		article.setAppuser(this);

		return article;
	}

	public Article removeArticle(Article article) {
		getArticles().remove(article);
		article.setAppuser(null);

		return article;
	}

	public void setMagazines(Set<Magazine> magazines) {
		this.magazines = magazines;
	}

	public Set<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	public Review addReview(Review review) {
		getReviews().add(review);
		review.setAppuser(this);

		return review;
	}

	public Review removeReview(Review review) {
		getReviews().remove(review);
		review.setAppuser(null);

		return review;
	}

	public Set<Subscription> getSubscriptions() {
		return this.subscriptions;
	}

	public void setSubscriptions(Set<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public Subscription addSubscription(Subscription subscription) {
		getSubscriptions().add(subscription);
		subscription.setAppuser(this);

		return subscription;
	}

	public Subscription removeSubscription(Subscription subscription) {
		getSubscriptions().remove(subscription);
		subscription.setAppuser(null);

		return subscription;
	}

}