package com.fraude.entities;
import javax.persistence.Table;

import org.json.JSONPropertyIgnore;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the list_details_appelant database table.
 * 
 */
@Entity
@Table(name="list_details_appelant",schema="tableref")
@NamedQuery(name="ListDetailsAppelant.findAll", query="SELECT l FROM ListDetailsAppelant l")
public class ListDetailsAppelant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="date_modif")
	private Timestamp dateModif;

	private String hotlistnumber;
	@ManyToOne
	@JoinColumn(name="id_hotlist",referencedColumnName="id")
	
	private ListAppelant appelant;

	
	@Column(name="nom_utilisateur")
	private String nomUtilisateur;

	public ListDetailsAppelant() {
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

	public String getHotlistnumber() {
		return this.hotlistnumber;
	}

	public void setHotlistnumber(String hotlistnumber) {
		this.hotlistnumber = hotlistnumber;
	}

	public ListAppelant getAppelant() {
		return appelant;
	}
	public void setAppelant(ListAppelant appelant) {
		this.appelant = appelant;
	}
	public String getNomUtilisateur() {
		return this.nomUtilisateur;
	}

	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

}