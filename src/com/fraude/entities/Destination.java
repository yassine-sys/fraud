package com.fraude.entities;

import java.io.Serializable;

import javax.faces.convert.FacesConverter;
import javax.persistence.*;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The persistent class for the destination database table.
 * 
 */
@Entity
@Table(schema = "tableref")
@NamedQuery(name = "Destination.findAll", query = "SELECT d FROM Destination d")
@FacesConverter(value = "Destination")
public class Destination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_type", referencedColumnName = "id")
	private TypeDestination destination;

	@OneToMany(mappedBy = "dest", cascade = { CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.REMOVE }, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<DetailsDestination> lst_details;

	@OneToMany(mappedBy = "destination")
	private List<ZonesDestination> zonesdestinations = new ArrayList<ZonesDestination>();

	public List<ZonesDestination> getZonesdestinations() {
		return zonesdestinations;
	}

	public void setZonesdestinations(List<ZonesDestination> zonesdestinations) {
		this.zonesdestinations = zonesdestinations;
	}

	public TypeDestination getDestination() {
		return destination;
	}

	public void setDestination(TypeDestination destination) {
		this.destination = destination;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "date_debut")
	private Date dateDebut;

	@Temporal(TemporalType.DATE)
	@Column(name = "date_fin")
	private Date dateFin;

	@Column(name = "date_modif")
	private Timestamp dateModif;

	private String dest;

	@Column(name = "nom_utilisateur")
	private String nomUtilisateur;

	public Destination() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getDest() {
		return this.dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public String getNomUtilisateur() {
		return this.nomUtilisateur;
	}

	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

	public List<DetailsDestination> getLst_details() {
		return lst_details;
	}

	public void setLst_details(List<DetailsDestination> lst_details) {
		this.lst_details = lst_details;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateDebut == null) ? 0 : dateDebut.hashCode());
		result = prime * result + ((dateFin == null) ? 0 : dateFin.hashCode());
		result = prime * result + ((dateModif == null) ? 0 : dateModif.hashCode());
		result = prime * result + ((dest == null) ? 0 : dest.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nomUtilisateur == null) ? 0 : nomUtilisateur.hashCode());
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
		Destination other = (Destination) obj;
		if (dateDebut == null) {
			if (other.dateDebut != null)
				return false;
		} else if (!dateDebut.equals(other.dateDebut))
			return false;
		if (dateFin == null) {
			if (other.dateFin != null)
				return false;
		} else if (!dateFin.equals(other.dateFin))
			return false;
		if (dateModif == null) {
			if (other.dateModif != null)
				return false;
		} else if (!dateModif.equals(other.dateModif))
			return false;
		if (dest == null) {
			if (other.dest != null)
				return false;
		} else if (!dest.equals(other.dest))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nomUtilisateur == null) {
			if (other.nomUtilisateur != null)
				return false;
		} else if (!nomUtilisateur.equals(other.nomUtilisateur))
			return false;
		return true;
	}

}