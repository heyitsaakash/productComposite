package com.bbb.composite.product.details.services.client;

import java.util.List;

import com.bbb.composite.product.details.dto.CategoryDTO;

/**
 * CategoryIntegrationService interface class
 * 
 * @author psh111
 */
@FunctionalInterface
public interface CategoryIntegrationService {
	
	/**
	 * Function to get the List of Categories.
	 * 
	 * @param bbbChannel channel value as String
	 * @param siteId site id as String value
	 * @param relatedCategories list of related categories
	 * @return value as List of CategoryDTO
	 */
	public List<CategoryDTO> getCategories(String bbbChannel, String siteId, List<String> relatedCategories) ;
}
