package com.fraude.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fraude.entities.FiltresReglesFraude;

import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the regles_fraudes database table.
 * 
 */
@Entity
@Table(name = "regles_fraudes", schema = "tableref")
@NamedQuery(name = "ReglesFraude.findAll", query = "SELECT r FROM ReglesFraude r")
public class ReglesFraude implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "date_modif")
	private Timestamp dateModif;

	private String description;

	private String etat;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "regle", cascade = CascadeType.PERSIST, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<ParametresReglesFraude> liste_parameters;

	@OneToMany(mappedBy = "regle", cascade = CascadeType.PERSIST, orphanRemoval = true)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<FiltresReglesFraude> liste_filters;

	public List<FiltresReglesFraude> getListe_filters() {
		return liste_filters;
	}

	public void setListe_filters(List<FiltresReglesFraude> filters) {
		this.liste_filters = (List<FiltresReglesFraude>) filters;
	}

	public List<ParametresReglesFraude> getListe_parameters() {
		return liste_parameters;
	}

	public void setListe_parameters(List<ParametresReglesFraude> params) {
		this.liste_parameters = (List<ParametresReglesFraude>) params;
	}

    @ManyToOne
	@JoinColumn(name = "id_categorie", referencedColumnName = "id")
	private CategoriesFraude categorie;

    @ManyToOne
	@JoinColumn(name = "id_flux", referencedColumnName = "id")
	private Flow flux;
    
    private Integer time_window;
    
    

	public Integer getTime_window() {
		return time_window;
	}

	public void setTime_window(Integer time_window) {
		this.time_window = time_window;
	}

	public Flow getFlux() {
		return flux;
	}
	public void setFlux(Flow flux) {
		this.flux = flux;
	}

	public CategoriesFraude getCategorie() {
		return categorie;
	}

	public void setCategorie(CategoriesFraude categorie) {
		this.categorie = categorie;
	}

	private String nom;

	@Column(name = "nom_utilisateur")
	private String nomUtilisateur;

	private String type;

	public ReglesFraude() {
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEtat() {
		return this.etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNomUtilisateur() {
		return this.nomUtilisateur;
	}

	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}