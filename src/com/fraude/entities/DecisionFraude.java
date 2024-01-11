package com.fraude.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

import javax.persistence.Table;
/**
 * The persistent class for the decision_fraude database table.
 * 
 */
@Entity
@Table(name="decision_fraude",schema="stat")
@NamedQuery(name="DecisionFraude.findAll", query="SELECT d FROM DecisionFraude d")
public class DecisionFraude implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer id;
	
	private Integer flag;
	
	private Integer etat;
	
	@Column(name="id_regle")
	private Integer idRegle;
	
	public Integer getEtat() {
		return etat;
	}
	public void setEtat(Integer etat) {
		this.etat = etat;
	}
	
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	
	@ManyToOne
	@JoinColumn(name="id_regle",referencedColumnName="id",insertable=false,updatable=false)
	private ReglesFraude regle;
	
	
	public Integer getIdRegle() {
		return idRegle;
	}
	public void setIdRegle(Integer idRegle) {
		this.idRegle = idRegle;
	}
	
	public ReglesFraude getRegle() {
		return regle;
	}
	public void setRegle(ReglesFraude regle) {
		this.regle = regle;
	}

	@Column(name="date_decision", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp dateDecision;

	@Column(name="date_modif")
	private Timestamp dateModif;

	private String decision;

	private String msisdn;

	@Column(name="nom_utilisateur")
	private String nomUtilisateur;

	public DecisionFraude() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getDateDecision() {
		return this.dateDecision;
	}

	public void setDateDecision(Timestamp dateDecision) {
		this.dateDecision = dateDecision;
	}

	public Timestamp getDateModif() {
		return this.dateModif;
	}

	public void setDateModif(Timestamp dateModif) {
		this.dateModif = dateModif;
	}

	public String getDecision() {
		return this.decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getMsisdn() {
		return this.msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getNomUtilisateur() {
		return this.nomUtilisateur;
	}

	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

}