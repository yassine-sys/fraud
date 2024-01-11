package com.fraude.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "etl_field_proc")
public class EtlFieldProc extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3361724959963775897L;

	@ManyToOne
	@JoinColumn(name = "id_proc_flow", referencedColumnName = "id")
	private EtlProcFlow proc_flow;

	@ManyToOne
	@JoinColumn(name = "id_field_origine", referencedColumnName = "id")
	private EtlFieldFlow field;

	public EtlFieldFlow getField() {
		return field;
	}

	public void setField(EtlFieldFlow field) {
		this.field = field;
	}

	@Column(name = "id_field_dest")
	private Long id_field_dest;

	private String function;

	private String type_field;

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

	public Long getId_field_dest() {
		return id_field_dest;
	}
	public void setId_field_dest(Long id_field_dest) {
		this.id_field_dest = id_field_dest;
	}
	public void setFunction(String function) {
		this.function = function;
	}

	public void setProc_flow(EtlProcFlow proc_flow) {
		this.proc_flow = proc_flow;
	}

	public void setType_field(String type_field) {
		this.type_field = type_field;
	}

	
	public String getFunction() {
		return function;
	}

	public EtlProcFlow getProc_flow() {
		return proc_flow;
	}

	public String getType_field() {
		return type_field;
	}


}
