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
import com.bbb.composite.product.details.converters.ProductSolrEntityToDTOConverter;
import com.bbb.composite.product.details.dto.ProductCompositeDTO;
import com.bbb.composite.product.details.model.Product;
import com.bbb.composite.product.details.dto.ProductResponseDTO;
import com.bbb.composite.product.details.services.ProductDetailsService;
import com.bbb.core.constants.BBBErrorCodeConstants;
import com.bbb.core.controllers.BaseController;
import com.bbb.composite.product.details.dto.BaseServiceDTO;
import com.bbb.core.dto.ServiceStatus;
import com.bbb.core.exceptions.DataNotFoundException;
import com.bbb.core.exceptions.handler.RestResponseExceptionHandler;
import com.bbb.microservices.product.details.swagger.response.ProductCompositeResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
/**
 * This Controller provides all product related actions.
 * @author skhur6
 *
 */
@RestController
@RequestMapping("/product-details")
public class ProductDetailsController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductDetailsController.class);
	
	@Autowired
	@Qualifier("com.bbb.composite.product.details.services.ProductDetailsServiceImpl")
	private ProductDetailsService productDetailsService;
	
	
	@Autowired
	@Qualifier("com.bbb.composite.product.details.converters.ProductSolrEntityToDTOConverter")
	private ProductSolrEntityToDTOConverter productSolrEntityToDTOConverter;
	
	/**
	 * This method returns the complete product data from SOLR
	 * 
	 * @param productId product Id passed
	 * @param siteId siteId as String for which Product data is required.
	 * @param request HttpServletRequest object.
	 * @param bbbChannel Channel value to be passed as String.
	 * @return value BaseServiceDTO<ProductCompositeDTO> as a ResponseEntity. 
	 */
	@ApiOperation(response=ProductCompositeResponse.class ,value="provide the product Details")
	@ApiResponses(value = {@ApiResponse(code = 400, message = "Invalid  id or product id"),
			@ApiResponse(code = 404, message = "Product details not found for given id"),
			@ApiResponse(code = 500, message = "Request encountered an unexpected error") })
	@RequestMapping(method = RequestMethod.GET, path = "/{productId}")
	public ResponseEntity<BaseServiceDTO<ProductResponseDTO>> getProductDetailsFromSolr(@PathVariable String productId, 
			HttpServletRequest request) {
		
		Instant requestReceivedTime = Instant.now();
		request.setAttribute(RestResponseExceptionHandler.REQUEST_RECEIVED_TIME, requestReceivedTime);
		
		//step 1 - obtain the produt solr dto
		Product productSolr=productDetailsService.getProductDetails(productId);
		
		//step 2 - apply the required transformations(bus. rules) and get the response dto
		//step 3- get the base service dto ready with appropriate tracing information
		
		if (productSolr == null) {
			String message = MessageFormat.format(BBBProductDetailsConstants.PRODUCT_DETAIL_ERROR_PRODUCT_ENTITY_NOT_FOUND, productId, null);
			LOGGER.info("{} - {}", BBBErrorCodeConstants.EC_ENTITY_NOT_FOUND, message);
			throw new DataNotFoundException(message);
		} else {
		
			ProductResponseDTO productResponseDTO;
			productResponseDTO = new ProductResponseDTO();
			
			productSolrEntityToDTOConverter.convertEntityToDto(productSolr, productResponseDTO);
			
			BaseServiceDTO<ProductResponseDTO> baseServiceDTO = new BaseServiceDTO<>();
			baseServiceDTO.setData(productResponseDTO);
			baseServiceDTO.setResponseSentTime(Instant.now());
			baseServiceDTO.setRequestReceivedTime(requestReceivedTime);
			baseServiceDTO.setServiceStatus(ServiceStatus.SUCCESS);
			baseServiceDTO.setPageSize(1);
			baseServiceDTO.setPageFrom(1);
			baseServiceDTO.setPageTo(1);
			baseServiceDTO.addTraceInfo(ProductDetailsController.this.spanAccessor);
			return new ResponseEntity<>(baseServiceDTO, HttpStatus.OK);
		}
				
	}	
	
	
	/**
	 * This method returns the complete product data
	 * 
	 * @param productId product Id passed
	 * @param siteId siteId as String for which Product data is required.
	 * @param request HttpServletRequest object.
	 * @param bbbChannel Channel value to be passed as String.
	 * @return value BaseServiceDTO<ProductCompositeDTO> as a ResponseEntity. 
	 */
	@ApiOperation(response=ProductCompositeResponse.class ,value="provide the product Details")
	@ApiResponses(value = {@ApiResponse(code = 400, message = "Invalid site id or product id"),
			@ApiResponse(code = 404, message = "Product details not found for given site and product"),
			@ApiResponse(code = 500, message = "Request encountered an unexpected error") })
	@RequestMapping(method = RequestMethod.GET, path = "/site/{siteId}/product/{productId}")
	public ResponseEntity<BaseServiceDTO<ProductCompositeDTO>> getProductDetails(@PathVariable String productId, 
			@PathVariable String siteId, HttpServletRequest request,
			@RequestParam(name = "channel", required = false) String bbbChannel) {
		
		Instant requestReceivedTime = Instant.now();
		request.setAttribute(RestResponseExceptionHandler.REQUEST_RECEIVED_TIME, requestReceivedTime);
		
		ProductCompositeDTO productCompositeDto = productDetailsService.getProductDetails(productId, siteId, bbbChannel);
		if (productCompositeDto == null) {
			String message = MessageFormat.format(BBBProductDetailsConstants.PRODUCT_DETAIL_ERROR_PRODUCT_ENTITY_NOT_FOUND, productId, siteId);
			LOGGER.info("{} - {}", BBBErrorCodeConstants.EC_ENTITY_NOT_FOUND, message);
			throw new DataNotFoundException(message);
		} else {
			BaseServiceDTO<ProductCompositeDTO> baseServiceDTO = new BaseServiceDTO<>();
			baseServiceDTO.setData(productCompositeDto);
			baseServiceDTO.setResponseSentTime(Instant.now());
			baseServiceDTO.setRequestReceivedTime(requestReceivedTime);
			baseServiceDTO.setServiceStatus(ServiceStatus.SUCCESS);
			baseServiceDTO.setPageSize(1);
			baseServiceDTO.setPageFrom(1);
			baseServiceDTO.setPageTo(1);
			baseServiceDTO.addTraceInfo(ProductDetailsController.this.spanAccessor);
			return new ResponseEntity<>(baseServiceDTO, HttpStatus.OK);
		}
	}

}
