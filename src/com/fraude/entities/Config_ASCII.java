package com.fraude.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "etl_config_ascii")
public class Config_ASCII extends AbstractEntity {

	private String delimiter;
	private int bytes_to_skip;

	private String conf_type_search;

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return super.getId();
	}

	public String getConf_type_search() {
		return conf_type_search;
	}

	public void setConf_type_search(String conf_type_search) {
		this.conf_type_search = conf_type_search;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public int getBytes_to_skip() {
		return bytes_to_skip;
	}

	public void setBytes_to_skip(int bytes_to_skip) {
		this.bytes_to_skip = bytes_to_skip;
	}

}
