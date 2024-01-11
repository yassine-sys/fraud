package com.fraude.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.fraude.entities.GuiGroup;


@Local
public interface GuiGroupInterface {

	public List<GuiGroup> getAllGroups();

	public void addGroup(GuiGroup g);

	public void deleteGroup(GuiGroup g);

	public void updateGroup(GuiGroup g);
}
