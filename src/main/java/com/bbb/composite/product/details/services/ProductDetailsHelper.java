package com.bbb.composite.product.details.services;

import java.util.List;

import com.bbb.composite.product.details.dto.ProductCompositeDTO;
import com.bbb.composite.product.details.dto.SkuCompositeDTO;
import com.bbb.composite.product.details.dto.price.ProductPriceDTO;
import com.bbb.composite.product.details.dto.price.SkuPriceDTO;
import com.bbb.composite.product.details.dto.product.ProductDTO;
import com.bbb.composite.product.details.dto.sku.SkuDTO;

/**
 * This is a service which provides all the methods to provide the data.
 * 
 * @author skhur6
 *
 */
public interface ProductDetailsHelper {

	/**
	 * fetch the product Details
	 * 
	 * @param productDTO ProductDTO object to be passed.
	 * @return the product composite DTO obj
	 */
	ProductCompositeDTO getProductDetails(ProductDTO productDTO);

	/**
	 * Convert the SKU Data
	 * 
	 * @param skuDTOList list of sku dto
	 * @return List of sku composite dto
	 */
	List<SkuCompositeDTO> convertSkuData(List<SkuDTO> skuDTOList);

	/**
	 * Function for adding Variant option details to the Product.
	 * 
	 * @param skuDTOList list of sku dto
	 * @param productCompositeDTO product composite dto obj
	 */
	void addVariantOptionDetailsToProduct(List<SkuDTO> skuDTOList, ProductCompositeDTO productCompositeDTO);

	/**
	 * function for populating product price data.
	 * 
	 * @param productPriceDTO product price dto
	 * @param productDetailsDTO product details dto.
	 */
	void populateProductPriceData(ProductPriceDTO productPriceDTO, ProductCompositeDTO productDetailsDTO);

	/**
	 * function for populating sku price data
	 * 
	 * @param skuPriceDTOList list of sku price dto
	 * @param productDetailsDTO product composite dto obj
	 */
	void populateSkuPriceData(List<SkuPriceDTO> skuPriceDTOList, ProductCompositeDTO productDetailsDTO);

}