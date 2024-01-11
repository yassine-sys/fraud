package com.fraude.entities;

import java.io.Serializable;
import javax.persistence.*;

import javax.persistence.Table;

/**
 * The persistent class for the dest_acc_offre database table.
 * 
 */
@Entity
@Table(name = "dest_acc_offre", schema = "tableref")
@NamedQuery(name = "DestAccOffre.findAll", query = "SELECT d FROM DestAccOffre d")
public class DestAccOffre implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_dest", referencedColumnName = "id")
	private Destination destinationAcc;

	@ManyToOne
	@JoinColumn(name = "id_det_acc_off", referencedColumnName = "id")
	private DetailsAccOffre detailsAccOffre;

	public DestAccOffre() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Destination getDestinationAcc() {
		return destinationAcc;
	}

	public void setDestinationAcc(Destination destinationAcc) {
		this.destinationAcc = destinationAcc;
	}

	public DetailsAccOffre getDetailsAccOffre() {
		return detailsAccOffre;
	}

	public void setDetailsAccOffre(DetailsAccOffre detailsAccOffre) {
		this.detailsAccOffre = detailsAccOffre;
	}
}