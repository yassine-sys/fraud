package com.fraude.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "etl_nodes")
public class Noeud extends AbstractEntity {

	public enum CollectType {
		PUSH, PULL
	}

	public enum Status {
		Enabled, Disabled
	}
	
	@ManyToOne
	private Convertisseur converter;

	@OneToMany(mappedBy = "rep_noeud", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Repertoire> noeud_repertoires;

	@Pattern(regexp = "[a-zA-Z_]*[0-9]*", message = "Node name must not contain special characters or whitespaces !!")
	private String libelle_noeud;

	@Pattern(regexp = "[0-9]*\\.[0-9]*\\.[0-9]*\\.[0-9]*", message = "Unvalid ftp Address !!")
	private String ftpAddress;

	private String userName;
	private String password;
	private String repDistant;

	@Enumerated(EnumType.STRING)
	private CollectType collectType;

	//@Pattern(regexp = "[0-9]*", message = "deb_seq must be numeric !!")
	private int deb_seq;

	//@Pattern(regexp = "[0-9]*", message = "fin_seq must be numeric !!")
	private int fin_seq;
	
	//@Enumerated(EnumType.STRING)
	private String status; // enabled or deactivated

	

	public String getFtpAddress() {
		return ftpAddress;
	}

	public void setFtpAddress(String ftpAddress) {
		this.ftpAddress = ftpAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepDistant() {
		return repDistant;
	}

	public void setRepDistant(String repDistant) {
		this.repDistant = repDistant;
	}

	public int getDeb_seq() {
		return deb_seq;
	}

	public void setDeb_seq(int deb_seq) {
		this.deb_seq = deb_seq;
	}

	public int getFin_seq() {
		return fin_seq;
	}

	public void setFin_seq(int fin_seq) {
		this.fin_seq = fin_seq;
	}

	public CollectType getCollectType() {
		return collectType;
	}

	public void setCollectType(CollectType collectType) {
		this.collectType = collectType;
	}

	public String getLibelle_noeud() {
		return libelle_noeud;
	}

	public void setLibelle_noeud(String libelle_noeud) {
		this.libelle_noeud = libelle_noeud;
	}

	@Override
	public String toString() {
		return libelle_noeud;
	}

	public List<Repertoire> getNoeud_repertoires() {
		return noeud_repertoires;
	}

	public void setNoeud_repertoires(List<Repertoire> noeud_repertoires) {
		this.noeud_repertoires = noeud_repertoires;
	}
	
	public Convertisseur getConverter() {
		return converter;
	}

	public void setConverter(Convertisseur converter) {
		this.converter = converter;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
