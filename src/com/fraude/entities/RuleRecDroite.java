package com.fraude.entities;

import java.util.List;

public class RuleRecDroite {

	
	
	private List<String> parenteses;
	
	
	public List<String> getParenteses() {
		return parenteses;
	}
	public void setParenteses(List<String> parenteses) {
		this.parenteses = parenteses;
	}
	
	
	private String logic;
	
	public String getLogic() {
		return logic;
	}
	public void setLogic(String logic) {
		this.logic = logic;
	}
	private List<ChampRec> list_champs_rec1;
	
	private String operator;
	
	
	
	
	
	public List<ChampRec> getList_champs_rec1() {
		return list_champs_rec1;
	}
	public void setList_champs_rec1(List<ChampRec> list_champs_rec1) {
		this.list_champs_rec1 = list_champs_rec1;
	}

	
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	
}
