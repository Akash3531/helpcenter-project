package com.helpCenter.elasticSearch;

import org.springframework.stereotype.Component;

@Component
public class ElasticSearchConstants {

	private static String userName = "elastic";

	private static String password = "_i0eCRN1RMplLu-mVYYw";

	public static String getUserName() {
		return userName;
	}

	public static void setUserName(String userName) {
		ElasticSearchConstants.userName = userName;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		ElasticSearchConstants.password = password;
	}

}
