package com.fraude.interfaces;

import javax.ejb.Local;

import com.fraude.entities.EtlPrincipalFields;

@Local
public interface EtlPrincipalFieldsInterface {

	public EtlPrincipalFields getPrincipalFieldByName(String principal_field_name);
	public void updateEtlPrincipalFields(EtlPrincipalFields principalField);
}
