package com.fraude.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="etl_job_flows")
public class EtlJobFlow extends AbstractEntity{

	@OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(name = "flow_id")
	private Flow flow;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private EtlJob job;
	
	private int ordre;
	

    @Override
    public void setId(Long id) {
        super.setId(id);
    }
    
	public EtlJob getJob() {
		return job;
	}

	public void setJob(EtlJob job) {
		this.job = job;
	}

	public Flow getFlow() {
		return flow;
	}

	public int getOrdre() {
		return ordre;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public void setOrdre(int ordre) {
		this.ordre = ordre;
	}
}
