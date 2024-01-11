package com.fraude.interfaces;

import java.util.List;

import javax.ejb.Local;


import com.fraude.entities.Noeud;
import com.fraude.entities.Repertoire;


@Local
public interface RepositoryInterface {

	public void addRepository(Repertoire p );
	public List<Repertoire> getRepertoireByNoeud(Noeud n);
	public List<Repertoire> getAllRep();
	public void updateRepository(Repertoire rep);
}
