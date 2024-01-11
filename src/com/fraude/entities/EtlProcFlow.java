package com.fraude.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "etl_proc_flow")
public class EtlProcFlow extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4005202420662822093L;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_proc", referencedColumnName = "id")
	private EtlProc proc;
	
	@ManyToOne
	@JoinColumn(name = "id_flow", referencedColumnName = "id")
	private Flow flow;

	@OneToMany(mappedBy = "proc_flow", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<EtlFieldProc> list_fields;

	
	
	public List<EtlFieldProc> getList_fields() {
		return list_fields;
	}

	public void setList_fields(List<EtlFieldProc> list_fields) {
		this.list_fields = list_fields;
	}

	private String query_filters;

	public String getQuery_filters() {
		return query_filters;
	}

	public void setQuery_filters(String query_filters) {
		this.query_filters = query_filters;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}
	

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		super.setId(id);
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public void setProc(EtlProc proc) {
		this.proc = proc;
	}

	public Flow getFlow() {
		return flow;
	}

	public EtlProc getProc() {
		return proc;
	}

}
