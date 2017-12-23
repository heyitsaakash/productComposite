package com.bbb.composite.product.details.services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.bbb.composite.product.details.dto.ProductCategoryCompositeDTO;
import com.bbb.composite.product.details.dto.ProductCompositeDTO;
import com.bbb.composite.product.details.dto.product.ProductDTO;
import com.bbb.composite.product.details.model.Product;
import com.bbb.core.dto.BaseServiceDTO;

/**
 * This is a service which provides all the methods to provide the data.
 * @author skhur6
 *
 */
public interface ProductDetailsService {

	/**
	 * Get product details based on productId, siteId and bbbChannel
	 *  
	 * @param productId product Id string val
	 * @param siteId site Id string val
	 * @param bbbChannel bbbChannel string val
	 * @return value as ProductCompositeDTO obj
	 */
	public ProductCompositeDTO getProductDetails(String productId, String siteId, String bbbChannel);
	
	/**
	 * Get product category details based on productId, siteId and bbbChannel
	 *  
	 * @param productId product Id string val
	 * @param siteId site Id string val
	 * @param bbbChannel bbbChannel string val
	 * @return value as ProductCategoryCompositeDTO obj
	 */
	public ProductCategoryCompositeDTO getProductCategoryDetails(String productId, String siteId, String bbbChannel);

	
	//added for the solr integration
	public Product getProductDetails(String productId);

	public ResponseEntity<BaseServiceDTO<List<Product>>> getProductsDetails(List<String> productIds);
	
}