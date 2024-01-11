package com.fraude.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.fraude.entities.RepSubModule;


@Local
public interface SubModuleInterface {

	public List<RepSubModule> getAllSubModules();
	public void addSubModule(RepSubModule subm);
	public void deleteSubModule(RepSubModule subm);
	public void updateSubModule(RepSubModule subm);
}
