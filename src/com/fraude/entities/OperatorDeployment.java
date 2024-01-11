package com.fraude.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue; 
import javax.persistence.GenerationType;
import javax.persistence.Id; 
import javax.persistence.Table; 

@Entity
@Table(schema = "tableref")
public class OperatorDeployment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id ;
	private String name_operator;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName_operator() {
		return name_operator;
	}
	public void setName_operator(String name_operator) {
		this.name_operator = name_operator;
	}
	
}
