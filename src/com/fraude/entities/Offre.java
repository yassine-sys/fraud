package com.fraude.entities;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Table;
/**
 * The persistent class for the offres database table.
 * 
 */
@Entity
@Table(name="offres",schema="tableref")
@NamedQuery(name="Offre.findAll", query="SELECT o FROM Offre o")
public class Offre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_offre")
	private String idOffre;
	
	
	
	

	@Column(name="nom_offre")
	private String nomOffre;

	@Column(name="type_offre")
	private String typeOffre;

	public Offre() {
	}

	public String getIdOffre() {
		return this.idOffre;
	}

	public void setIdOffre(String idOffre) {
		this.idOffre = idOffre;
	}

	public String getNomOffre() {
		return this.nomOffre;
	}

	public void setNomOffre(String nomOffre) {
		this.nomOffre = nomOffre;
	}

	public String getTypeOffre() {
		return this.typeOffre;
	}

	public void setTypeOffre(String typeOffre) {
		this.typeOffre = typeOffre;
	}

}