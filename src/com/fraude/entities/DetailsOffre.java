package com.fraude.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the details_offre database table.
 * 
 */
@Entity
@Table(name="details_offre",schema="tableref")
@NamedQuery(name="DetailsOffre.findAll", query="SELECT d FROM DetailsOffre d")
public class DetailsOffre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="cycle_offre")
	private BigDecimal cycleOffre;

	@Temporal(TemporalType.DATE)
	@Column(name="date_debut_validite")
	private Date dateDebutValidite;

	@Temporal(TemporalType.DATE)
	@Column(name="date_fin")
	private Date dateFin;

	
	@OneToOne
	@JoinColumn(name="id_offre",referencedColumnName="id_offre")
	private Offre offre;
	
	
	@OneToMany(mappedBy="detailsOffre",cascade=CascadeType.ALL,orphanRemoval=true)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<OffrePlanTarif> listeplans;
	
	
	public List<OffrePlanTarif> getListeplans() {
		return listeplans;
	}
	public void setListeplans(List<OffrePlanTarif> listeplans) {
		this.listeplans = listeplans;
	}
	


	private double tarif;

	public DetailsOffre() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getCycleOffre() {
		return this.cycleOffre;
	}

	public void setCycleOffre(BigDecimal cycleOffre) {
		this.cycleOffre = cycleOffre;
	}

	public Date getDateDebutValidite() {
		return this.dateDebutValidite;
	}

	public void setDateDebutValidite(Date dateDebutValidite) {
		this.dateDebutValidite = dateDebutValidite;
	}

	public Date getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

public Offre getOffre() {
	return offre;
}
public void setOffre(Offre offre) {
	this.offre = offre;
}

	public double getTarif() {
		return this.tarif;
	}

	public void setTarif(double tarif) {
		this.tarif = tarif;
	}

}