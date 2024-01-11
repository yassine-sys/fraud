package com.fraude.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
@Table(name="details_mail",schema="tableref")
@NamedQuery(name="DetailsMail.findAll", query="SELECT r FROM DetailsMail r")

public class DetailsMail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
public DetailsMail() {
		
	}

	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer id;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="id_regle",referencedColumnName="id")
	//@LazyCollection(LazyCollectionOption.FALSE)
	private ReglesFraude Id_Reg;
	
	
	@Column(name="adresse_mail")
	private String AdresseMail;
	
	public String getAdresseMail() {
		return AdresseMail;
	}

	public void setAdresseMail(String adresseMail) {
		AdresseMail = adresseMail;
	}

	@Column(name="type")
	private String Type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ReglesFraude getId_Reg() {
		return Id_Reg;
	}

	public void setId_Reg(ReglesFraude id_Reg) {
		Id_Reg = id_Reg;
	}



	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

}
