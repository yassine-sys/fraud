package com.fraude.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "etl_rep_sous_module")
public class RepSubModule extends AbstractEntity{
	
	@Column(name="nom_sous_module")
	private String repSubModuleName;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id",referencedColumnName="id")
	private RepModule repModule;

	@OneToMany(mappedBy = "repSubmodule",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private List<Function> list_functions;
	
	@ManyToMany(mappedBy="sub_module_groups")
	private List<GuiGroup> sub_module_groups;
	
	@Column
	private String path;
	
	
	public String getRepSubModuleName() {
		return repSubModuleName;
	}

	public void setRepSubModuleName(String repSubModuleName) {
		this.repSubModuleName = repSubModuleName;
	}

	public RepModule getRepModule() {
		return repModule;
	}

	public void setRepModule(RepModule repModule) {
		this.repModule = repModule;
	}

	public List<Function> getList_functions() {
		return list_functions;
	}

	public void setList_functions(List<Function> list_functions) {
		this.list_functions = list_functions;
	}

	public List<GuiGroup> getSub_module_groups() {
		return sub_module_groups;
	}

	public void setSub_module_groups(List<GuiGroup> sub_module_groups) {
		this.sub_module_groups = sub_module_groups;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	

}
