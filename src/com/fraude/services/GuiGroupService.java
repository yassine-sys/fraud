package com.fraude.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.fraude.entities.GuiGroup;
import com.fraude.interfaces.GuiGroupInterface;

@Stateless
public class GuiGroupService implements GuiGroupInterface {

	@PersistenceContext
	EntityManager em;

	@Override
	public List<GuiGroup> getAllGroups() {
		// TODO Auto-generated method stub
		return em.createQuery("select g from GuiGroup g").getResultList();
	}

	@Override
	public void addGroup(GuiGroup g) {

		em.persist(g);
		
	}

	@Override
	public void deleteGroup(GuiGroup g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateGroup(GuiGroup g) {
		// TODO Auto-generated method stub
		em.merge(g);
	}

}
