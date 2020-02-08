package com.example.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the subscription database table.
 * 
 */
@Entity
@NamedQuery(name="Subscription.findAll", query="SELECT s FROM Subscription s")
public class Subscription implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private int price;

	//bi-directional many-to-one association to Appuser
	@ManyToOne
	private Appuser appuser;

	//bi-directional many-to-one association to Magazine
	@ManyToOne
	private Magazine magazine;

	public Subscription() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPrice() {
		return this.price;
	}

	public void setPrice(int price) {
		this.price = price;
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

}