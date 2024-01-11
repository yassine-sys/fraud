package com.fraude.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.fraude.entities.Noeud;


@Local
public interface NoeudInterface {

	public List<Noeud> getAllNoeud();
	public void addNoeud(Noeud n);
	public void deleteNoeud(Noeud n);
	public void updateNoeud(Noeud n);
}
