package com.fraude.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "etl_system")
public class System extends AbstractEntity{

	private String systemName;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
	private RepModule repModule;

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
}
