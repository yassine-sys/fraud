package com.fraude.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fraude.entities.FiltresFraude;
import com.fraude.entities.ReglesFraude;

/**
 * The persistent class for the filtres_regles_fraudes database table.
 * 
 */
@Entity
@Table(name = "filtres_regles_fraudes", schema = "tableref")
@NamedQuery(name = "FiltresReglesFraude.findAll", query = "SELECT f FROM FiltresReglesFraude f")
public class FiltresReglesFraude implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "date_modif")
	private Timestamp dateModif;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_filtre", referencedColumnName = "id")
	private FiltresFraude filtreFraude;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_regle", referencedColumnName = "id")
	private ReglesFraude regle;

	private String inegal;

	@Column(name = "nom_utlisateur")
	private String nomUtlisateur;

	private String vdef;

	private String vegal;

	private String vlike;

	private String vnotlike;

	public FiltresReglesFraude() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getDateModif() {
		return this.dateModif;
	}

	public void setDateModif(Timestamp dateModif) {
		this.dateModif = dateModif;
	}

	public FiltresFraude getFiltreFraude() {
		return filtreFraude;
	}

	public void setFiltreFraude(FiltresFraude filtreFraude) {
		this.filtreFraude = filtreFraude;
	}

	public ReglesFraude getRegle() {
		return regle;
	}

	public void setRegle(ReglesFraude regle) {
		this.regle = regle;
	}

	public String getInegal() {
		return this.inegal;
	}

	public void setInegal(String inegal) {
		this.inegal = inegal;
	}

	public String getNomUtlisateur() {
		return this.nomUtlisateur;
	}

	public void setNomUtlisateur(String nomUtlisateur) {
		this.nomUtlisateur = nomUtlisateur;
	}

	public String getVdef() {
		return this.vdef;
	}

	public void setVdef(String vdef) {
		this.vdef = vdef;
	}

	public String getVegal() {
		return this.vegal;
	}

	public void setVegal(String vegal) {
		this.vegal = vegal;
	}

	public String getVlike() {
		return this.vlike;
	}

	public void setVlike(String vlike) {
		this.vlike = vlike;
	}

	public String getVnotlike() {
		return this.vnotlike;
	}

	public void setVnotlike(String vnotlike) {
		this.vnotlike = vnotlike;
	}

}