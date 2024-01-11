package com.fraude.services;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.fraude.entities.GuiUser;
import com.fraude.interfaces.UserLoginInterface;

@Stateless
public class UserLoginservice implements UserLoginInterface {

	@PersistenceContext
	EntityManager em;

	@Override
	public GuiUser getconnectuser(String login, String passwd) {

		try {
			return (GuiUser) em.createQuery(
					"select u from GuiUser u where u.uLogin='" + login + "' and u.uPwd='" + passwd + "' and u.etat='A'")
					.getSingleResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("service problem");
			System.out.println("select u from GuiUser u where u.uLogin='" + login + "' and u.uPwd='" + passwd
					+ "' and u.etat='A'");
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public void updateUser(GuiUser guiUser) {
		// TODO Auto-generated method stub
		em.merge(guiUser);
	}

}
