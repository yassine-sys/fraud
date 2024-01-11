package com.fraude.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


/**
 * The persistent class for the gui_tabs_groups database table.
 * 
 */
@Entity
@Table(name="etl_Reporting_module_groupe",schema="tableref")
@NamedQuery(name="GuiModulesGroup.findAll", query="SELECT g FROM GuiModulesGroup g")
public class GuiModulesGroup implements Serializable {
	
	
	private static final long serialVersionUID = 1L;




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tabName == null) ? 0 : tabName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GuiModulesGroup other = (GuiModulesGroup) obj;
		if (tabName == null) {
			if (other.tabName != null)
				return false;
		} else if (!tabName.equals(other.tabName))
			return false;
		return true;
	}


	@Id
	private Integer id;

	
	@ManyToMany(mappedBy="module_groups")
	private List<GuiGroup> group_module;
	

	@OneToMany(mappedBy="gui_module_group")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<SubModuleGroup> liste_subs_module_group;
	
	
	
	

	

	public List<GuiGroup> getGroup_module() {
		return group_module;
	}
	public void setGroup_module(List<GuiGroup> group_module) {
		this.group_module = group_module;
	}


	@Column(name="tab_name")
	private String tabName;

	public GuiModulesGroup() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public String getTabName() {
		return this.tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}

}