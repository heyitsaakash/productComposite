package com.bbb.composite.product.details.services.client;

import java.time.Duration;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.bbb.core.dto.BaseServiceDTO;
import com.bbb.core.dto.ErrorMessage;
import com.bbb.core.dto.ServiceStatus;
import com.bbb.core.utils.CoreUtils;

/**
 * IntegrationServiceUtils Implementation class
 * 
 * @author psh111
 *
 */
@Component("com.bbb.composite.product.details.services.client.IntegrationServiceUtilsImpl")
public class IntegrationServiceUtilsImpl implements IntegrationServiceUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(IntegrationServiceUtilsImpl.class);
	
	@Override
	public <T> BaseServiceDTO<T> extractResponseBody(ResponseEntity<BaseServiceDTO<T>> responseEntity
																		, String productId , String siteId, String bbbChannel) {
		HttpStatus httpStatus = responseEntity.getStatusCode();
		if (httpStatus.is3xxRedirection()) {
			LOGGER.warn("Received a {} as status code for siteId:{}, productId:{}, channel:{}. Was not expecting a redirection.",
					httpStatus.value(), siteId, productId, bbbChannel);
		} else if (httpStatus.isError()) {
			LOGGER.warn("Received a {} as status code for siteId:{}, productId:{}, channel:{}", httpStatus.value(), siteId,
					productId, bbbChannel);
		}
		return responseEntity.getBody();
	}
	
	@Override
	public <T> BaseServiceDTO<T> extractResponseBody(ResponseEntity<BaseServiceDTO<T>> responseEntity
																, List<String> childSkus, String siteId, String bbbChannel) {
		HttpStatus httpStatus = responseEntity.getStatusCode();
		if (httpStatus.is3xxRedirection()) {
			LOGGER.warn("Received a {} as status code for siteId:{}, skus:{}, channel:{}. Was not expecting a redirection.",
					httpStatus.value(), siteId, childSkus, bbbChannel);
		} else if (httpStatus.isError()) {
			LOGGER.warn("Received a {} as status code for siteId:{}, skus:{}, channel:{}", httpStatus.value(), siteId,
					childSkus, bbbChannel);
		}
		return responseEntity.getBody();
	}
	
	@Override
	@SuppressWarnings("squid:S2629")
	public <T> T extractDTOFromResponseBody(BaseServiceDTO<T> responseBody, String uri, String productId, String siteId, String bbbChannel) {
		ServiceStatus serviceStatus = responseBody.getServiceStatus(); 
		Duration duration = CoreUtils.getDurationOfCall(responseBody);
		StringBuilder msgBuilder = new StringBuilder("Call statistics[");
		msgBuilder.append("Input provided(endpoint:{}, siteId:{}, productId:{}, channel:{}), ")
					.append("Service Status: {}, ")
					.append("Total durantion of call was {} millis, ")
					.append("TraceId: {}, ")
					.append("Page size: {}");
		List<ErrorMessage> errorMessages = responseBody.getErrorMessages();
		if(CollectionUtils.isNotEmpty(errorMessages)) {
			msgBuilder.append(", Error Messages:{}");
		}
		msgBuilder.append("]");
		LOGGER.info(msgBuilder.toString()
						, uri, siteId, productId, bbbChannel, serviceStatus, duration.toMillis()
						, responseBody.getTraceId(), responseBody.getPageSize(), errorMessages);
		
		return responseBody.getData();
	
	}
	
	@Override
	@SuppressWarnings("squid:S2629")
	public <T> T extractDTOFromResponseBody(BaseServiceDTO<T> responseBody, String uri, List<String> childSkus, String siteId, String bbbChannel) {
		ServiceStatus serviceStatus = responseBody.getServiceStatus(); 
		Duration duration = CoreUtils.getDurationOfCall(responseBody);
		StringBuilder msgBuilder = new StringBuilder("Call statistics[");
		msgBuilder.append("Input provided(endpoint:{}, skus:{}, productId:{}, channel:{}), ")
					.append("Service Status: {}, ")
					.append("Total durantion of call was {} millis, ")
					.append("TraceId: {}, ")
					.append("Page size: {}");
		List<ErrorMessage> errorMessages = responseBody.getErrorMessages();
		if(CollectionUtils.isNotEmpty(errorMessages)) {
			msgBuilder.append(", Error Messages:{}");
		}
		msgBuilder.append("]");
		LOGGER.info(msgBuilder.toString()
						, uri, siteId, childSkus, bbbChannel, serviceStatus, duration.toMillis()
						, responseBody.getTraceId(), responseBody.getPageSize(), errorMessages);
		
		return responseBody.getData();
	}
}
