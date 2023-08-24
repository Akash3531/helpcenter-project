package com.helpCenter.elasticSearch.aggregators;

import org.springframework.stereotype.Component;

@Component
public class AggregationResponse {

	private String term;

	private long doc_Count;

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public long getDoc_Count() {
		return doc_Count;
	}

	public void setDoc_Count(long doc_Count) {
		this.doc_Count = doc_Count;
	}

	public AggregationResponse(String term, long doc_Count) {
		super();
		this.term = term;
		this.doc_Count = doc_Count;
	}

	public AggregationResponse() {
		super();
	
	}

	@Override
	public String toString() {
		return "AggregationResponse [term=" + term + ", doc_Count=" + doc_Count + "]";
	}

}
