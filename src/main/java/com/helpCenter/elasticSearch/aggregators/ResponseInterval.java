package com.helpCenter.elasticSearch.aggregators;

import org.springframework.stereotype.Component;

@Component
public class ResponseInterval {
	
	private String key;
	private long doc_count;
	
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public long getDoc_count() {
		return doc_count;
	}
	public void setDoc_count(long doc_count) {
		this.doc_count = doc_count;
	}
	public ResponseInterval(String key, long doc_count) {
		super();
		this.key = key;
		this.doc_count = doc_count;
	}
	public ResponseInterval() {
		super();
		
	}
	@Override
	public String toString() {
		return "ResponseInterval [key=" + key + ", doc_count=" + doc_count + "]";
	}
	
	

}
