package com.fraude.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.fraude.entities.Config_ASCII;
import com.fraude.entities.Config_ASN1;
import com.fraude.entities.Convertisseur;


@Local
public interface ConverterInterface {

	public List<Convertisseur> getAllConverters();
	public void addConverter(Convertisseur c);
	public void deleteConverter(Convertisseur c);
public void addConfigAsn1(Config_ASN1 c);
public void addConfigASCII(Config_ASCII c);

public List<Convertisseur> getConvertisseursByTypeConfig(String type);
public void updateConvertisseur(Convertisseur c);

	
	
}
