package com.backend.mbean;

import java.io.Serializable;

import com.fraude.entities.Function;
import com.fraude.entities.RepModule;
import com.fraude.entities.RepSubModule;


public class Doc implements Serializable, Comparable<Doc> {

	private RepModule module;

	private RepSubModule subModule;

	private Function function;

	private String name;

	public Doc(RepModule module, RepSubModule subModule, Function function, String name) {
		this.module = module;
		this.subModule = subModule;
		this.function = function;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((module == null) ? 0 : module.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((subModule == null) ? 0 : subModule.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Doc other = (Doc) obj;
		if (module == null) {
			if (other.module != null)
				return false;
		} else if (!module.equals(other.module))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (subModule == null) {
			if (other.subModule != null)
				return false;
		} else if (!subModule.equals(other.subModule))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return name;
	}

	public int compareTo(Doc doc) {
		return this.getName().compareTo(doc.getName());
	}

	public RepModule getModule() {
		return module;
	}

	public void setModule(RepModule module) {
		this.module = module;
	}

	public RepSubModule getSubModule() {
		return subModule;
	}

	public void setSubModule(RepSubModule subModule) {
		this.subModule = subModule;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}
}