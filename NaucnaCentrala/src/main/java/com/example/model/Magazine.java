package com.example.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
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

	@Lob
	private String description;

	private boolean isopenaccess;

	private String title;

	//bi-directional many-to-one association to Appuser
	@ManyToOne
	private Appuser appuser;

	//bi-directional many-to-many association to ScientificField
	@ManyToMany(fetch=FetchType.EAGER)
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

}