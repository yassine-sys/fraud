package com.fraude.entities;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "etl_rep_module")
public class RepModule extends AbstractEntity {

	// @Column(name="nom_module")
	private String moduleName;

	@OneToMany(mappedBy = "repModule", cascade = CascadeType.ALL)
	private List<System> list_systems;

	@OneToMany(mappedBy = "repModule", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<RepSubModule> list_sub_modules;

	@ManyToMany(mappedBy = "module_groups")
	private List<GuiGroup> group_module;

	public List<GuiGroup> getGroup_module() {
		return group_module;
	}

	public void setGroup_module(List<GuiGroup> group_module) {
		this.group_module = group_module;
	}

	public List<System> getList_systems() {
		return list_systems;
	}

	public void setList_systems(List<System> list_systems) {
		this.list_systems = list_systems;
	}

	public List<RepSubModule> getList_sub_modules() {
		return list_sub_modules;
	}

	public void setList_sub_modules(List<RepSubModule> list_sub_modules) {
		this.list_sub_modules = list_sub_modules;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
}
