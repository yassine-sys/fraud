package com.fraude.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name="etl_repository")
public class Repertoire extends AbstractEntity {

	private static final long serialVersionUID = -85736906448247608L;

	@Column(name="Rep_name")
	@Pattern(regexp="[a-zA-Z_]*[0-9]*", message="Repository name must not contain special characters or whitespaces !!")
	private String nameRep;

	@Pattern(regexp="[a-zA-Z_]*", message="Pattern is invalid !!")
	private String pattern;

	@Column(name = "fils_number")
	private int nb_fils;

	/*@OneToMany(mappedBy = "repertoire")
	private List<Adresse_Ftp> liste_addresse;

	public List<Adresse_Ftp> getListe_addresse() {
		return liste_addresse;
	}

	public void setListe_addresse(List<Adresse_Ftp> liste_addresse) {
		this.liste_addresse = liste_addresse;
	}*/

	@ManyToOne
	@JoinColumn(name="id_noeud",referencedColumnName = "id")
	private Noeud rep_noeud;

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Noeud getRep_noeud() {
		return rep_noeud;
	}

	public void setRep_noeud(Noeud rep_noeud) {
		this.rep_noeud = rep_noeud;
	}

	public String getNameRep() {
		return nameRep;
	}

	public void setNameRep(String nameRep) {
		this.nameRep = nameRep;
	}
	
	public int getNb_fils() {
		return nb_fils;
	}

	public void setNb_fils(int nb_fils) {
		this.nb_fils = nb_fils;
	}

}
