package com.fraude.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.fraude.entities.Convertisseur;
import com.fraude.entities.EtlFieldService;
import com.fraude.entities.EtlServiceConverter;
import com.fraude.entities.Service;


@Local
public interface ServiceInterface {

	
	public List<Service> getAllServices();
	
	public void addService(Service s);
	
	public void deleteService(Service s);
	
	public void updateService(Service s);
	
public List<EtlServiceConverter> getAllServiceConvertisseurByConv(Convertisseur c);	
public List<EtlFieldService> getAllChampsByService(EtlServiceConverter c);	

	public void addService_Convertisseur(EtlServiceConverter s);
	
	public void deleteService_Convertisseur(EtlServiceConverter s);
	
	public void updateService_Convertisseur(EtlServiceConverter s);
	
}
