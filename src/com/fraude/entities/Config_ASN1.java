	package com.fraude.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="etl_config_asn1")
public class Config_ASN1 extends AbstractEntity {
	
	
	@Pattern(regexp="^[0-9/]+([/]*[0-9]*)*$", message="Config cdrs must a path containing just numerics !!")
	private String location_cdrs;
	
	private int bytes_to_skip;
	
	public enum LType {
		DEFINED,INDEFINITE
	}
	
	@Enumerated(EnumType.STRING)
	private LType lengthType;
	
	
	
	public LType getLengthType() {
		return lengthType;
	}

	public void setLengthType(LType lengthType) {
		this.lengthType = lengthType;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}
	
	public String getLocation_cdrs() {
		return location_cdrs;
	}

	public void setLocation_cdrs(String location_cdrs) {
		this.location_cdrs = location_cdrs;
	}

	public int getBytes_to_skip() {
		return bytes_to_skip;
	}

	public void setBytes_to_skip(int bytes_to_skip) {
		this.bytes_to_skip = bytes_to_skip;
	}

	}
