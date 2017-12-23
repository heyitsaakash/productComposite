package com.bbb.composite.product.details;

import static org.mockito.Mockito.doReturn;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import com.bbb.composite.product.details.controllers.ProductCategoryDetailsController;
import com.bbb.composite.product.details.services.ProductDetailsService;
import com.bbb.core.exceptions.DataNotFoundException;

/**
 * Mockito test cases to validate the non integration scenarios.
 * 
 * @author psh111
 *
 */
@RunWith(MockitoJUnitRunner.class)	
public class ProductCategoryDetailsControllerMockitoTest {

	@InjectMocks
	private ProductCategoryDetailsController productCategoryDetailsController;
	
	@Mock
	ProductDetailsService productDetailsService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	/**
	 * Tets case to validate DataNotFoundException exception.
	 */
	@Test(expected=DataNotFoundException.class)
	public void testGetProductCategoryDetailsException(){
		String productId = "productId_4";
		String siteId = "siteId_2";		
		MockHttpServletRequest request = new MockHttpServletRequest();
		doReturn(null).when(productDetailsService).getProductCategoryDetails(productId, siteId, "");
		productCategoryDetailsController.getProductCategoryDetails(productId, siteId, request, "");
	}
	
	
}


