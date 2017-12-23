package com.bbb.composite.product.details.dao;


import java.util.Map;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;


public interface GenericDownloaderDAO {

//	<T> T downloadDataFromUrl(String url, HttpMethod method, Map<String, ?> parameters, String userName,
//			String password, Class<T> responseType);

	
	<T> T downloadDataFromUrl(String url, HttpMethod method, Map<String, ?> parameters, String userName,
		String password, ParameterizedTypeReference<T> responseType);

	//<T>T downloadDataFromUrl(String url, HttpMethod method, List<Object> parameters, String userName, String password,
		//	ParameterizedTypeReference<T> responseType);

	

}