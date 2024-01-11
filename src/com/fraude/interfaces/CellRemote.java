package com.fraude.interfaces;

import java.util.List;

import javax.ejb.Local;
import com.fraude.entities.Cell;

@Local
public interface CellRemote {

	public List<Cell> getAllCell();

	public void addCell(Cell cell);

	public void updateCell(Cell cell);

	public void deleteCell(Cell cell);
	
	public Cell getCellByLacCellId(String lac,String cell);
	 public void updateCell(Integer id,Cell cell) ;

	 public void deleteCell(Integer id) ;


}
