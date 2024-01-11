package com.fraude.services;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;

import com.fraude.entities.Cell;
import com.fraude.entities.ListAppelant;
import com.fraude.entities.ListAppele;
import com.fraude.entities.ListCellid;
import com.fraude.entities.ListDetailsAppelant;
import com.fraude.entities.ListDetailsAppele;
import com.fraude.entities.ListDetailsCellid;
import com.fraude.entities.ListDetailsImei;
import com.fraude.entities.ListImei;
import com.fraude.interfaces.FraudeHotlistRemote;

@Stateless
public class FraudeHolistService implements FraudeHotlistRemote {

	@PersistenceContext
	EntityManager emRep;

	@Override
	public void addCellId(ListCellid cell) {
		// TODO Auto-generated method stub
		emRep.persist(cell);
	}

	@Override
	public void updateCellId(ListCellid cell) {
		// TODO Auto-generated method stub
		emRep.merge(cell);
	}

	@Override
	public void deleteCellId(ListCellid cell) {
		// TODO Auto-generated method stub
		emRep.remove(emRep.contains(cell) ? cell : emRep.merge(cell));
	}

	@Override
	public void addAppelant(ListAppelant appelant) {
		// TODO Auto-generated method stub
		emRep.persist(appelant);
	}

	@Override
	public void updateAppelant(ListAppelant appelant) {
		// TODO Auto-generated method stub
		emRep.merge(appelant);
	}

	@Override
	public void deleteAppelant(ListAppelant appelant) {
		// TODO Auto-generated method stub
		emRep.remove(emRep.contains(appelant) ? appelant : emRep.merge(appelant));

	}

	@Override
	public void addAppele(ListAppele appele) {
		// TODO Auto-generated method stub
		emRep.persist(appele);
	}

	@Override
	public void updateAppele(ListAppele appele) {
		// TODO Auto-generated method stub
		emRep.merge(appele);
	}

	@Override
	public void deleteAppele(ListAppele appele) {
		// TODO Auto-generated method stub
		emRep.createQuery("DELETE FROM ListAppele where id= " + appele.getId()).executeUpdate();

	}

	@Override
	public void addImei(ListImei imei) {
		// TODO Auto-generated method stub
		emRep.persist(imei);
	}

	@Override
	public void updateImei(ListImei imei) {
		// TODO Auto-generated method stub
		emRep.merge(imei);
	}

	@Override
	public void deleteImei(ListImei imei) {
		// TODO Auto-generated method stub
		emRep.remove(emRep.contains(imei) ? imei : emRep.merge(imei));

	}

	@Override
	public List<ListCellid> getAllCellId() {
		// TODO Auto-generated method stub
		return emRep.createQuery("Select p From ListCellid p").getResultList();
	}

	@Override
	public List<ListAppelant> getAllAppelant() {
		// TODO Auto-generated method stub
		return emRep.createQuery("Select p From ListAppelant p").getResultList();
	}

	@Override
	public List<ListAppele> getAllAppele() {
		// TODO Auto-generated method stub
		// List<Object[]> lst = emRep.createNativeQuery("Select * from
		// cdrs_archives.cdrs_in_act_140701 ").getResultList();
		return emRep.createQuery("Select p From ListAppele p").getResultList();
	}

	@Override
	public List<ListImei> getAllImei() {
		// TODO Auto-generated method stub
		return emRep.createQuery("Select p From ListImei p").getResultList();
	}

