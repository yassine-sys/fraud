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





@Entity
@Table(name="details_sms",schema="tableref")
@NamedQuery(name="DetailsSms.findAll", query="SELECT r FROM DetailsSms r")

public class DetailsSms implements Serializable{

	
	
	 
		private static final long serialVersionUID = 1L;
		
		
		
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private Integer id;
		
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name="id_regle",referencedColumnName="id")
		private ReglesFraude Id_Reg;
		
		
		@Column(name="msisdn")
		private String Msisdn;
		
	public String getMsisdn() {
		return Msisdn;
	}
	public void setMsisdn(String msisdn) {
		Msisdn = msisdn;
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
