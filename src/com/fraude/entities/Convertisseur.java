package com.fraude.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "etl_converters")
public class Convertisseur extends AbstractEntity {

	@OneToMany(mappedBy = "convertisseur")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<EtlServiceConverter> liste_service;

	@OneToMany(mappedBy = "converter")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Noeud> list_noeud;

	@ManyToOne
	@JoinColumn(name = "id_flow", referencedColumnName = "id")
	private Flow flow;

	@Column(name = "name")
	@Pattern(regexp = "[a-zA-Z_0-9]*", message = "Converter name must not contain special characters or whitespaces !!")
	private String libelle;

	@ManyToOne(cascade = CascadeType.ALL)
	private Config_ASCII config_ascii;

	@ManyToOne(cascade = CascadeType.ALL)
	private Config_ASN1 config_asn1;

	private String fournisseur;

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getFournisseur() {
		return fournisseur;
	}

	public void setFournisseur(String fournisseur) {
		this.fournisseur = fournisseur;
	}

	public Config_ASCII getConfig_ascii() {
		return config_ascii;
	}

	public void setConfig_ascii(Config_ASCII config_ascii) {
		this.config_ascii = config_ascii;
	}

	public void setConfig_asn1(Config_ASN1 config_asn1) {
		this.config_asn1 = config_asn1;
	}

	public Config_ASN1 getConfig_asn1() {
		return config_asn1;
	}

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public void setList_noeud(List<Noeud> list_noeud) {
		this.list_noeud = list_noeud;
	}
	
	public List<EtlServiceConverter> getListe_service() {
		return liste_service;
	}

	public void setListe_service(List<EtlServiceConverter> liste_service) {
		this.liste_service = liste_service;
	}

	public List<Noeud> getList_noeud() {
		return list_noeud;
	}
}
