package com.fraude.managedBeans.config;

import java.io.Serializable;

public class Doc implements Serializable, Comparable<Doc>{
	
	private Object type;
	private String name;
	
	public Doc(Object type,String name){
		this.type =type;
		this.name = name;
		
	}
	
	public Object getType() {
		return type;
	}
	public void setType(Object type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int compareTo(Doc document) {
		// TODO Auto-generated method stub
		return this.getName().compareTo(document.getName());
	}
	

}
