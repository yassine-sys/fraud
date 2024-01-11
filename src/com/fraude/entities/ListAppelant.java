package com.fraude.entities;

import javax.persistence.Table;
import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the list_appelant database table.
 * 
 */
@Entity
@Table(name = "list_appelant", schema = "tableref")
@NamedQuery(name = "ListAppelant.findAll", query = "SELECT l FROM ListAppelant l")
public class ListAppelant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToMany(mappedBy = "appelant", cascade = CascadeType.REMOVE, orphanRemoval = true , fetch = FetchType.EAGER)
	@JsonIgnore
	private List<ListDetailsAppelant> listedetails;

	@Column(name = "date_modif")
	private Timestamp dateModif;

	private String nom;

	@Column(name = "nom_uitlisateur")
	private String nomUitlisateur;

	public ListAppelant() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getDateModif() {
		return this.dateModif;
	}

	public void setDateModif(Timestamp dateModif) {
		this.dateModif = dateModif;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNomUitlisateur() {
		return this.nomUitlisateur;
	}

	public void setNomUitlisateur(String nomUitlisateur) {
		this.nomUitlisateur = nomUitlisateur;
	}

	public List<ListDetailsAppelant> getListedetails() {
		return listedetails;
	}

	public void setListedetails(List<ListDetailsAppelant> listedetails) {
		this.listedetails = listedetails;
	}

}