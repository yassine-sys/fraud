package com.fraude.interfaces;

import java.util.List;

import javax.ejb.Local;


@Local
public interface FunctionsInterfaces {

	
	public void createReplaceFunctions(String function_name,String tablename, List<String> champs1, List<String> champs2 ,String condition);




}
