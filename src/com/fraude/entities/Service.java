package com.fraude.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="etl_services")
public class Service extends AbstractEntity {
	
	@OneToMany(mappedBy="service")
	private List<EtlServiceConverter> liste_convertisseur;
	
	private String code_service;
	
	private String description;
	
	
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}
	
	public List<EtlServiceConverter> getListe_convertisseur() {
		return liste_convertisseur;
	}
	public void setListe_convertisseur(List<EtlServiceConverter> liste_convertisseur) {
		this.liste_convertisseur = liste_convertisseur;
	}
	
	public void setCode_service(String code_service) {
		this.code_service = code_service;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode_service() {
		return code_service;
	}
	public String getDescription() {
		return description;
	}
	
}
