package com.fraude.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.fraude.entities.RepModule;


@Local
public interface ModuleInterface {

	public List<RepModule> getAllModules();
	public void addModule(RepModule m);
	public void deleteModule(RepModule m);
	public void updateModule(RepModule m);
}
