package com.fraude.entities;
import java.io.Serializable;

import javax.persistence.*;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the type_destination database table.
 * 
 */
@Entity
@Table(schema="tableref",name="type_destination")
@NamedQuery(name="TypeDestination.findAll", query="SELECT t FROM TypeDestination t")
public class TypeDestination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer id;
	
	

	@Column(name="date_modif")
	private Timestamp dateModif;

	private String description;

	@Column(name="nom_utilisateur")
	private String nomUtilisateur;

	@Column(name="type_dest")
	@ToUse
	private String typeDest;

	public TypeDestination() {
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNomUtilisateur() {
		return this.nomUtilisateur;
	}

	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

	public String getTypeDest() {
		return this.typeDest;
	}

	public void setTypeDest(String typeDest) {
		this.typeDest = typeDest;
	}

	

	
	
}