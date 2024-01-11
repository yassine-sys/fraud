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
 * The persistent class for the list_cellid database table.
 * 
 */
@Entity
@Table(name = "list_cellid", schema = "tableref")
@NamedQuery(name = "ListCellid.findAll", query = "SELECT l FROM ListCellid l")
public class ListCellid implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToMany(mappedBy = "cellId", orphanRemoval = true, fetch = FetchType.EAGER)
	@JsonIgnore
	private List<ListDetailsCellid> listedetails;

	@Column(name = "date_modif")
	private Timestamp dateModif;

	private String nom;

	@Column(name = "nom_utilisateur")
	private String nomUtilisateur;

	public ListCellid() {
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

	public List<ListDetailsCellid> getListedetails() {
		return listedetails;
	}

	public void setListedetails(List<ListDetailsCellid> listedetails) {
		this.listedetails = listedetails;
	}
}