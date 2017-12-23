package com.bbb.composite.product.details.services.client;

import java.util.List;

import com.bbb.composite.product.details.dto.price.ProductPriceDTO;
import com.bbb.composite.product.details.dto.price.SkuPriceDTO;

/**
 * PriceIntegrationService interface
 * 
 * @author psh111
 *
 */
public interface PriceIntegrationService {
	/**
	 * Fetch product price details based on bbbchannel siteId and productId.
	 * 
	 * @param bbbChannel channel value
	 * @param siteId site id
	 * @param productId product id
	 * @return value as productPriceDTO
	 */
	public ProductPriceDTO getProductPriceDetails(String bbbChannel, String siteId, String productId);

	/**
	 * Fetch skuPriceDetails based on bbbchannel siteId and list of sku's
	 * 
	 * @param bbbChannel channel value
	 * @param siteId site Id
	 * @param skus list of sku's
	 * @return value as List of SkuPriceDTO
	 */
	public List<SkuPriceDTO> getSkuPriceDetails(String bbbChannel, String siteId, List<String> skus);
}
