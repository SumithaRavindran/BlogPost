package com.appspot.hatchwaysapp.model;

public class Ping {
	private boolean success;
	
	public Ping() {
		
	}
	public Ping(boolean success) {
		super();
		this.success = success;
	}
	
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
