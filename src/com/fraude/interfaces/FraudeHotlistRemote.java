package com.fraude.interfaces;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.fraude.entities.ListAppelant;
import com.fraude.entities.ListAppele;
import com.fraude.entities.ListCellid;
import com.fraude.entities.ListDetailsAppelant;
import com.fraude.entities.ListDetailsAppele;
import com.fraude.entities.ListDetailsCellid;
import com.fraude.entities.ListDetailsImei;
import com.fraude.entities.ListImei;

@Local
public interface FraudeHotlistRemote {

	public List<ListCellid> getAllCellId();

	public List<ListAppelant> getAllAppelant();

	public List<ListAppele> getAllAppele();

	public List<ListImei> getAllImei();

	public void addCellId(ListCellid cell);

	public void updateCellId(ListCellid cell);

	public void deleteCellId(ListCellid cell);

	public void addDetailCellId(ListDetailsCellid lda);

	public void updateDetailCellId(ListDetailsCellid lda);

	public void deleteDetailCellId(ListDetailsCellid lda);

	public List<ListDetailsCellid> getListDetailsCellId(ListCellid lc);

	public void addAppelant(ListAppelant appelant);

	public void updateAppelant(ListAppelant appelant);

	public void deleteAppelant(ListAppelant appelant);

	public void addDetailAppelant(ListDetailsAppelant lda);

	public void updateDetailAppelant(ListDetailsAppelant lda);

	public void deleteDetailAppelant(ListDetailsAppelant lda);

	public List<ListDetailsAppelant> getListDetailsAppelant(ListAppelant lp);

	public List<Object[]> getListDetailsFromManageRegle(String table, Integer idParent);

	public Object getFromRegle(String table, Integer id);

	public void addAppele(ListAppele appele);

	public void updateAppele(ListAppele appele);

	public void deleteAppele(ListAppele appele);

	public void addDetailAppele(ListDetailsAppele lda);

	public void updateDetailAppele(ListDetailsAppele lda);

	public void deleteDetailAppele(ListDetailsAppele lda);

	public List<ListDetailsAppele> getListDetailsAppeles(ListAppele lp);

	public void addImei(ListImei imei);

	public void updateImei(ListImei imei);

	public void deleteImei(ListImei imei);

	public void addDetailImei(ListDetailsImei lda);

	public void updateDetailImei(ListDetailsImei lda);

	public void deleteDetailImei(ListDetailsImei lda);

	public List<ListDetailsImei> getListDetailsImei(ListImei lc);

	public ListDetailsCellid findDetailsCellid(String hotlistNum);

	public ListDetailsAppelant findDetailsAppelant(String hotlistNum);

	public ListDetailsAppele findDetailsAppele(String hotlistNum);

	public ListDetailsImei findDetailsImei(String hotlistNum);
	
	public void updateAppelant(@PathParam("id") Integer id, ListAppelant updateRequest);
	
	public void deleteAppelant(@PathParam("id") Integer id);
	
	public void updateAppele(@PathParam("id") Integer id, ListAppele updateRequest);
	
	public void deleteAppele(@PathParam("id") Integer id);
	
	
public void updateCellid(@PathParam("id") Integer id, ListCellid updateRequest);
	
	public void deleteCellid(@PathParam("id") Integer id);
	
	
public void updateImei(@PathParam("id") Integer id, ListImei updateRequest);
	
	public void deleteImei(@PathParam("id") Integer id);
	
	public List<ListDetailsAppelant> getAlldetailsappelant(Integer id);
	
	
	public void deleteListDetailsAppelant(@PathParam("id") Integer id);
	
	
	public void updateListDetailsAppelant(@PathParam("id") Integer id, ListDetailsAppelant updateRequest);
	
	
	
public List<ListDetailsAppele> getAlldetailsappele(Integer id);
	
	
	public void deleteListDetailsAppele(@PathParam("id") Integer id);
	
	
	public void updateListDetailsAppele(@PathParam("id") Integer id, ListDetailsAppele updateRequest);
	
	
public List<ListDetailsCellid> getAlldetailsCellid(Integer id);
	
	
	public void deleteListDetailsCellid(@PathParam("id") Integer id);
	
	
	public void updateListDetailsCellid(@PathParam("id") Integer id, ListDetailsCellid updateRequest);
	
	public List<ListImei> getImeiByid(Integer id);
	
	public List<ListCellid> getCellidByid(Integer id);
	
	public List<ListAppelant> getAppelantByid(Integer id);
	
	public List<ListAppele> getAppeleByid(Integer id);
	
	
public List<ListDetailsImei> getAllListDetailsImei(Integer id);
	
	
	public void deleteListDetailsImei(@PathParam("id") Integer id);
	
	
	public void updateListDetailsImei(@PathParam("id") Integer id, ListDetailsImei updateRequest);
	
	
	

}
