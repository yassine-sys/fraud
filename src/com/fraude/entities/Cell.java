package com.fraude.entities;

import java.io.Serializable;


import javax.persistence.*;

import javax.persistence.Table;

import java.sql.Timestamp;


/**
 * The persistent class for the cell database table.
 * 
 */
@Entity
@Table(schema="tableref")
@NamedQuery(name="Cell.findAll", query="SELECT c FROM Cell c")

public class Cell implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable=true)
	private float altitude;
	@Column(nullable=true)
	private float angle;

	@Column(nullable=true)
	private float azimuth;

	@Column(name="date_modif")
	private Timestamp dateModif;

	@Column(name="lac")
	private String Lac;

	@Column(name="cell_id")
	private String cellId;

	@Column(nullable=true)
	private float latitude;

	@Column(name="cell_nom")
	private String cellNom;

public String getCellNom() {
	return cellNom;
}
public void setCellNom(String cellNom) {
	this.cellNom = cellNom;
}
	

	@Column(name="nom_utilisateur")
	private String nomUtilisateur;

	public Cell() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getAltitude() {
		return this.altitude;
	}

	public void setAltitude(float altitude) {
		this.altitude = altitude;
	}

	public float getAngle() {
		return this.angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public float getAzimuth() {
		return this.azimuth;
	}

	public void setAzimuth(float azimuth) {
		this.azimuth = azimuth;
	}

	public Timestamp getDateModif() {
		return this.dateModif;
	}

	public void setDateModif(Timestamp dateModif) {
		this.dateModif = dateModif;
	}
	
	private String type;
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
public String getCellId() {
	return cellId;
}
public void setCellId(String cellId) {
	this.cellId = cellId;
}
public void setLac(String lac) {
	Lac = lac;
}public String getLac() {
	return Lac;
}
	
	public float getLatitude() {
		return this.latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}



	public String getNomUtilisateur() {
		return this.nomUtilisateur;
	}

	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

}