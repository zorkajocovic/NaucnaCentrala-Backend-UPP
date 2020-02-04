package com.example.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
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
	private long id;

	private String city;

	private String country;

	private String email;

	private String name;

	private String password;
	
	private String role;

	private String surname;
	
	private String username;

	//bi-directional many-to-many association to ScientificField
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="appuser_scifield"
		, joinColumns={
			@JoinColumn(name="appuser_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="scientific_field_id")
			}
		)
	private Set<ScientificField> scientificFields;

	//bi-directional many-to-one association to Magazine
	@OneToMany(mappedBy="appuser")
	private List<Magazine> magazines;

	public Appuser() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
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

	public List<Magazine> getMagazines() {
		return this.magazines;
	}

	public void setMagazines(List<Magazine> magazines) {
		this.magazines = magazines;
	}

	public Magazine addMagazine(Magazine magazine) {
		getMagazines().add(magazine);
		magazine.setAppuser(this);

		return magazine;
	}

	public Magazine removeMagazine(Magazine magazine) {
		getMagazines().remove(magazine);
		magazine.setAppuser(null);

		return magazine;
	}

}