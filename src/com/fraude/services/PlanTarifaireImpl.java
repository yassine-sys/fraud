package com.fraude.services;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.fraude.entities.PlanTarifaire;
import com.fraude.interfaces.PlanTarifaireRemote;

/**
 * Session Bean implementation class PlanTarifaireImpl
 */
@Stateless
public class PlanTarifaireImpl implements PlanTarifaireRemote {

    /**
     * Default constructor. 
     */
    public PlanTarifaireImpl() {
        // TODO Auto-generated constructor stub
    }
    
    @PersistenceContext
	EntityManager emRep;

	@Override
	public void createPlanT(PlanTarifaire p) {
		emRep.persist(p);
		
	}

	@Override
	public void deletePlanT(PlanTarifaire p) {
		emRep.remove(emRep.contains(p) ? p : emRep.merge(p));
	}

	@Override
	public void updatePlanT(PlanTarifaire p) {
		emRep.merge(p);
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PlanTarifaire> getAllPlanTarifaires() {
		// TODO Auto-generated method stub
		return emRep.createQuery("Select p from PlanTarifaire p").getResultList();
	}

	@Override
	public PlanTarifaire getPlanByName(String name) {
		PlanTarifaire found = null;
		Query query = emRep.createQuery("select p from PlanTarifaire c where p.planTarifaire=:x");
		query.setParameter("x", name);
		try{
			found = (PlanTarifaire) query.getSingleResult();
		}catch(Exception ex){
			Logger.getLogger(this.getClass().getName()).log(Level.INFO, "no plan with name="+name);
		}
		return found;
	}

}
