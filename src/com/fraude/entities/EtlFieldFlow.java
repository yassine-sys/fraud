package com.fraude.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "etl_field_flow")
public class EtlFieldFlow extends AbstractEntity {

	/*
	 * public enum Type_source{ from_conv,from_proc }
	 * 
	 * @Enumerated(EnumType.STRING)
	 * 
	 * @Column(name="source") private Type_source type;
	 */
	@Column(name = "name_base")
	@Pattern(regexp = "([a-zA-Z_]*[0-9]*)", message = "Field name must not contain special characters or whitespaces !!")
	private String libelle;

	private String format;

	@ManyToOne
	@JoinColumn(name = "id_data_type", referencedColumnName = "id")
	private EtlDataType data_type;
	
	
	@Column(name="is_date")
	private boolean is_date;
	
	public boolean isIsdate() {
		return is_date;
	}

	public void setIsdate(boolean isdate) {
		this.is_date = isdate;
	}
	
	public EtlDataType getData_type() {
		return data_type;
	}

	public void setData_type(EtlDataType data_type) {
		this.data_type = data_type;
	}

	@Pattern(regexp = "([0-9]*)", message = "Length data must be numeric !!")
	private String length_data;
	
	@Pattern(regexp = "([0-9]*)", message = "Precision data is unvalid !!")
	private String precision_data;

	private String mapping;

	public String getMapping() {
		return mapping;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	@ManyToOne
	@JoinColumn(name = "id_flow", referencedColumnName = "id")
	private Flow flow;

	@OneToOne
    @JoinColumn(name = "id_principal_field", referencedColumnName = "id")
    private EtlPrincipalFields principalField;

    public EtlPrincipalFields getPrincipalField() {
        return principalField;
    }

    public void setPrincipalField(EtlPrincipalFields principalField) {
        this.principalField = principalField;
    }
	
	
	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}

	@Override
	public void setId(Long id) {
		super.setId(id);
	}

	public String getLibelle() {
		return libelle;
	}
	/*
	 * public Type_source getType() { return type; }
	 */

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	/*
	 * public void setType(Type_source type) { this.type = type; }
	 */

	public String getLength_data() {
		return length_data;
	}

	public void setLength_data(String length_data) {
		this.length_data = length_data;
	}

	public String getPrecision_data() {
		return precision_data;
	}

	public void setPrecision_data(String precision_data) {
		this.precision_data = precision_data;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
