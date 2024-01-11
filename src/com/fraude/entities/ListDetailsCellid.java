package com.fraude.entities;
import javax.persistence.Table;
import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the list_details_cellid database table.
 * 
 */
@Entity
@Table(name="list_details_cellid",schema="tableref")
@NamedQuery(name="ListDetailsCellid.findAll", query="SELECT l FROM ListDetailsCellid l")
public class ListDetailsCellid implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="date_modif")
	private Timestamp dateModif;

	private String hotlistnumber;
	
	@ManyToOne
	@JoinColumn(name="id_hotlist",referencedColumnName="id")
	private ListCellid cellId;

	@Column(name="nom_utlisateur")
	private String nomUtlisateur;

	public ListDetailsCellid() {
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

public ListCellid getCellId() {
	return cellId;
}
public void setCellId(ListCellid cellId) {
	this.cellId = cellId;
}
	public String getNomUtlisateur() {
		return this.nomUtlisateur;
	}

	public void setNomUtlisateur(String nomUtlisateur) {
		this.nomUtlisateur = nomUtlisateur;
	}

}