package com.bbb.composite.product.details.dao;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.bbb.composite.product.details.model.Product;
//import com.bbb.core.dto.BaseServiceDTO;
import com.bbb.composite.product.details.dto.BaseServiceDTO;
import com.bbb.core.integration.rest.CoreRestTemplate;
import com.bbb.core.utils.CoreUtils;
import org.springframework.web.client.RestTemplate;

@Service("GenericDownloaderDAOImpl")
public class GenericDownloaderDAOImpl implements GenericDownloaderDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(GenericDownloaderDAOImpl.class);

	
	
//	@Autowired
//	@Qualifier("coreRestTemplate")
//	private CoreRestTemplate coreRestTemplate;

	@Autowired
	private RestTemplate coreRestTemplate;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bbb.pregenerator.services.GenericDownloaderService#
	 * downloadDataFromUrl(java.lang.String,
	 * org.springframework.http.HttpMethod, java.util.List, java.lang.String,
	 * java.lang.String, org.springframework.core.ParameterizedTypeReference)
	 */
	@Override
	public <T> T downloadDataFromUrl(String url, HttpMethod method, Map<String, ?> parameters, String userName,
			String password, ParameterizedTypeReference<T> responseType) {
		
		LOGGER.warn("received:"+parameters.get("productIds"));
		HttpEntity<String> requestEntity = this.getRequestEntity(userName, password);
		//String[] arrParams = new String[1];
		//arrParams[0]=(String)parameters.get("productIds");
		ResponseEntity<T> responseEntity = this.coreRestTemplate.exchange(url, method, requestEntity,
		responseType, parameters);
	
		
		return this.handleResponseEntity(responseEntity, url, method, parameters);
	}

	
	
	private HttpEntity<String> getRequestEntity(String userName, String password) {
		HttpHeaders headers = new HttpHeaders();
		if (StringUtils.isNotBlank(userName) || StringUtils.isNotBlank(password)) {
			String credentials = userName + ":" + password;
			byte[] credsBytes = credentials.getBytes();
			byte[] base64CredsBytes = Base64.encodeBase64(credsBytes);
			String base64Creds = new String(base64CredsBytes, Charset.forName("UTF-8"));
			headers.add("Authorization", "Basic " + base64Creds);
		}
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
		return entity;
	}
	
	
	
	private <T> String constructErrorResponseLogMessage(HttpStatus responseHttpStatus, String url
														, HttpMethod method, Map<String, ?> parameters, T responseBody) {
		URI targetUri = UriComponentsBuilder.fromHttpUrl(url).build(parameters);
		StringBuilder sb = new StringBuilder();
		sb.append("Received ").append(responseHttpStatus).append(" http status code while downloading the uri ")
			.append(targetUri).append(" over ").append(method).append(".");
		if(null != responseBody) {
			sb.append(" Response received is -> ").append(responseBody);
		}
		return sb.toString();
	}
	
	private <T> T handleResponseEntity(ResponseEntity<T> responseEntity, String url, HttpMethod method,
			Map<String, ?> parameters) {
		T data = null;
		if (responseEntity != null) {
			HttpStatus responseHttpStatus = responseEntity.getStatusCode();
			if (responseHttpStatus.is2xxSuccessful()) {
				data = responseEntity.getBody();
			} else if (responseHttpStatus.is3xxRedirection()) {
				URI locationUri = responseEntity.getHeaders().getLocation();
				LOGGER.warn("Received {} http status code for redirection to {} while downloading the url {} over {}",
						responseHttpStatus, locationUri, url, method);
			} else {
				T responseBody = responseEntity.getBody();
				String logMsg = this.constructErrorResponseLogMessage(responseHttpStatus, url, method, parameters,
						responseBody);
				LOGGER.warn(logMsg);
			}
		} else {
			LOGGER.error("Received null ResponseEntity while downloading the url {} over {}", url, method);
		}
		return data;
	}
}
