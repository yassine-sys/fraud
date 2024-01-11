package com.fraude.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="etl_service_converters")
public class EtlServiceConverter extends AbstractEntity {

	
	@ManyToOne
	@JoinColumn(name="id_convertor",referencedColumnName="id")
	private Convertisseur convertisseur;
	
	@Column(name="tag")
	
	@Pattern(regexp="([0-9]*)", message="Tag of service must be numeric !!")
	private String tag_service;
	
	
	public String getTag_service() {
		return tag_service;
	}
	public void setTag_service(String tag_service) {
		this.tag_service = tag_service;
	}
	
	@ManyToOne
	@JoinColumn(name="id_service",referencedColumnName="id")
	private Service service;
	
	@OneToMany(mappedBy="service",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private List<EtlFieldService> liste_champs;
	
	
	public List<EtlFieldService> getListe_champs() {
		return liste_champs;
	}
	public void setListe_champs(List<EtlFieldService> liste_champs) {
		this.liste_champs = liste_champs;
	}
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}
	
	
	
	public Convertisseur getConvertisseur() {
		return convertisseur;
	}
	public void setService(Service service) {
		this.service = service;
	}
	
	public Service getService() {
		return service;
	}
	public void setConvertisseur(Convertisseur convertisseur) {
		this.convertisseur = convertisseur;
	}
	
	
}
