package com.example.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the scientific_field database table.
 * 
 */
@Entity
@Table(name="scientific_field")
@NamedQuery(name="ScientificField.findAll", query="SELECT s FROM ScientificField s")
public class ScientificField implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String name;

	//bi-directional many-to-many association to Appuser
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="appuser_scifield"
		, joinColumns={
			@JoinColumn(name="scientific_field_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="appuser_id")
			}
		)
	private Set<Appuser> appusers;

	//bi-directional many-to-many association to Magazine
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="magazine_scifield"
		, joinColumns={
			@JoinColumn(name="scientific_field_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="magazine_id")
			}
		)
	private Set<Magazine> magazines;

	public ScientificField() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Appuser> getAppusers() {
		return this.appusers;
	}

	public void setAppusers(Set<Appuser> appusers) {
		this.appusers = appusers;
	}

	public Set<Magazine> getMagazines() {
		return this.magazines;
	}

	public void setMagazines(Set<Magazine> magazines) {
		this.magazines = magazines;
	}

}