package com.backend.mbean;

import java.io.Serializable;

public class DocProfile implements Serializable, Comparable<DocProfile> {

	private Object data;
	private String name;
	private String type;

	public DocProfile(Object data, String name,String type) {
		this.data = data;
		this.name = name;
		this.type = type;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(DocProfile document) {
		// TODO Auto-generated method stub
		return this.getName().compareTo(document.getName());
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
