package com.fraude.entities;

import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.Table;

/**
 * The persistent class for the filtres_fraudes database table.
 * 
 */
@Entity
@Table(name="filtres_fraudes",schema="tableref")
@NamedQuery(name="FiltresFraude.findAll", query="SELECT f FROM FiltresFraude f")
public class FiltresFraude implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String filtre;

	public FiltresFraude() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFiltre() {
		return this.filtre;
	}

	public void setFiltre(String filtre) {
		this.filtre = filtre;
	}

}