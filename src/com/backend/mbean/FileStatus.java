package com.backend.mbean;

public class FileStatus {

	private String filename;
	
	private int status;
	
	public FileStatus(String filename,int status){
		this.filename = filename;
		this.status = status;
	}

	public String getFilename() {
		return filename;
	}

	public int getStatus() {
		return status;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
