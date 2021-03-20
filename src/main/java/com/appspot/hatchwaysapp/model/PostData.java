package com.appspot.hatchwaysapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class PostData {

	@JsonInclude(Include.NON_NULL)
	Data[] posts;
	@JsonInclude(Include.NON_NULL)
	private String error;
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	public Data[] getPosts() {
		return posts;
	}


	public void setPosts(Data[] posts) {
		this.posts = posts;
	}
}
