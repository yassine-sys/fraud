package com.fraude.entities;

import java.util.List;

public class RuleRecJoin {

	private int id;
	private List<String> parenteses_ouvrant;
	private Flow Flow;
	private List<EtlFieldFlow> list_champs_rec1;
	private String operator;
	private String logic;
	private String valeur_Field;
	private List<String> parenteses_fermant;
	private String field;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<String> getParenteses_fermant() {
		return parenteses_fermant;
	}

	public void setParenteses_fermant(List<String> parenteses_fermant) {
		this.parenteses_fermant = parenteses_fermant;
	}

	public List<String> getParenteses_ouvrant() {
		return parenteses_ouvrant;
	}

	public void setParenteses_ouvrant(List<String> parenteses_ouvrant) {
		this.parenteses_ouvrant = parenteses_ouvrant;
	}

	public String getLogic() {
		return logic;
	}

	public void setLogic(String logic) {
		this.logic = logic;
	}

	public String getValeur() {
		return valeur_Field;
	}

	public void setValeur(String valeur) {
		this.valeur_Field = valeur;
	}

	public List<EtlFieldFlow> getList_champs_rec1() {
		return list_champs_rec1;
	}

	public void setList_champs_rec1(List<EtlFieldFlow> list) {
		this.list_champs_rec1 = list;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Flow getFlow() {
		return Flow;
	}

	public void setFlow(Flow flow) {
		Flow = flow;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
