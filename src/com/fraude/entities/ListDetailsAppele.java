package com.fraude.entities;
import javax.persistence.Table;
import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the list_details_appele database table.
 * 
 */
@Entity
@Table(name="list_details_appele",schema="tableref")
@NamedQuery(name="ListDetailsAppele.findAll", query="SELECT l FROM ListDetailsAppele l")
public class ListDetailsAppele implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="date_modif")
	private Timestamp dateModif;

	private String hotlistnumber;
	
	@ManyToOne
	@JoinColumn(name="id_hotlist",referencedColumnName="id")
	private ListAppele appele;

	@Column(name="nom_utilisateur")
	private String nomUtilisateur;

	public ListDetailsAppele() {
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

	public String getHotlistnumber() {
		return this.hotlistnumber;
	}

	public void setHotlistnumber(String hotlistnumber) {
		this.hotlistnumber = hotlistnumber;
	}

	public ListAppele getAppele() {
		return appele;
	}
	public void setAppele(ListAppele appele) {
		this.appele = appele;
	}
	public String getNomUtilisateur() {
		return this.nomUtilisateur;
	}

	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

}