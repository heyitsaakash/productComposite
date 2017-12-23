package com.bbb.composite.product.details.services.client;

import com.bbb.composite.product.details.dto.product.ProductDTO;

/**
 * ProductIntegrationService interface class
 * 
 * @author psh111
 */
@FunctionalInterface
public interface ProductIntegrationService {
	/**
	 * Fetch product details based on productId , site Id and bbbchannel
	 * 
	 * @param productId product Id
	 * @param siteId site Id
	 * @param bbbChannel channel value
	 * @return value as ProductDTO
	 */
	public ProductDTO getProductDetails(String productId, String siteId, String bbbChannel);
}
