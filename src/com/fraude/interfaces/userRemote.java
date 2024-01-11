package com.fraude.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.fraude.entities.GuiGroup;
import com.fraude.entities.GuiUser;



@Local
public interface userRemote {
	
	public List<GuiUser>  getAllUsers();
	
	public void addUser(GuiUser u);
	
	public void deleteUser(GuiUser u);
	
	public void updateUser(GuiUser u);
	public GuiGroup getGroupByName(String name);
	
	public List<GuiGroup> getAllGroup();
	
	public GuiUser authenticate(String login,String password);
	public GuiUser findUserByLogin(String login);
	

}
