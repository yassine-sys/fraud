package com.fraude.entities;

import java.util.List;

import javax.annotation.Generated;
import javax.annotation.PostConstruct;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "etl_flows")
public class Flow extends AbstractEntity {

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}

	@Column(name = "name")
	@Pattern(regexp = "([a-zA-Z_]*[0-9]*)", message = "Flow name must not contain special characters or whitespaces !!")
	private String flowName;

	@Pattern(regexp = "([a-zA-Z_]*[0-9]*)", message = "Table name must not contain special characters or whitespaces !!")
	private String table_name;

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	@OneToMany(mappedBy = "flow", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<EtlFieldFlow> liste_champs;

	public List<EtlFieldFlow> getListe_champs() {
		return liste_champs;
	}

	public void setListe_champs(List<EtlFieldFlow> liste_champs) {
		this.liste_champs = liste_champs;
	}

	@OneToMany(mappedBy = "flow", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Convertisseur> liste_convertisseurs;

	public List<Convertisseur> getListe_convertisseurs() {
		return liste_convertisseurs;
	}

	public void setListe_convertisseurs(List<Convertisseur> liste_convertisseurs) {
		this.liste_convertisseurs = liste_convertisseurs;
	}

	@ManyToOne
	@JoinColumn(name = "id_type_flow", referencedColumnName = "id")
	private EtlTypeFlow flow_type;

	public EtlTypeFlow getFlow_type() {
		return flow_type;
	}

	public void setFlow_type(EtlTypeFlow flow_type) {
		this.flow_type = flow_type;
	}

	@OneToOne
	@JoinColumn(name = "id_proc", referencedColumnName = "id")
	private EtlProc proc;

	/*@OneToMany
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<EtlPrincipalFields> principalFields;

	public List<EtlPrincipalFields> getPrincipalFields() {
		return principalFields;
	}

	public void setPrincipalFields(List<EtlPrincipalFields> principalFields) {
		this.principalFields = principalFields;
	}*/

	public EtlProc getProc() {
		return proc;
	}

	public void setProc(EtlProc proc) {
		this.proc = proc;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

}
