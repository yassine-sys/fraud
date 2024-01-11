package com.fraude.entities;

import java.util.List;

public class ChampRec {

	private EtlFieldFlow id_field_dest_another;

	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private List<String> fonctions;

	private EtlFieldFlow champ;

	private String operator;

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public EtlFieldFlow getChamp() {
		return champ;
	}

	public void setChamp(EtlFieldFlow champ) {
		this.champ = champ;
	}

	public List<String> getFonctions() {
		return fonctions;
	}

	public void setFonctions(List<String> fonctions) {
		this.fonctions = fonctions;
	}

	public EtlFieldFlow getId_field_dest_another() {
		return id_field_dest_another;
	}

	public void setId_field_dest_another(EtlFieldFlow id_field_dest_another) {
		this.id_field_dest_another = id_field_dest_another;
	}
}
