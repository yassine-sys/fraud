package com.fraude.entities;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: EtlPrincipalFields
 *
 */
@Entity
@Table(name = "etl_principal_fields")
public class EtlPrincipalFields extends AbstractEntity  {

	private static final long serialVersionUID = 1L;

	public EtlPrincipalFields() {
		super();
	}

	@ManyToOne
	@JoinColumn(name = "flow_id",referencedColumnName="id")
	private Flow flow;

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	
	

	private String description;

	private String fieldName;

	private boolean used = false;

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	private boolean specify_format;

	/*
	 * public List<Flow> getFlows() { return flows; }
	 * 
	 * public void setFlows(List<Flow> flows) { this.flows = flows; }
	 */

	public boolean isSpecify_format() {
		return specify_format;
	}

	public void setSpecify_format(boolean specify_format) {
		this.specify_format = specify_format;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
