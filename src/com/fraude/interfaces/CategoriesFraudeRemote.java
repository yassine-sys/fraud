package com.fraude.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.fraude.entities.CategoriesFraude;

@Local
public interface CategoriesFraudeRemote {

	public void AjouterCatFraude(CategoriesFraude catfraud);

	public void ModifierCatFraud(CategoriesFraude catfraude);

	public void SuprimerCatFraude(CategoriesFraude catfraude);

	public List<CategoriesFraude> getAllCatFraude();
	
	public void updateCategoriesFraude(Integer id,CategoriesFraude cat);
	
	public void deleteCatFraud(Integer id);

}