	@Override
	public void addDetailAppelant(ListDetailsAppelant lda) {
		 String hotlistnumberdata = lda.getHotlistnumber();
		 ListAppelant appelant=lda.getAppelant();
		    String nomUtilisateur=lda.getNomUtilisateur();
		    // Split the hotlist numbers using space, comma, or newline as separators
		    List<String> hotlistnumbers = Arrays.asList(hotlistnumberdata.split("[,\\s\\n]+"));

		    // Iterate over the hotlist numbers and persist a new entity for each
		    for (String hotlistnumber : hotlistnumbers) {
		        // Create a new ListDetailsImei instance for each hotlist number
		    	ListDetailsAppelant lda1 = new ListDetailsAppelant();
		        lda1.setHotlistnumber(hotlistnumber);
		        lda1.setAppelant(appelant);
		        lda1.setDateModif(new Timestamp(System.currentTimeMillis()));
		        lda1.setNomUtilisateur(nomUtilisateur);

		        // Persist the new entity in the database
		        emRep.persist(lda1);
		    }
	}

	@Override
	public void addDetailAppele(ListDetailsAppele lda) {
		 String hotlistnumberdata = lda.getHotlistnumber();
		    
		 ListAppele appele=lda.getAppele();
		  String nomUtilisateur=lda.getNomUtilisateur();
		    // Split the hotlist numbers using space, comma, or newline as separators
		    List<String> hotlistnumbers = Arrays.asList(hotlistnumberdata.split("[,\\s\\n]+"));

		    // Iterate over the hotlist numbers and persist a new entity for each
		    for (String hotlistnumber : hotlistnumbers) {
		        // Create a new ListDetailsImei instance for each hotlist number
		    	ListDetailsAppele lda1 = new ListDetailsAppele();
		        lda1.setHotlistnumber(hotlistnumber);
		        lda1.setAppele(appele);
		        lda1.setDateModif(new Timestamp(System.currentTimeMillis()));
		        lda1.setNomUtilisateur(nomUtilisateur);
		        // Persist the new entity in the database
		        emRep.persist(lda1);
		    }
	}

	@Override
	public List<ListDetailsAppele> getListDetailsAppeles(ListAppele lp) {
		// TODO Auto-generated method stub
		return emRep.createQuery("Select p From ListDetailsAppele p  where p.appele.id=" + lp.getId()).getResultList();
	}

	@Override
	public void updateDetailAppele(ListDetailsAppele lda) {
		// TODO Auto-generated method stub
		emRep.merge(lda);
	}

	@Override
	public void deleteDetailAppele(ListDetailsAppele lda) {
		// TODO Auto-generated method stub

		emRep.createQuery("DELETE FROM ListDetailsAppele where id= " + lda.getId()).executeUpdate();

	}

	@Override
	public void addDetailCellId(ListDetailsCellid lda) {
		 String hotlistnumberdata = lda.getHotlistnumber();
		   ListCellid cellid =lda.getCellId(); 
		   String nomUtilisateur=lda.getNomUtlisateur();
		    // Split the hotlist numbers using space, comma, or newline as separators
		    List<String> hotlistnumbers = Arrays.asList(hotlistnumberdata.split("[,\\s\\n]+"));

		    // Iterate over the hotlist numbers and persist a new entity for each
		    for (String hotlistnumber : hotlistnumbers) {
		        // Create a new ListDetailsImei instance for each hotlist number
		    	ListDetailsCellid lda1 = new ListDetailsCellid();
		        lda1.setHotlistnumber(hotlistnumber);
		        lda1.setCellId(cellid);
		        lda1.setDateModif(new Timestamp(System.currentTimeMillis()));
		        lda1.setNomUtlisateur(nomUtilisateur);
		        // Persist the new entity in the database
		        emRep.persist(lda1);
		    }
	}

	@Override
	public void updateDetailCellId(ListDetailsCellid lda) {
		// TODO Auto-generated method stub
		emRep.merge(lda);
	}

	@Override
	public void deleteDetailCellId(ListDetailsCellid lda) {
		// TODO Auto-generated method stub
		emRep.createQuery("DELETE FROM ListDetailsCellid where id= " + lda.getId()).executeUpdate();

	}

	@Override
	public List<ListDetailsCellid> getListDetailsCellId(ListCellid lc) {
		// TODO Auto-generated method stub
		return emRep.createQuery("Select p From ListDetailsCellid p  where p.cellId.id=" + lc.getId()).getResultList();
	}

