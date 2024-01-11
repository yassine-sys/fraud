package com.fraude.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "etl_field_service")
public class EtlFieldService extends AbstractEntity {

	private String nom_champ;

	private boolean applicable;
	
	@Pattern(regexp="([0-9]*)", message="Position of field must be numeric !!")
	private String position;

	@Pattern(regexp="^[0-9]+([/]*[0-9]*)*$", message="Tag must be numeric or a path containing just numerics !!")
	private String tag;

	@ManyToOne
	@JoinColumn(name = "id_service_conveter", referencedColumnName = "id")
	private EtlServiceConverter service;

	@ManyToOne
	@JoinColumn(name = "id_field", referencedColumnName = "id")
	private EtlFieldFlow field;

	@ManyToOne
	@JoinColumn(name = "id_tbin", referencedColumnName = "id")
	private EtlBinType type;

	public boolean isApplicable() {
		return applicable;
	}

	public void setApplicable(boolean applicable) {
		this.applicable = applicable;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}

	public void setNom_champ(String nom_champ) {
		this.nom_champ = nom_champ;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getNom_champ() {
		return nom_champ;
	}

	public EtlServiceConverter getService() {
		return service;
	}

	public void setService(EtlServiceConverter service) {
		this.service = service;
	}

	public String getTag() {
		return tag;
	}

	public EtlBinType getType() {
		return type;
	}

	public void setType(EtlBinType type) {
		this.type = type;
	}

	public EtlFieldFlow getField() {
		return field;
	}

	public void setField(EtlFieldFlow field) {
		this.field = field;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
}
