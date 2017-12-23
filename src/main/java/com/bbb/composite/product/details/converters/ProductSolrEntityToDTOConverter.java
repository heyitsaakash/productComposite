package com.bbb.composite.product.details.converters;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.bbb.core.data.converter.EntityToDtoConverter;
import com.bbb.composite.product.details.model.Product;
import com.bbb.composite.product.details.dto.ProductResponseDTO;

@Component("com.bbb.composite.product.details.converters.ProductSolrEntityToDTOConverter")
public class ProductSolrEntityToDTOConverter implements EntityToDtoConverter<Product, ProductResponseDTO>{
	
	@Override
	public void convertEntityToDto(Product entity, ProductResponseDTO responseDto) {
		// TODO Auto-generated method stub
		
		if (null!=entity) {
			
			
			
			BeanUtils.copyProperties(entity, responseDto);
		}
	}

}
