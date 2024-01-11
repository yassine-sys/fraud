package com.fraude.services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.fraude.entities.GuiUser;
import com.fraude.interfaces.GuiUserInterface;


@Stateless
public class GuiUserService implements GuiUserInterface{

	@PersistenceContext
	EntityManager em;
	
	@Override
	public List<GuiUser> getAllUsers() {
		// TODO Auto-generated method stub
		return em.createQuery("select u from GuiUser u").getResultList();
	}

	@Override
	public void addUser(GuiUser u) {
		// TODO Auto-generated method stub
		em.persist(u);
	}

	@Override
	public void deleteUser(GuiUser u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(GuiUser u) {
		// TODO Auto-generated method stub
		em.merge(u);
	}

}
