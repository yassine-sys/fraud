package com.fraude.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import javax.persistence.Table;

/**
 * The persistent class for the serveur database table.
 * 
 */
@Entity
@Table(name="serveur",schema="stat")
@NamedQuery(name="Serveur.findAll", query="SELECT s FROM Serveur s")
public class Serveur implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id_serveur")
	private Integer idServeur;

	
	
	private BigDecimal collect;

	private String descr;

	private BigDecimal etat;

	private String hostname;

	private Integer id;

	private String ip;

	private BigDecimal nbcore;

	private String os;

	private BigDecimal rammb;

	public Serveur() {
	}

	public Integer getIdServeur() {
		return this.idServeur;
	}

	public void setIdServeur(Integer idServeur) {
		this.idServeur = idServeur;
	}

	public BigDecimal getCollect() {
		return this.collect;
	}

	public void setCollect(BigDecimal collect) {
		this.collect = collect;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public BigDecimal getEtat() {
		return this.etat;
	}

	public void setEtat(BigDecimal etat) {
		this.etat = etat;
	}

	public String getHostname() {
		return this.hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public BigDecimal getNbcore() {
		return this.nbcore;
	}

	public void setNbcore(BigDecimal nbcore) {
		this.nbcore = nbcore;
	}

	public String getOs() {
		return this.os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public BigDecimal getRammb() {
		return this.rammb;
	}

	public void setRammb(BigDecimal rammb) {
		this.rammb = rammb;
	}

}