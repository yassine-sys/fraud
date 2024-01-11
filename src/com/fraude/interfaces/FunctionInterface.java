package com.fraude.interfaces;

import java.util.List;

import javax.ejb.Local;

import com.fraude.entities.Function;


@Local
public interface FunctionInterface {

	public List<Function> getAllFunctions();
	public void addFunction(Function subm);
	public void deleteFunction(Function subm);
	public void updateFunction(Function subm);
}