	@Override
	public void updateDetailAppelant(ListDetailsAppelant lda) {
		// TODO Auto-generated method stub
		emRep.merge(lda);
	}

	@Override
	public void deleteDetailAppelant(ListDetailsAppelant lda) {
		// TODO Auto-generated method stub
		emRep.createQuery("DELETE FROM ListDetailsAppelant where id= " + lda.getId()).executeUpdate();
	}

	@Override
	public List<ListDetailsAppelant> getListDetailsAppelant(ListAppelant lp) {
		// TODO Auto-generated method stub
		return emRep.createQuery("Select p From ListDetailsAppelant p  where p.appelant.id=" + lp.getId())
				.getResultList();
	}

//	@Override
//	public void addDetailImei(ListDetailsImei lda) {
//		// TODO Auto-generated method stub
//		emRep.persist(lda);
//	}

	
	@Override
	public void addDetailImei(ListDetailsImei lda) {
		 String hotlistnumberdata = lda.getHotlistnumber();
		    ListImei imei=lda.getImei();
		    String nomUtilisateur=lda.getNomUtilisateur();
		    // Split the hotlist numbers using space, comma, or newline as separators
		    List<String> hotlistnumbers = Arrays.asList(hotlistnumberdata.split("[,\\s\\n]+"));

		    // Iterate over the hotlist numbers and persist a new entity for each
		    for (String hotlistnumber : hotlistnumbers) {
		        // Create a new ListDetailsImei instance for each hotlist number
		        ListDetailsImei lda1 = new ListDetailsImei();
		        lda1.setHotlistnumber(hotlistnumber);
		        lda1.setImei(imei);
		        lda1.setDateModif(new Timestamp(System.currentTimeMillis()));
		        lda1.setNomUtilisateur(nomUtilisateur);

		        // Persist the new entity in the database
		        emRep.persist(lda1);
		    }
	}

	@Override
	public void updateDetailImei(ListDetailsImei lda) {
		// TODO Auto-generated method stub
		emRep.merge(lda);
	}

	@Override
	public void deleteDetailImei(ListDetailsImei lda) {
		// TODO Auto-generated method stub
		emRep.createQuery("DELETE FROM ListDetailsImei where id= " + lda.getId()).executeUpdate();

	}

	@Override
	public List<ListDetailsImei> getListDetailsImei(ListImei lc) {
		// TODO Auto-generated method stub
		return emRep.createQuery("Select p From ListDetailsImei p  where p.imei.id=" + lc.getId()).getResultList();
	}

