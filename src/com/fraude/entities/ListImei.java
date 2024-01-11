package com.fraude.entities;
import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the list_imei database table.
 * 
 */
@Entity
@Table(name="list_imei",schema="tableref")
@NamedQuery(name="ListImei.findAll", query="SELECT l FROM ListImei l")
public class ListImei implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="date_modif")
	private Timestamp dateModif;
	
	@OneToMany(mappedBy="imei",cascade=CascadeType.REMOVE,orphanRemoval=true, fetch = FetchType.EAGER)
	@JsonIgnore
	private List<ListDetailsImei> listDetailsImeis;

	private String nom;

	@Column(name="nom_utilisateur")
	private String nomUtilisateur;

	public ListImei() {
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

	public String getNomUtilisateur() {
		return this.nomUtilisateur;
	}

	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

}