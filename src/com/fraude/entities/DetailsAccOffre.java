package com.fraude.entities;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Table;

/**
 * The persistent class for the details_acc_offres database table.
 * 
 */
@Entity
@Table(name="details_acc_offres",schema="tableref")
@NamedQuery(name="DetailsAccOffre.findAll", query="SELECT d FROM DetailsAccOffre d")
public class DetailsAccOffre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="account_id")
	private String accountId;

	@Column(name="heure_debut")
	private String heureDebut;

	@Column(name="heure_fin")
	private String heureFin;

	
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="id_details",referencedColumnName="id")
private DetailsOffre detailsOffre;
	
	
	@OneToMany(mappedBy="detailsAccOffre",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.EAGER)
	private List<DestAccOffre> listeDestinationAcc;

	

	@Column(name="minute_debut")
	private String minuteDebut;

	@Column(name="minute_fin")
	private String minuteFin;

	private BigDecimal nombre;

	@Column(name="seconde_debut")
	private String secondeDebut;

	@Column(name="seconde_fin")
	private String secondeFin;

	private String service;

	@Column(name="type_account")
	private String typeAccount;

	@Column(name="type_offre")
	private String typeOffre;

	public DetailsAccOffre() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAccountId() {
		return this.accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getHeureDebut() {
		return this.heureDebut;
	}

	public void setHeureDebut(String heureDebut) {
		this.heureDebut = heureDebut;
	}

	public String getHeureFin() {
		return this.heureFin;
	}

	public void setHeureFin(String heureFin) {
		this.heureFin = heureFin;
	}

	public DetailsOffre getDetailsOffre() {
		return detailsOffre;
	}
	public void setDetailsOffre(DetailsOffre detailsOffre) {
		this.detailsOffre = detailsOffre;
	}

	public String getMinuteDebut() {
		return this.minuteDebut;
	}

	public void setMinuteDebut(String minuteDebut) {
		this.minuteDebut = minuteDebut;
	}

	public String getMinuteFin() {
		return this.minuteFin;
	}

	public void setMinuteFin(String minuteFin) {
		this.minuteFin = minuteFin;
	}

	public BigDecimal getNombre() {
		return this.nombre;
	}

	public void setNombre(BigDecimal nombre) {
		this.nombre = nombre;
	}

	public String getSecondeDebut() {
		return this.secondeDebut;
	}

	public void setSecondeDebut(String secondeDebut) {
		this.secondeDebut = secondeDebut;
	}

	public String getSecondeFin() {
		return this.secondeFin;
	}

	public void setSecondeFin(String secondeFin) {
		this.secondeFin = secondeFin;
	}

	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getTypeAccount() {
		return this.typeAccount;
	}

	public void setTypeAccount(String typeAccount) {
		this.typeAccount = typeAccount;
	}

	public String getTypeOffre() {
		return this.typeOffre;
	}

	public void setTypeOffre(String typeOffre) {
		this.typeOffre = typeOffre;
	}

	
	public List<DestAccOffre> getListeDestinationAcc() {
		return listeDestinationAcc;
	}
	public void setListeDestinationAcc(List<DestAccOffre> listeDestinationAcc) {
		this.listeDestinationAcc = listeDestinationAcc;
	}
}