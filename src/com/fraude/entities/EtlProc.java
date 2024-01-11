package com.fraude.entities;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name="etl_procs")
public class EtlProc  extends AbstractEntity {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4399969592710771491L;

	private String name;
	
	private String proc_type;
	
	private String query_join;
	
	private Boolean time_consideration;
	
	private Boolean is_table; 
	
	
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}
	
	@Override
	protected void setId(Long id) {
		// TODO Auto-generated method stub
		super.setId(id);
	}
	
	public String getName() {
		return name;
	}
	public String getProc_type() {
		return proc_type;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getQuery_join() {
		return query_join;
	}
	public void setProc_type(String proc_type) {
		this.proc_type = proc_type;
	}
	
	public void setQuery_join(String query_join) {
		this.query_join = query_join;
	}

	public Boolean getTime_consideration() {
		return time_consideration;
	}

	public void setTime_consideration(Boolean time_consideration) {
		this.time_consideration = time_consideration;
	}

	public Boolean getIs_table() {
		return is_table;
	}

	public void setIs_table(Boolean is_table) {
		this.is_table = is_table;
	}

}
