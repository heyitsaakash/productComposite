package com.bbb.composite.product.details.services.client;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bbb.core.dto.BaseServiceDTO;

/**
 * Interface for extracting BaseServiceDTO ResponseBody 
 * 
 * @author psh111
 *
 */
public interface IntegrationServiceUtils {

	/**
	 * Extracting BaseserviceDTO from ResponseBody
	 * 
	 * @param responseEntity response Entity
	 * @param productId product Id
	 * @param siteId site Id
	 * @param bbbChannel channel value
	 * @return value as BaseServiceDTO
	 */
	<T> BaseServiceDTO<T> extractResponseBody(ResponseEntity<BaseServiceDTO<T>> responseEntity, String productId,
			String siteId, String bbbChannel);
	/**
	 * Extracting BaseserviceDTO from ResponseBody with list of ChildSKU's passed as param.
	 * 
	 * @param responseEntity Response Entity
	 * @param childSkus List of SKU's
	 * @param siteId site Id
	 * @param bbbChannel channel Value
	 * @return value as BaseServiceDTO
	 */
	<T> BaseServiceDTO<T> extractResponseBody(ResponseEntity<BaseServiceDTO<T>> responseEntity,
			List<String> childSkus, String siteId, String bbbChannel);

	/**
	 * Generic Method to extract DTO from ResponseBody
	 * 
	 * @param responseBody Response Body
	 * @param uri value as String
	 * @param childSkus list of SKU's
	 * @param siteId site Id
	 * @param bbbChannel channel value
	 * @return value as GenericType Class
	 */
	<T> T extractDTOFromResponseBody(BaseServiceDTO<T> responseBody, String uri, List<String> childSkus, String siteId,
			String bbbChannel);

	/**
	 * Generic function to extract DTO from ResponseBody with Product Id passed as param. 
	 * 
	 * @param responseBody Response Body
	 * @param uri value as String
	 * @param productId product Id as String
	 * @param siteId site Id
	 * @param bbbChannel channel value
	 * @return value as Generic Type Class
	 */
	<T> T extractDTOFromResponseBody(BaseServiceDTO<T> responseBody, String uri, String productId, String siteId,
			String bbbChannel);
}