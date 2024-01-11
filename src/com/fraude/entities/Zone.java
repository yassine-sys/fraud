package com.fraude.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the zones database table.
 * 
 */
@Entity
@Table(name = "zones", schema = "tableref")
@NamedQuery(name = "Zone.findAll", query = "SELECT z FROM Zone z")
public class Zone implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToMany(mappedBy = "zone", cascade = { CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.REMOVE }, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<ZonesDestination> zonesdestinations = new ArrayList<ZonesDestination>();

	public List<ZonesDestination> getZonesdestinations() {
		return zonesdestinations;
	}

	public void setZonesdestinations(List<ZonesDestination> zonesdestinations) {
		this.zonesdestinations = zonesdestinations;
	}

	@Column(name = "code_zones")
	private String codeZones;

	@Column(name = "date_modif")
	private Timestamp dateModif;

	@Column(name = "date_modification")
	private Timestamp dateModification;

	@ManyToOne
	@JoinColumn(name = "id_package", referencedColumnName = "id")
	private PackageZone packagezone;

	public PackageZone getPackagezone() {
		return packagezone;
	}

	public void setPackagezone(PackageZone packagezone) {
		this.packagezone = packagezone;
	}

	private String nom;

	@Column(name = "nom_utilisateur")
	private String nomUtilisateur;

	public Zone() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodeZones() {
		return this.codeZones;
	}

	public void setCodeZones(String codeZones) {
		this.codeZones = codeZones;
	}

	public Timestamp getDateModif() {
		return this.dateModif;
	}

	public void setDateModif(Timestamp dateModif) {
		this.dateModif = dateModif;
	}

	public Timestamp getDateModification() {
		return this.dateModification;
	}

	public void setDateModification(Timestamp dateModification) {
		this.dateModification = dateModification;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeZones == null) ? 0 : codeZones.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((packagezone == null) ? 0 : packagezone.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Zone other = (Zone) obj;
		if (codeZones == null) {
			if (other.codeZones != null)
				return false;
		} else if (!codeZones.equals(other.codeZones))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (packagezone == null) {
			if (other.packagezone != null)
				return false;
		} else if (!packagezone.equals(other.packagezone))
			return false;
		return true;
	}

}