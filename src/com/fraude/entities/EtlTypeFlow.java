package com.fraude.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "etl_type_flows")
public class EtlTypeFlow implements Serializable {

	@Id
	private Integer id;

	private String type_flow;

	public Integer getId() {
		return id;
	}

	public void setType_flow(String type_flow) {
		this.type_flow = type_flow;
	}

	public String getType_flow() {
		return type_flow;
	}
	
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this.id == null) {
			return false;
		}

		if (obj instanceof EtlTypeFlow && obj.getClass().equals(getClass())) {
			return this.id.equals(((EtlTypeFlow) obj).id);
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hash = 5;
		hash = 43 * hash + Objects.hashCode(this.id);
		return hash;
	}

}
