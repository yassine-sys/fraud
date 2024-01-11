package com.fraude.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;


@Entity
@Table(name="etl_function")
public class EtlFunction extends AbstractEntity {

	@Pattern(regexp="([a-zA-Z]*[0-9]*)", message="Function name must not contain special characters or whitespaces !!")
	private String name;
	
	@Pattern(regexp="([a-zA-Z_]*)", message="Function type must be alphabetic !!")
	private String type_function;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType_function() {
		return type_function;
	}
	public void setType_function(String type_function) {
		this.type_function = type_function;
	}
}
