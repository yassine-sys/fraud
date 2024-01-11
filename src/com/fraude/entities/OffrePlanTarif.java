package com.fraude.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.persistence.Table;

/**
 * The persistent class for the offre_plan_tarif database table.
 * 
 */
@Entity
@Table(name="offre_plan_tarif",schema="tableref")
@NamedQuery(name="OffrePlanTarif.findAll", query="SELECT o FROM OffrePlanTarif o")
public class OffrePlanTarif implements Serializable {
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="code_plan_tarif",referencedColumnName="code_plan_tarifaire")
	private PlanTarifaire plan;
	

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	
	@ManyToOne
	@JoinColumn(name="id_details",referencedColumnName="id")
	private DetailsOffre detailsOffre;
	

	public OffrePlanTarif() {
	}

public DetailsOffre getDetailsOffre() {
	return detailsOffre;
}
public void setDetailsOffre(DetailsOffre detailsOffre) {
	this.detailsOffre = detailsOffre;
}
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PlanTarifaire getPlan() {
		return plan;
	}
	public void setPlan(PlanTarifaire plan) {
		this.plan = plan;
	}
}