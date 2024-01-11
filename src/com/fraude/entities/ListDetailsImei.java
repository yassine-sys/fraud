package com.fraude.entities;
import javax.persistence.Table;
import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the list_details_imei database table.
 * 
 */
@Entity
@Table(name="list_details_imei",schema="tableref")
@NamedQuery(name="ListDetailsImei.findAll", query="SELECT l FROM ListDetailsImei l")
public class ListDetailsImei implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	
	
	

	@Column(name="date_modif")
	private Timestamp dateModif;

	private String hotlistnumber;

	@ManyToOne
	@JoinColumn(name="id_hotlist",referencedColumnName="id")
	private ListImei imei;

	@Column(name="nom_utilisateur")
	private String nomUtilisateur;

	public ListDetailsImei() {
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

	public ListImei getImei() {
		return imei;
	}
	public void setImei(ListImei imei) {
		this.imei = imei;
	}
	public String getNomUtilisateur() {
		return this.nomUtilisateur;
	}

	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

	
	
}