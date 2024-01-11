package com.fraude.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="sub_group_tab_selected",schema="tableref")
public class SubGroupTabSelected {
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public SubModuleGroup getSubgroup() {
		return subgroup;
	}

	public void setSubgroup(SubModuleGroup subgroup) {
		this.subgroup = subgroup;
	}

	public GuiGroup getGroup() {
		return group;
	}

	public void setGroup(GuiGroup group) {
		this.group = group;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="gui_tab_group_id",referencedColumnName="id")
	private SubModuleGroup gui_tab;
	
	@ManyToOne
	@JoinColumn(name="sub_group_id",referencedColumnName="id")
	private SubModuleGroup subgroup;
	
	@ManyToOne
	@JoinColumn(name="group_id",referencedColumnName="g_id")
	private GuiGroup group;
	
	
	

}
