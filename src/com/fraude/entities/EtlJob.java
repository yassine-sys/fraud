package com.fraude.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "etl_jobs")
public class EtlJob extends AbstractEntity {

	private String jobName;
	
	private String description;
	
	@OneToMany(mappedBy = "job", 
	        cascade = CascadeType.ALL, 
	        orphanRemoval = true)
	private List<EtlJobFlow> listJobFlows = new ArrayList<>();

	public String getJobName() {
		return jobName;
	}

	public String getDescription() {
		return description;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
