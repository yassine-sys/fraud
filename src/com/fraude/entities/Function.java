package com.fraude.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fraude.entities.GuiGroup;

@Entity
@Table(name = "etl_rep_functions")
public class Function extends AbstractEntity {

	private String functionName;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submodule_id")
	private RepSubModule repSubmodule;

	@OneToMany(mappedBy = "function",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private List<Report> list_reports;
	
	@ManyToMany(mappedBy="function_groups")
	private List<GuiGroup> function_groups;
	
	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public RepSubModule getRepSubmodule() {
		return repSubmodule;
	}

	public void setRepSubmodule(RepSubModule repSubmodule) {
		this.repSubmodule = repSubmodule;
	}

	public List<Report> getList_reports() {
		return list_reports;
	}

	public void setList_reports(List<Report> list_reports) {
		this.list_reports = list_reports;
	}

	public List<GuiGroup> getFunction_groups() {
		return function_groups;
	}

	public void setFunction_groups(List<GuiGroup> function_groups) {
		this.function_groups = function_groups;
	}
}
