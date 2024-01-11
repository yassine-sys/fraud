package com.fraude.entities;

import java.util.List;

public class FiltreRec {

	
	private List<String> parenteses_ouvrant;
	
	
	public List<String> getParenteses_ouvrant() {
		return parenteses_ouvrant;
	}
	public void setParenteses_ouvrant(List<String> parenteses_ouvrant) {
		this.parenteses_ouvrant = parenteses_ouvrant;
	}
	
	private List<String> parenteses_fermant;
	
	public List<String> getParenteses_fermant() {
		return parenteses_fermant;
	}
	public void setParenteses_fermant(List<String> parenteses_fermant) {
		this.parenteses_fermant = parenteses_fermant;
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
	
	
	private String value;
	
	
	
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
