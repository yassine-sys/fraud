package com.fraude.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="Non_Frodulent_seq",schema="stat")
public class Non_Frodulent_seq implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer id;
	
	private Integer flag;
	
	private Integer etat;
	
	@ManyToOne
	@JoinColumn(name="id_regle",referencedColumnName="id")
	private ReglesFraude regle;
	
	
	@Column(name="date_decision", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp dateDecision;

	@Column(name="date_modif")
	private Timestamp dateModif;

	private String decision;

	private String msisdn;

	@Column(name="nom_utilisateur")
	private String nomUtilisateur;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getEtat() {
		return etat;
	}

	public void setEtat(Integer etat) {
		this.etat = etat;
	}

	public ReglesFraude getRegle() {
		return regle;
	}

	public void setRegle(ReglesFraude regle) {
		this.regle = regle;
	}

	public Timestamp getDateDecision() {
		return dateDecision;
	}

	public void setDateDecision(Timestamp dateDecision) {
		this.dateDecision = dateDecision;
	}

	public Timestamp getDateModif() {
		return dateModif;
	}

	public void setDateModif(Timestamp dateModif) {
		this.dateModif = dateModif;
	}

	public String getDecision() {
		return decision;
	}

	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getNomUtilisateur() {
		return nomUtilisateur;
	}

	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

	
	
	
	
	
}
