package com.fraude.entities;

import java.io.Serializable;
import javax.persistence.*;

import javax.persistence.Table;
/**
 * The persistent class for the flux_cdrs database table.
 * 
 */
@Entity
@Table(name="flux_cdrs",schema="tableref")
@NamedQuery(name="FluxCdr.findAll", query="SELECT f FROM FluxCdr f")
public class FluxCdr implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer id;

	private String nom;

	public FluxCdr() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}