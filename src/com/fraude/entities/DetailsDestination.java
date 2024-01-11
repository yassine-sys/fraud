package com.fraude.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Table;
/**
 * The persistent class for the details_destination database table.
 * 
 */
@Entity
@Table(name="details_destination",schema="tableref")
@NamedQuery(name="DetailsDestination.findAll", query="SELECT d FROM DetailsDestination d")
public class DetailsDestination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	
	@ManyToOne
	@JoinColumn(name="id_dest",referencedColumnName="id")
	private Destination dest;
	
	
	public Destination getDest() {
		return dest;
	}
	public void setDest(Destination dest) {
		this.dest = dest;
	}
	@Column(name="code_dest")
	private String codeDest;

	@Temporal(TemporalType.DATE)
	@Column(name="date_debut")
	private Date dateDebut;

	@Temporal(TemporalType.DATE)
	@Column(name="date_fin")
	private Date dateFin;

	@Column(name="date_modif")
	private Timestamp dateModif;

	
	@Column(name="nom_utilisateur")
	private String nomUtilisateur;

	public DetailsDestination() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeDest() {
		return this.codeDest;
	}

	public void setCodeDest(String codeDest) {
		this.codeDest = codeDest;
	}

	public Date getDateDebut() {
		return this.dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public Timestamp getDateModif() {
		return this.dateModif;
	}

	public void setDateModif(Timestamp dateModif) {
		this.dateModif = dateModif;
	}

	
	public String getNomUtilisateur() {
		return this.nomUtilisateur;
	}

	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

}