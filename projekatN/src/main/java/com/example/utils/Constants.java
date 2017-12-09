package com.example.utils;

public class Constants {
	public static final String TOKEN = "X-Auth-Token";

	public static final String ROLE_TENANT = "ROLE_USER";
	public static final String ROLE_COMPANY = "ROLE_COMPANY";
	public static final String ROLE_OWNER = "ROLE_OWNER";
	public static final String ROLE_PRESIDENT = "ROLE_PRESIDENT";
	
	private Constants() {
		throw new IllegalStateException("Utility class");
	}

}
