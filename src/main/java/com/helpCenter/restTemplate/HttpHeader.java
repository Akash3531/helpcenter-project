package com.helpCenter.restTemplate;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.helpCenter.elasticSearch.ElasticSearchConstants;

@Component
public class HttpHeader {

	public HttpHeaders createHeadersWithAuthentication() {
        String credentials = ElasticSearchConstants.getUserName() + ":" + ElasticSearchConstants.getPassword();
        String encodedCredentials = Base64Utils.encodeToString(credentials.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedCredentials);
        return headers;
    }
}
