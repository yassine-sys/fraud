package com.fraude.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.fraude.entities.PlanTarifaire;

@Local
public interface PlanTarifaireRemote {

	public void createPlanT(PlanTarifaire p);

	public void deletePlanT(PlanTarifaire p);

	public void updatePlanT(PlanTarifaire p);

	public PlanTarifaire getPlanByName(String name);

	public List<PlanTarifaire> getAllPlanTarifaires();

}
