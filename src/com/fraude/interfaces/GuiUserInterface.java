package com.fraude.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.fraude.entities.GuiUser;


@Local
public interface GuiUserInterface {

	public List<GuiUser> getAllUsers();

	public void addUser(GuiUser u);

	public void deleteUser(GuiUser u);

	public void updateUser(GuiUser u);
}
