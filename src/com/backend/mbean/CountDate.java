package com.backend.mbean;

import java.math.BigInteger;

public class CountDate {

	private BigInteger count;
	
	private String date;

	public CountDate(Object obj1, Object obj2) {
		// TODO Auto-generated constructor stub
		this.count=(BigInteger) obj1;
		this.date=(String) obj2;
	}

	public BigInteger getCount() {
		return count;
	}

	public void setCount(BigInteger count) {
		this.count = count;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	
}
