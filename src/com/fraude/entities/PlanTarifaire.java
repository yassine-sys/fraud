package com.fraude.entities;
import java.io.Serializable;

import javax.persistence.*;
import javax.persistence.Table;
import java.sql.Timestamp;


/**
 * The persistent class for the plan_tarifaire database table.
 * 
 */
@Entity
@Table(name="plan_tarifaire",schema="tableref")
@NamedQuery(name="PlanTarifaire.findAll", query="SELECT p FROM PlanTarifaire p")
public class PlanTarifaire implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="code_plan_tarifaire")
	private String codePlanTarifaire;

	@Column(name="date_modif")
	private Timestamp dateModif;

	@Column(name="nom_utilisateur")
	private String nomUtilisateur;

	@Column(name="plan_tarifaire")
	@ToUse
	private String planTarifaire;

	private String type;

	public PlanTarifaire() {
	}

	public String getCodePlanTarifaire() {
		return this.codePlanTarifaire;
	}

	public void setCodePlanTarifaire(String codePlanTarifaire) {
		this.codePlanTarifaire = codePlanTarifaire;
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

	public String getPlanTarifaire() {
		return this.planTarifaire;
	}

	public void setPlanTarifaire(String planTarifaire) {
		this.planTarifaire = planTarifaire;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((codePlanTarifaire == null) ? 0 : codePlanTarifaire
						.hashCode());
		result = prime * result
				+ ((dateModif == null) ? 0 : dateModif.hashCode());
		result = prime * result
				+ ((nomUtilisateur == null) ? 0 : nomUtilisateur.hashCode());
		result = prime * result
				+ ((planTarifaire == null) ? 0 : planTarifaire.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		PlanTarifaire other = (PlanTarifaire) obj;
		if (codePlanTarifaire == null) {
			if (other.codePlanTarifaire != null)
				return false;
		} else if (!codePlanTarifaire.equals(other.codePlanTarifaire))
			return false;
		if (dateModif == null) {
			if (other.dateModif != null)
				return false;
		} else if (!dateModif.equals(other.dateModif))
			return false;
		if (nomUtilisateur == null) {
			if (other.nomUtilisateur != null)
				return false;
		} else if (!nomUtilisateur.equals(other.nomUtilisateur))
			return false;
		if (planTarifaire == null) {
			if (other.planTarifaire != null)
				return false;
		} else if (!planTarifaire.equals(other.planTarifaire))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
	
	

}