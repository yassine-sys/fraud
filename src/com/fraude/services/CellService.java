package com.fraude.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.fraude.entities.CategoriesFraude;
import com.fraude.entities.Cell;
import com.fraude.interfaces.CellRemote;

@Stateless
public class CellService implements CellRemote {

	@PersistenceContext
	EntityManager em;

	@Override
	public List<Cell> getAllCell() {
		// TODO Auto-generated method stub

		return em.createQuery("Select c from Cell c ").getResultList();

	}

	@Override
	public void addCell(Cell cell) {
		// TODO Auto-generated method stub
		em.persist(cell);
	}

	@Override
	public void updateCell(Cell cell) {
		// TODO Auto-generated method stub
		em.merge(cell);
	}
	 @Override
	 public void updateCell(Integer id,Cell cell) {
		 Cell existingCell=em.find(Cell.class, id);
		 
		 if(existingCell!=null) {
			 existingCell.setAltitude(cell.getAltitude());
			 existingCell.setAngle(cell.getAngle());
			 existingCell.setAzimuth(cell.getAzimuth());
			 existingCell.setCellId(cell.getCellId());
			 existingCell.setCellNom(cell.getCellNom());
			 existingCell.setDateModif(cell.getDateModif());
			 existingCell.setLac(cell.getLac());
			 existingCell.setNomUtilisateur(cell.getNomUtilisateur());
			 existingCell.setType(cell.getType());
			 
			 
			 
			 
			 
			 em.merge(existingCell);
		 }
		 
		 
	 }
	@Override
	public void deleteCell(Cell cell) {
		// TODO Auto-generated method stub
		em.createQuery("DELETE FROM Cell where id = " + cell.getId()).executeUpdate();
	}
	 @Override
	 public void deleteCell(Integer id) {
		 Cell Cell=em.find(Cell.class, id);
		 if(Cell!=null) {
			 em.remove(Cell);
		 }
	 }


	@Override
	public Cell getCellByLacCellId(String lac, String cell) {
		// TODO Auto-generated method stub
		Query q = em.createQuery("Select c From Cell c where c.Lac='" + lac + "' and c.cellId='" + cell + "'");
		if (q.getResultList().isEmpty() || q.getResultList() == null) {
			return null;
		} else {
			return (Cell) q.getResultList().get(0);
		}
	}

}
