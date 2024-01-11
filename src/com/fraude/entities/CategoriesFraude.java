package com.fraude.entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.Table;


/**
 * The persistent class for the categories_fraudes database table.
 * 
 */
@Entity
@Table(name="categories_fraudes",schema="tableref")
@NamedQuery(name="CategoriesFraude.findAll", query="SELECT c FROM CategoriesFraude c")
public class CategoriesFraude implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="nom_categorie")
	private String nomCategorie;

	public CategoriesFraude() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomCategorie() {
		return this.nomCategorie;
	}

	public void setNomCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}

}