package com.bbb.composite.product.details.controllers;

import java.text.MessageFormat;
import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bbb.composite.product.details.constants.BBBProductDetailsConstants;
import com.bbb.composite.product.details.dto.ProductCategoryCompositeDTO;
import com.bbb.composite.product.details.services.ProductDetailsService;
import com.bbb.core.constants.BBBErrorCodeConstants;
import com.bbb.core.controllers.BaseController;
import com.bbb.core.dto.BaseServiceDTO;
import com.bbb.core.dto.ServiceStatus;
import com.bbb.core.exceptions.DataNotFoundException;
import com.bbb.core.exceptions.handler.RestResponseExceptionHandler;
import com.bbb.microservices.product.details.swagger.response.ProductCompositeResponse;

import io.swagger.annotations.ApiOperation;
/**
 * This Controller provides all product related actions.
 * @author skhur6
 *
 */
@RestController
@RequestMapping("/product-category-details")
public class ProductCategoryDetailsController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryDetailsController.class);
	
	@Autowired
	@Qualifier("com.bbb.composite.product.details.services.ProductDetailsServiceImpl")
	private ProductDetailsService proudctDetailsService;
	
	/**
	 * Method to get the product category details.
	 * 
	 * @param productId the product Id as String value
	 * @param siteId the Site Id  as String value
	 * @param request object of HttpServletRequest
	 * @param bbbChannel String value to pass Channel
	 * @return value as ResponseEntity of BaseServiceDTO<ProductCategoryCompositeDTO>
	 */
	@ApiOperation(response=ProductCompositeResponse.class ,value="provide the product Category Details")
	@RequestMapping(method = RequestMethod.GET, path = "/site/{siteId}/product/{productId}")
	public ResponseEntity<BaseServiceDTO<ProductCategoryCompositeDTO>> getProductCategoryDetails(@PathVariable String productId, 
			@PathVariable String siteId, HttpServletRequest request,
			@RequestParam(name = "channel", required = false) String bbbChannel) {
		
		Instant requestReceivedTime = Instant.now();
		request.setAttribute(RestResponseExceptionHandler.REQUEST_RECEIVED_TIME, requestReceivedTime);
		
		ProductCategoryCompositeDTO productCategoryCompositeDTO = proudctDetailsService.getProductCategoryDetails(productId, siteId, bbbChannel);
		if (productCategoryCompositeDTO == null) {
			String message = MessageFormat.format(BBBProductDetailsConstants.PRODUCT_CATEGORY_DETAIL_ERROR_PRODUCT_ENTITY_NOT_FOUND, productId, siteId);
			LOGGER.info("{} - {}", BBBErrorCodeConstants.EC_ENTITY_NOT_FOUND, message);
			throw new DataNotFoundException(message);
		} else {
			BaseServiceDTO<ProductCategoryCompositeDTO> baseServiceDTO = new BaseServiceDTO<>();
			baseServiceDTO.setData(productCategoryCompositeDTO);
			baseServiceDTO.setResponseSentTime(Instant.now());
			baseServiceDTO.setRequestReceivedTime(requestReceivedTime);
			baseServiceDTO.setServiceStatus(ServiceStatus.SUCCESS);
			baseServiceDTO.setPageSize(1);
			baseServiceDTO.setPageFrom(1);
			baseServiceDTO.setPageTo(1);
			baseServiceDTO.addTraceInfo(ProductCategoryDetailsController.this.spanAccessor);
			return new ResponseEntity<>(baseServiceDTO, HttpStatus.OK);
		}
	}

}
