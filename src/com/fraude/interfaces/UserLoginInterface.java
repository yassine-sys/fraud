package com.fraude.interfaces;

import javax.ejb.Remote;

import com.fraude.entities.GuiUser;


@Remote
public interface UserLoginInterface {

	public GuiUser getconnectuser(String login ,String passwd);
	public void updateUser(GuiUser guiUser);
	
}
