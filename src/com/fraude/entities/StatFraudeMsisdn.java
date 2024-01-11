package com.fraude.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import javax.persistence.Table;

/**
 * The persistent class for the stat_fraude_msisdn database table.
 * 
 */
@Entity
@Table(name="stat_fraude_msisdn",schema="stat")
@NamedQuery(name="StatFraudeMsisdn.findAll", query="SELECT s FROM StatFraudeMsisdn s")
public class StatFraudeMsisdn implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer id;

	@Column(name="date_decision")
	private String dateDecision;

	private BigDecimal duree;


	
	@ManyToOne
	@JoinColumn(name="id_categorie",referencedColumnName="id")
	private CategoriesFraude category;

	
	private String location;

	@Column(name="max_date")
	private String maxDate;

	@Column(name="min_date")
	private String minDate;

	private String msisdn;

	@Column(name="nb_appel")
	private BigDecimal nbAppel;

	public StatFraudeMsisdn() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDateDecision() {
		return this.dateDecision;
	}

	public void setDateDecision(String dateDecision) {
		this.dateDecision = dateDecision;
	}

	public BigDecimal getDuree() {
		return this.duree;
	}

	public void setDuree(BigDecimal duree) {
		this.duree = duree;
	}

	public CategoriesFraude getCategory() {
		return category;
	}
	public void setCategory(CategoriesFraude category) {
		this.category = category;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getMaxDate() {
		return this.maxDate;
	}

	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}

	public String getMinDate() {
		return this.minDate;
	}

	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}

	public String getMsisdn() {
		return this.msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public BigDecimal getNbAppel() {
		return this.nbAppel;
	}

	public void setNbAppel(BigDecimal nbAppel) {
		this.nbAppel = nbAppel;
	}

}