	@Override
	public ListDetailsCellid findDetailsCellid(String hotlistNum) {
		// TODO Auto-generated method stub
		try {
			Query q = emRep
					.createQuery("Select li from ListDetailsCellid li where li.hotlistnumber='" + hotlistNum + "'");
			return (ListDetailsCellid) q.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public ListDetailsAppelant findDetailsAppelant(String hotlistNum) {
		// TODO Auto-generated method stub
		try {
			Query q = emRep
					.createQuery("Select li from ListDetailsAppelant li where li.hotlistnumber='" + hotlistNum + "'");
			return (ListDetailsAppelant) q.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public ListDetailsAppele findDetailsAppele(String hotlistNum) {
		// TODO Auto-generated method stub
		try {
			Query q = emRep
					.createQuery("Select li from ListDetailsAppele li where li.hotlistnumber='" + hotlistNum + "'");
			return (ListDetailsAppele) q.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}
	}

	@Override
	public ListDetailsImei findDetailsImei(String hotlistNum) {
		// TODO Auto-generated method stub
		try {
			Query q = emRep
					.createQuery("Select li from ListDetailsImei li where li.hotlistnumber='" + hotlistNum + "'");
			return (ListDetailsImei) q.getSingleResult();
		} catch (NoResultException ex) {
			return null;
		}

	}

	@Override
	public List<Object[]> getListDetailsFromManageRegle(String table, Integer idParent) {
		// TODO Auto-generated method stub
		return emRep.createNativeQuery("Select * From " + table + "  where id_hotlist=" + idParent).getResultList();
	}

	@Override
	public Object getFromRegle(String table, Integer id) {
		// TODO Auto-generated method stub
		return (String) emRep.createQuery("Select s.nom From  " + table + " s where s.id=" + id + " ")
				.getSingleResult();
	}

	@Override
	public void updateAppelant(Integer id, ListAppelant updateRequest) {
		ListAppelant existingAppelant = emRep.find(ListAppelant.class, id);

		if (existingAppelant != null) {
			existingAppelant.setNom(updateRequest.getNom());

			existingAppelant.setDateModif(updateRequest.getDateModif());

			emRep.merge(existingAppelant);
		}
	}

	@Override
	public void deleteAppelant(Integer id) {
		ListAppelant appelant = emRep.find(ListAppelant.class, id);
		if (appelant != null) {
			emRep.remove(appelant);
		}
	}

	@Override
	public void updateAppele(Integer id, ListAppele updateRequest) {

		ListAppele existingAppele = emRep.find(ListAppele.class, id);

		if (existingAppele != null) {
			existingAppele.setNom(updateRequest.getNom());

			existingAppele.setDateModif(updateRequest.getDateModif());

			emRep.merge(existingAppele);
		}

	}

	@Override
	public void deleteAppele(Integer id) {
		ListAppele appele = emRep.find(ListAppele.class, id);
		if (appele != null) {
			emRep.remove(appele);
		}

	}

	@Override
	public void updateCellid(Integer id, ListCellid updateRequest) {
		ListCellid existingcellid = emRep.find(ListCellid.class, id);

		if (existingcellid != null) {
			existingcellid.setNom(updateRequest.getNom());
			existingcellid.setDateModif(updateRequest.getDateModif());

			emRep.merge(existingcellid);
		}
	}

	@Override
	public void deleteCellid(Integer id) {
		ListCellid cellid = emRep.find(ListCellid.class, id);
		if (cellid != null) {
			emRep.remove(cellid);
		}

	}

	@Override
	public void updateImei(Integer id, ListImei updateRequest) {
		ListImei existingimei = emRep.find(ListImei.class, id);

		if (existingimei != null) {
			existingimei.setNom(updateRequest.getNom());

			existingimei.setDateModif(updateRequest.getDateModif());

			emRep.merge(existingimei);
		}
	}

	@Override
	public void deleteImei(Integer id) {
		ListImei Imei = emRep.find(ListImei.class, id);
		if (Imei != null) {
			emRep.remove(Imei);
		}
	}

	@Override
	public List<ListDetailsAppelant> getAlldetailsappelant(Integer appelant_id) {
		TypedQuery<ListDetailsAppelant> query = emRep.createQuery(
				"SELECT epf FROM ListDetailsAppelant epf WHERE epf.appelant.id = :appelant_id",
				ListDetailsAppelant.class);
		query.setParameter("appelant_id", appelant_id);
		return query.getResultList();
	}

	@Override
	public void deleteListDetailsAppelant(Integer id) {
		ListDetailsAppelant details = emRep.find(ListDetailsAppelant.class, id);
		if (details != null) {
			emRep.remove(details);
		}

	}

	@Override
	public void updateListDetailsAppelant(Integer id, ListDetailsAppelant updateRequest) {
		ListDetailsAppelant existingdetails = emRep.find(ListDetailsAppelant.class, id);

		if (existingdetails != null) {
			existingdetails.setDateModif(updateRequest.getDateModif());
			existingdetails.setHotlistnumber(updateRequest.getHotlistnumber());

			emRep.merge(existingdetails);
		}

	}

	@Override
	public List<ListDetailsAppele> getAlldetailsappele(Integer appele_id) {
		TypedQuery<ListDetailsAppele> query = emRep.createQuery(
				"SELECT epf FROM ListDetailsAppele epf WHERE epf.appele.id = :appele_id", ListDetailsAppele.class);
		query.setParameter("appele_id", appele_id);
		return query.getResultList();
	}

	@Override
	public void deleteListDetailsAppele(Integer id) {
		ListDetailsAppele details = emRep.find(ListDetailsAppele.class, id);
		if (details != null) {
			emRep.remove(details);
		}

	}

	@Override
	public void updateListDetailsAppele(Integer id, ListDetailsAppele updateRequest) {
		ListDetailsAppele existingdetails = emRep.find(ListDetailsAppele.class, id);

		if (existingdetails != null) {
			existingdetails.setDateModif(updateRequest.getDateModif());
			existingdetails.setHotlistnumber(updateRequest.getHotlistnumber());

			emRep.merge(existingdetails);
		}

	}

	@Override
	public List<ListDetailsCellid> getAlldetailsCellid(Integer cell_id) {
		TypedQuery<ListDetailsCellid> query = emRep.createQuery(
				"SELECT epf FROM ListDetailsCellid epf WHERE epf.cellId.id =:cell_id", ListDetailsCellid.class);
		query.setParameter("cell_id", cell_id);
		return query.getResultList();
	}

	@Override
	public void deleteListDetailsCellid(Integer id) {
		ListDetailsCellid details = emRep.find(ListDetailsCellid.class, id);
		if (details != null) {
			emRep.remove(details);
		}

	}

	@Override
	public void updateListDetailsCellid(Integer id, ListDetailsCellid updateRequest) {
		ListDetailsCellid existingdetails = emRep.find(ListDetailsCellid.class, id);

		if (existingdetails != null) {
			existingdetails.setDateModif(updateRequest.getDateModif());
			existingdetails.setHotlistnumber(updateRequest.getHotlistnumber());

			emRep.merge(existingdetails);
		}

	}
	
	@Override
	public List<ListImei> getImeiByid(Integer id) {
		TypedQuery<ListImei> query = emRep
				.createQuery("SELECT epf FROM ListImei epf WHERE epf.id = :id", ListImei.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	@Override
	public List<ListCellid> getCellidByid(Integer id) {
		TypedQuery<ListCellid> query = emRep
				.createQuery("SELECT epf FROM ListCellid epf WHERE epf.id = :id", ListCellid.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	@Override
	public List<ListAppelant> getAppelantByid(Integer id) {
		TypedQuery<ListAppelant> query = emRep
				.createQuery("SELECT epf FROM ListAppelant epf WHERE epf.id = :id", ListAppelant.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	
	@Override
	public List<ListAppele> getAppeleByid(Integer id) {
		TypedQuery<ListAppele> query = emRep
				.createQuery("SELECT epf FROM ListAppele epf WHERE epf.id = :id", ListAppele.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	
	

	@Override
	public List<ListDetailsImei> getAllListDetailsImei(Integer imei_id) {
		TypedQuery<ListDetailsImei> query = emRep
				.createQuery("SELECT epf FROM ListDetailsImei epf WHERE epf.imei.id = :imei_id", ListDetailsImei.class);
		query.setParameter("imei_id", imei_id);
		return query.getResultList();
	}

	@Override
	public void deleteListDetailsImei(Integer id) {
		ListDetailsImei details = emRep.find(ListDetailsImei.class, id);
		if (details != null) {
			emRep.remove(details);
		}

	}

	@Override
	public void updateListDetailsImei(Integer id, ListDetailsImei updateRequest) {
		ListDetailsImei existingdetails = emRep.find(ListDetailsImei.class, id);

		if (existingdetails != null) {
			existingdetails.setDateModif(updateRequest.getDateModif());
			existingdetails.setHotlistnumber(updateRequest.getHotlistnumber());

			emRep.merge(existingdetails);
		}

	}
	



}
