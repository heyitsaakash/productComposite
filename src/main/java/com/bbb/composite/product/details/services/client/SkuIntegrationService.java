package com.bbb.composite.product.details.services.client;

import java.util.List;

import com.bbb.composite.product.details.dto.sku.SkuDTO;

/**
 * SkuIntegrationService Interface.
 * 
 * @author psh111
 *
 */
@FunctionalInterface
public interface SkuIntegrationService {
	/**
	 * Fetch SKU Details based on channel, siteId and ChildSkus
	 * 
	 * @param bbbChannel channel value
	 * @param siteId site Id
	 * @param childSkus list of ChildSkus
	 * @return value as List of SkuDto
	 */
	public List<SkuDTO> getSkuDetails(String bbbChannel, String siteId, List<String> childSkus) ;
}
