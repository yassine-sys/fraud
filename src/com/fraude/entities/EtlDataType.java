package com.fraude.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="etl_data_type")
public class EtlDataType {
	
	@Id
	private int id;
	
	private String type_data;
	
	//private String cassandra_type;
	
	private boolean withLength;
	private boolean withPrecision;
	
	public boolean getWithLength() {
		return withLength;
	}
	public void setWithLength(boolean withLength) {
		this.withLength = withLength;
	}
	public boolean getWithPrecision() {
		return withPrecision;
	}
	public void setWithPrecision(boolean withPrecision) {
		this.withPrecision = withPrecision;
	}
	/*public String getCassandra_type() {
		return cassandra_type;
	}
	public void setCassandra_type(String cassandra_type) {
		this.cassandra_type = cassandra_type;
	}*/

	public String getType_data() {
		return type_data;
	}
	public void setType_data(String type_data) {
		this.type_data = type_data;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
}

