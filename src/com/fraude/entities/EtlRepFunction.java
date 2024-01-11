package com.fraude.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "etl_rep_function")
public class EtlRepFunction extends AbstractEntity {

	private Long id_function;
	private String name;
	private Long id_rep;
	private int ordre;

	// public String getNameFunctionById(String id)
	public Long getId_function() {
		return id_function;
	}

	public void setId_function(Long id_function) {
		this.id_function = id_function;
	}

	public Long getId_rep() {
		return id_rep;
	}

	public void setId_rep(Long id_rep) {
		this.id_rep = id_rep;
	}

	public int getOrdre() {
		return ordre;
	}

	public void setOrdre(int ordre) {
		this.ordre = ordre;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "toString(): [name=" + name + ", id_function=" + id_function + ", id_rep=" + id_rep + ", ordre=" + ordre
				+ " !!!]";
	}
}
