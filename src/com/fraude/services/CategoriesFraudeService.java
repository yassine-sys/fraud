package com.fraude.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.fraude.entities.CategoriesFraude;
import com.fraude.interfaces.CategoriesFraudeRemote;

@Stateless
public class CategoriesFraudeService implements CategoriesFraudeRemote {

	@PersistenceContext
	EntityManager em;

	@Override
	public void AjouterCatFraude(CategoriesFraude catfraud) {
		// TODO Auto-generated method stub
		em.persist(catfraud);

	}

	@Override
	public void ModifierCatFraud(CategoriesFraude catfraude) {
		// TODO Auto-generated method stub
		em.merge(catfraude);
	}

	@Override
	public void SuprimerCatFraude(CategoriesFraude catfraude) {
		// TODO Auto-generated method stub
		em.remove(em.merge(catfraude));

	}

	@Override
	public List<CategoriesFraude> getAllCatFraude() {
		// TODO Auto-generated method stub
		return em.createQuery("Select c From CategoriesFraude c").getResultList();
	}
	
	 @Override
	 public void updateCategoriesFraude(Integer id,CategoriesFraude cat) {
		 CategoriesFraude existingCat=em.find(CategoriesFraude.class, id);
		 
		 if(existingCat!=null) {
			 existingCat.setNomCategorie(cat.getNomCategorie());
			
			 
			 em.merge(existingCat);
		 }
		 
		 
	 }
	 
	 @Override
	 public void deleteCatFraud(Integer id) {
		 CategoriesFraude cat=em.find(CategoriesFraude.class, id);
		 if(cat!=null) {
			 em.remove(cat);
		 }
	 }

}
