package com.fraude.interfaces;

import java.util.List;

import javax.ejb.Local;
import com.fraude.entities.DestAccOffre;
import com.fraude.entities.DetailsAccOffre;
import com.fraude.entities.DetailsOffre;
import com.fraude.entities.Offre;
import com.fraude.entities.OffrePlanTarif;

@Local
public interface OffreRemote {
	public List<Offre> getAllOffres();

	public void addOffrePlanTarifaire(OffrePlanTarif planoff);

	public void addOffre(Offre offre);

	public void addDetailsOffre(DetailsOffre details);

	public void updateDetailsOffre(DetailsOffre details);

	public void addDetailsAccOffre(DetailsAccOffre detAccOffre);

	public void updateDetailsAccOffre(DetailsAccOffre detAccOffre);

	public void deleteDetailAccOffre(DetailsAccOffre detAccOffre);

	public void deleteOffrePlanTarif(OffrePlanTarif opt);

	public List<DetailsAccOffre> getDetailsByService(String service);

	public List<DetailsAccOffre> getDetailsByDetails(DetailsOffre details);

	public DetailsOffre findDetailsOffre(DetailsOffre details);

	public DetailsOffre findDetailsByOffre(Offre offre);

	public DetailsAccOffre findDetailsAccOffre(DetailsAccOffre detailsacc);

	public void deleteOffre(Offre offre);

	public List<DestAccOffre> getListDestinationsByDetAcc(DetailsAccOffre detAcc);

}
