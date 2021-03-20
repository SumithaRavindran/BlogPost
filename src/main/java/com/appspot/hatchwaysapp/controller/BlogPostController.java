package com.appspot.hatchwaysapp.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.appspot.hatchwaysapp.model.Data;
import com.appspot.hatchwaysapp.model.Ping;
import com.appspot.hatchwaysapp.model.PostData;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import com.google.gson.JsonObject;

import javax.net.ssl.HttpsURLConnection;

@RestController
public class BlogPostController {
	
	@RequestMapping(value ="/api/ping")
    public Ping getPing() 
    {
		return new Ping(true);
    }
	
	
	@RequestMapping(value ="/api/posts")
	public ResponseEntity<PostData> getPosts(@RequestParam(value = "tags", required = false) Optional<String> tags, 
			@RequestParam(value = "sortBy", required = false) Optional<String> sortreq,
			@RequestParam(value = "direction", required = false) Optional<String> directionreq)  {
		
		PostData postData = new PostData();
		
		if(!tags.isPresent() && tags.get() != null && tags.get().equals("")) {
			 
			postData.setError("Tags parameter is required");
			return new ResponseEntity<>(postData, HttpStatus.NOT_FOUND);
		}
		
		String sortby = "id";
		if(sortreq.isPresent() && sortreq.get() != null && sortreq.get().equals("")) {
			sortby = sortreq.get();
			if(sortby != "id" 
					&& sortby.equals("reads") 
					&& sortby.equals("likes")
					&& sortby.equals("popularity")) {
				postData.setError("sortBy parameter is invalid");
				return new ResponseEntity<>(postData, HttpStatus.NOT_FOUND);
			}
		}
		
		String direction = "asc";
		if(directionreq.isPresent() && directionreq.get() != null && directionreq.get().equals("")) {
			direction = directionreq.get();
			if(direction.equals("asc") && direction.equals("desc")) {
				postData.setError("direction parameter is invalid");
				return new ResponseEntity<>(postData, HttpStatus.NOT_FOUND);
			}
		}
		
		String[] tagsArray = tags.get().split(",");
	     
		
	     List<Data> result = new ArrayList<Data>();
	        
	     for(int i =0; i<tagsArray.length; i++) {
	    	GetData(tagsArray[i], result);
	     }
	     SortTheArray(result, sortby, direction);
	    
		Data[] array = new Data[result.size()];
		result.toArray(array); 
		postData.setPosts(array);
		return new ResponseEntity<>(postData, HttpStatus.OK);
	}
	private void SortTheArray(List<Data> result, String sortby, String direction) {
		  if(sortby.equals("id")) {
		    	if(direction.equals("asc"))
		    		Collections.sort(result, new Comparator<Data>() {
		    		    @Override
		    		    public int compare(Data a, Data b) {
		    		    	 return a.getId() - b.getId();
		    		    }
		    		});
		    	else
		    		Collections.sort(result, new Comparator<Data>() {
		    		    @Override
		    		    public int compare(Data a, Data b) {
		    		    	 return b.getId() - a.getId();
		    		    }
		    		});
		    }else if(sortby.equals("reads")) {
		    	if(direction.equals("asc"))
		    		Collections.sort(result, new Comparator<Data>() {
		    		    @Override
		    		    public int compare(Data a, Data b) {
		    		    	 return a.getReads() - b.getReads();
		    		    }
		    		});
		    	else
		    		Collections.sort(result, new Comparator<Data>() {
		    		    @Override
		    		    public int compare(Data a, Data b) {
		    		    	 return b.getReads() - a.getReads();
		    		    }
		    		});
		    }else if(sortby.equals("likes")) {
		    	if(direction.equals("asc"))
		    		Collections.sort(result, new Comparator<Data>() {
		    		    @Override
		    		    public int compare(Data a, Data b) {
		    		    	 return a.getLikes() - b.getLikes();
		    		    }
		    		});
		    	else
		    		Collections.sort(result, new Comparator<Data>() {
		    		    @Override
		    		    public int compare(Data a, Data b) {
		    		    	 return b.getLikes() - a.getLikes();
		    		    }
		    		});
		    }
		    else if(sortby.equals("popularity")) {
		    	if(direction.equals("asc"))
		    		Collections.sort(result, new Comparator<Data>() {
		    		    @Override
		    		    public int compare(Data a, Data b) {
		    		    	 return Float.compare(a.getPopularity(), b.getPopularity());
		    		    }
		    		});
		    	else
		    		Collections.sort(result, new Comparator<Data>() {
		    		    @Override
		    		    public int compare(Data a, Data b) {
		    		    	 return Float.compare(b.getPopularity(), a.getPopularity());
		    		    }
		    		});
		    }
	}
	
	private void GetData(String tag,  List<Data> result ){
		
		 String response= null;
		try {
			URL obj = new URL("https://api.hatchways.io/assessment/blog/posts?tag=" + tag);
            HttpsURLConnection con = (HttpsURLConnection)obj.openConnection();
         
            InputStream is = con.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader in = new BufferedReader(isr);
        while ((response = in.readLine()) != null) {
            JsonObject convertedObject = new Gson().fromJson(response, JsonObject.class);
        
            JsonArray data = convertedObject.getAsJsonArray("posts");
            for (int i = 0; i < data.size(); i++) {
            	Data datanew = new Data();
            	
            	if(!data.get(i).getAsJsonObject().get("author").isJsonNull())
            		datanew.setAuthor(data.get(i).getAsJsonObject().get("author").getAsString());
            	
            	if(!data.get(i).getAsJsonObject().get("authorId").isJsonNull())
            	datanew.setAuthorId(data.get(i).getAsJsonObject().get("authorId").getAsInt());
            	
            	if(!data.get(i).getAsJsonObject().get("id").isJsonNull())
            	datanew.setId(data.get(i).getAsJsonObject().get("id").getAsInt());
            	
            	if(!data.get(i).getAsJsonObject().get("likes").isJsonNull())
            	datanew.setLikes(data.get(i).getAsJsonObject().get("likes").getAsInt()); 
            	
            	if(!data.get(i).getAsJsonObject().get("popularity").isJsonNull())
            	datanew.setPopularity(data.get(i).getAsJsonObject().get("popularity").getAsFloat());
            	
            	if(!data.get(i).getAsJsonObject().get("reads").isJsonNull())
            	datanew.setReads(data.get(i).getAsJsonObject().get("reads").getAsInt()); 
            	
            	if(!data.get(i).getAsJsonObject().get("tags").isJsonNull()) {
            		JsonArray tagsJson = data.get(i).getAsJsonObject().get("tags").getAsJsonArray();
                	String[] tags1 = new String[tagsJson.size()];
                	 for (int j = 0; j < tagsJson.size(); j++) {
                		 tags1[j] = tagsJson.get(j).getAsString();
                	 }
                	 datanew.setTags(tags1);
            	}
            	if(!result.contains(datanew)) {
            	 result.add(datanew);
            	
            	}
            }
        }
        in.close();
      
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
	}

}