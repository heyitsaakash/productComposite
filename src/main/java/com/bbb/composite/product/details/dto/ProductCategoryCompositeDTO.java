/**
 * 
 */
package com.bbb.composite.product.details.dto;

import java.util.List;
import java.io.Serializable;

/**
 * @author nbisht
 *
 */
public class ProductCategoryCompositeDTO implements Serializable {

	private static final long serialVersionUID = -8558453551692152621L;
	
	private String productId;
	
	private String name;
	
	private List<CategoryDTO> categoryList;

	/**
	 * @return the productId
	 */
	public final String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public final void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the categoryList
	 */
	public final List<CategoryDTO> getCategoryList() {
		return categoryList;
	}

	/**
	 * @param categoryList the categoryList to set
	 */
	public final void setCategoryList(List<CategoryDTO> categoryList) {
		this.categoryList = categoryList;
	}
	
	

}
