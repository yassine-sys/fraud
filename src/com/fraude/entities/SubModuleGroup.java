package com.fraude.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="sub_module_group",schema="tableref")
public class SubModuleGroup implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private int id;
	
	
	
	private String sub_name;
	
	public String getSub_name() {
		return sub_name;
	}
	public void setSub_name(String sub_name) {
		this.sub_name = sub_name;
	}
	
	@ManyToOne
	@JoinColumn(name="gui_module_group_id",referencedColumnName="id")
	private SubModuleGroup gui_module_group;
	
	
	public SubModuleGroup getGui_module_group() {
		return gui_module_group;
	}
	public void setGui_module_group(SubModuleGroup gui_module_group) {
		this.gui_module_group = gui_module_group;
	}

	public int getId() {
		return id;
	}
	
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	
 	
	
	
}
