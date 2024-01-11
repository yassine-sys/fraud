package com.fraude.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the zones_destination database table.
 * 
 */
@Entity
@Table(name="zones_destination",schema="tableref")
@NamedQuery(name="ZonesDestination.findAll", query="SELECT z FROM ZonesDestination z")
public class ZonesDestination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.DATE)
	@Column(name="date_debut_validite")
	private Date dateDebutValidite;

	@Column(name="date_modif")
	private Timestamp dateModif;
	
	
	
	
	@ManyToOne
	@JoinColumn(name="id_zone",referencedColumnName="id")
	private Zone zone;

	
	
	 
	@ManyToOne
	@JoinColumn(name="destination",referencedColumnName="id")
	private Destination destination;


	@Column(name="nom_utilisateur")
	private String nomUtilisateur;

	public ZonesDestination() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDateDebutValidite() {
		return this.dateDebutValidite;
	}

	public void setDateDebutValidite(Date dateDebutValidite) {
		this.dateDebutValidite = dateDebutValidite;
	}

	public Timestamp getDateModif() {
		return this.dateModif;
	}

	public void setDateModif(Timestamp dateModif) {
		this.dateModif = dateModif;
	}


	
	public Destination getDestination() {
		return destination;
	}
	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public Zone getZone() {
		return zone;
	}
	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public String getNomUtilisateur() {
		return this.nomUtilisateur;
	}

	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

}