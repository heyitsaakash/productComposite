package com.bbb.microservices.product.details.swagger.response;

import com.bbb.composite.product.details.dto.ProductCompositeDTO;
import com.bbb.core.dto.BaseServiceDTO;

/**
 * Class for getter setters for ProductCompositeDTO 
 * 
 * @author psh111
 *
 */
public class ProductCompositeResponse extends BaseServiceDTO<ProductCompositeDTO> {

	private static final long serialVersionUID = -5950720878077575432L;
	
	private ProductCompositeDTO data;
	
	@Override
	public ProductCompositeDTO getData() {
		return data;
	}
	@Override
	public void setData(ProductCompositeDTO data) {
		this.data = data;
	}
	
	

}
