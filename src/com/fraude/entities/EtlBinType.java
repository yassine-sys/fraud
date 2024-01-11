package com.fraude.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="etl_bin_type")
public class EtlBinType implements Serializable {

	
	@Id
	private int id;
	
	private String type_bin;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType_bin() {
		return type_bin;
	}
	public void setType_bin(String type_bin) {
		this.type_bin = type_bin;
	}
	
}
