package com.appspot.hatchwaysapp.model;

import java.util.Arrays;
import java.util.Comparator;

public class Data {

	public Data() {
		
	}
	private Integer id;
	private String author;
	private Integer authorId;
	private Integer likes;
	private Float popularity;
	private Integer reads;
	private String[] tags;


	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public Integer getAuthorId() {
		return authorId;
	}


	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}


	public Integer getLikes() {
		return likes;
	}


	public void setLikes(Integer likes) {
		this.likes = likes;
	}


	public Float getPopularity() {
		return popularity;
	}


	public void setPopularity(Float popularity) {
		this.popularity = popularity;
	}


	public Integer getReads() {
		return reads;
	}


	public void setReads(Integer reads) {
		this.reads = reads;
	}


	public String[] getTags() {
		return tags;
	}


	public void setTags(String[] tags) {
		this.tags = tags;
	}
	
	@Override
	public boolean equals(Object object) {
		 if (object == null) return false;
		    if (!(object instanceof Data))
		        return false;
		    if (object == this)
		        return true;
	    Data obj = (Data) object;
	    boolean isId = this.getId().equals(obj.getId());
	    boolean isAuthor = this.getAuthor().equals(obj.getAuthor());
	    boolean isAuthorId = this.getAuthorId().equals(getAuthorId());
	    boolean isLikes = this.getLikes().equals(obj.getLikes());
	    boolean isPopularity = this.getPopularity().equals(obj.getPopularity());
	    boolean isReads = this.getReads().equals(obj.getReads());
	    boolean isTags = checkEquality(this.tags, obj.getTags());
	    return isId && isAuthor && isAuthorId && isLikes  && isPopularity && 
	    		isReads && isTags;
	}
	
	@Override
	public int hashCode() {
	    return id;
	}
	
	public static boolean checkEquality(String[] s1, String[] s2) {
        if (s1 == s2)
            return true;
 
        if (s1 == null || s2 == null)
            return false;
 
        int n = s1.length;
        if (n != s2.length)
            return false;
        String[] s1Clone = s1.clone();
        String[] s2Clone = s2.clone();
        for (int i = 0; i < n; i++) {
            if (!s1Clone[i].equals(s2Clone[i]))
                return false;
        }
 
        return true;
    }
 
}
