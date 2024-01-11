package com.fraude.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.fraude.entities.DestAccOffre;
import com.fraude.entities.DetailsAccOffre;
import com.fraude.entities.DetailsOffre;
import com.fraude.entities.Offre;
import com.fraude.entities.OffrePlanTarif;
import com.fraude.interfaces.OffreRemote;

@Stateless
public class OffreService implements OffreRemote {

	@PersistenceContext
	EntityManager emRep;

	@Override
	public void addOffre(Offre offre) {
		// TODO Auto-generated method stub
		emRep.persist(offre);
	}

	@Override
	public List<DetailsAccOffre> getDetailsByService(String service) {
		// TODO Auto-generated method stub
		return emRep.createQuery("Select s From DetailsAccOffre where service= '" + service + "'").getResultList();
	}

	@Override
	public void addDetailsOffre(DetailsOffre details) {
		// TODO Auto-generated method stub
		emRep.persist(details);
	}

	@Override
	public void addDetailsAccOffre(DetailsAccOffre detAccOffre) {
		// TODO Auto-generated method stub
		emRep.persist(detAccOffre);

	}

	@Override
	public void addOffrePlanTarifaire(OffrePlanTarif planoff) {
		emRep.createNativeQuery("INSERT INTO tableref.offre_plan_tarif (id_details,code_plan_tarif) VALUES ("
				+ planoff.getDetailsOffre().getId() + ",'" + planoff.getPlan().getCodePlanTarifaire() + "');")
				.executeUpdate();

	}

	@Override
	public DetailsOffre findDetailsOffre(DetailsOffre details) {

		// TODO Auto-generated method stub
		String id = details.getOffre().getIdOffre();
		return (DetailsOffre) emRep.createQuery("Select s From DetailsOffre s where s.offre.idOffre = '" + id + "'")
				.getSingleResult();
	}

	@Override
	public DetailsAccOffre findDetailsAccOffre(DetailsAccOffre detailsacc) {
		// TODO Auto-generated method stub
		return emRep.find(DetailsAccOffre.class, detailsacc.getId());
	}

	@Override
	public List<Offre> getAllOffres() {
		// TODO Auto-generated method stub
		return emRep.createQuery("Select s From Offre s ").getResultList();
	}

	@Override
	public DetailsOffre findDetailsByOffre(Offre offre) {
		// TODO Auto-generated method stub
		String id = offre.getIdOffre();
		return (DetailsOffre) emRep.createQuery("Select s From DetailsOffre s where s.offre.idOffre = '" + id + "' ")
				.getSingleResult();
	}

	@Override
	public void updateDetailsOffre(DetailsOffre details) {
		// TODO Auto-generated method stub
		emRep.merge(details);

	}

	@Override
	public List<DetailsAccOffre> getDetailsByDetails(DetailsOffre details) {
		// TODO Auto-generated method stub
		Integer id = details.getId();
		return emRep.createQuery("Select s From DetailsAccOffre s where s.detailsOffre.id = " + id).getResultList();
	}

	@Override
	public void updateDetailsAccOffre(DetailsAccOffre detAccOffre) {
		// TODO Auto-generated method stub
		emRep.merge(detAccOffre);
	}

	@Override
	public void deleteDetailAccOffre(DetailsAccOffre detAccOffre) {
		// TODO Auto-generated method stub
		emRep.createQuery("DELETE From DetailsAccOffre s WHERE s.id= " + detAccOffre.getId()).executeUpdate();

	}

	@Override
	public void deleteOffrePlanTarif(OffrePlanTarif opt) {
		// TODO Auto-generated method stub
		emRep.createQuery("DELETE From OffrePlanTarif s WHERE s.id= " + opt.getId()).executeUpdate();
	}

	@Override
	public void deleteOffre(Offre offre) {
		// TODO Auto-generated method stub
		emRep.createQuery("DELETE From DetailsOffre s WHERE s.offre.idOffre= '" + offre.getIdOffre() + "'")
				.executeUpdate();
		emRep.createQuery("DELETE From Offre s WHERE s.idOffre='" + offre.getIdOffre() + "'").executeUpdate();

	}

	@Override
	public List<DestAccOffre> getListDestinationsByDetAcc(DetailsAccOffre detAcc) {
		// TODO Auto-generated method stub
		return emRep.createQuery("Select s From DestAccOffre s where s.detailsAccOffre.id = " + detAcc.getId())
				.getResultList();
	}

}
