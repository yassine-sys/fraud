package com.fraude.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: EtlServiceConverterTags
 *
 */
@Entity

public class EtlServiceConverterTags extends AbstractEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;

	private String tag_service;
	
	public EtlServiceConverterTags() {
		super();
	}

	public String getTag_service() {
		return tag_service;
	}

	public void setTag_service(String tag_service) {
		this.tag_service = tag_service;
	}
   
}
