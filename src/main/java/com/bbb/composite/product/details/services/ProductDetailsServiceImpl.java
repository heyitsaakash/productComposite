package com.bbb.composite.product.details.services;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bbb.composite.product.details.constants.BBBProductDetailsConstants;
import com.bbb.composite.product.details.dto.CategoryDTO;
import com.bbb.composite.product.details.dto.CategorySummaryDTO;
import com.bbb.composite.product.details.dto.ProductCategoryCompositeDTO;
import com.bbb.composite.product.details.dto.ProductCompositeDTO;
import com.bbb.composite.product.details.model.ProductResponse;

import com.bbb.composite.product.details.model.Product;
import com.bbb.composite.product.details.model.ProductWrapper;
import com.bbb.composite.product.details.dto.SkuCompositeDTO;
import com.bbb.composite.product.details.dto.price.ProductPriceDTO;
import com.bbb.composite.product.details.dto.price.SkuPriceDTO;
import com.bbb.composite.product.details.dto.product.ProductDTO;
import com.bbb.composite.product.details.dto.sku.SkuDTO;
import com.bbb.composite.product.details.services.client.CategoryIntegrationService;
import com.bbb.composite.product.details.services.client.PriceIntegrationService;
import com.bbb.composite.product.details.services.client.ProductIntegrationService;
import com.bbb.composite.product.details.services.client.SkuIntegrationService;
import com.bbb.core.constants.BBBErrorCodeConstants;
import com.bbb.core.exceptions.DataNotFoundException;
import com.bbb.composite.product.details.dao.GenericDownloaderDAO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ProductDetailsService Implementation class file.
 * 
 * @author psh111
 *
 */
@Service("com.bbb.composite.product.details.services.ProductDetailsServiceImpl")
public class ProductDetailsServiceImpl implements ProductDetailsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductDetailsServiceImpl.class);
	
	
	
	@Autowired
	@Qualifier("com.bbb.composite.product.details.services.ProductDetailsHelperImpl")
	private ProductDetailsHelper productDetailsHelper;
	
	@Autowired
	@Qualifier("com.bbb.composite.product.details.services.ProductIntegrationServiceImpl")
	private ProductIntegrationService productIntegrationService;
	
	@Autowired
	@Qualifier("com.bbb.composite.product.details.services.SkuIntegrationServiceImpl")
	private SkuIntegrationService skuIntegrationService;
	
	@Autowired
	@Qualifier("com.bbb.composite.product.details.services.PriceIntegrationServiceImpl")
	private PriceIntegrationService priceIntegrationService;
	
	@Autowired
	@Qualifier("com.bbb.composite.product.details.services.CategoryIntegrationServiceImpl")
	private CategoryIntegrationService categoryIntegrationService;
	
	//added by aakash - later to come from config server

	//the variables injected needed for the communication with backend solr
	@Value("${fusion.access.username}")
	private String fusionAccessUsername;

	@Value("${fusion.access.password}")
	private String fusionAccessPassword;

	@Value("${fusion.server.base.url}")
	private String fusionServerBaseUrl;

	@Value("${fusion.products.data.fetch.url.pattern}")
	private String fusionProductsDataFetchUrlPattern;
	
	private static final ParameterizedTypeReference<ProductWrapper<ProductResponse>> RESPONSE_TYPE = new ParameterizedTypeReference<ProductWrapper<ProductResponse>>() {
	};
	
	
	@Autowired
	@Qualifier("jacksonObjectMapper")
	private ObjectMapper jacksonObjectMapper;
	
	@Autowired
	@Qualifier("GenericDownloaderDAOImpl")
	private GenericDownloaderDAO genericDownloaderDAO;
	
	private static final String LOGGER_PLACE_HOLDERS = "{} - {}";
	
	@PostConstruct
	private void init() {
		
			this.fusionProductsDataFetchUrlPattern = this.fusionServerBaseUrl
				+ this.fusionProductsDataFetchUrlPattern;
			
			//this.fusionProductsDataFetchUrlPattern="http://35.196.101.210:8764/api/apollo/query-pipelines/bedbath-default/collections/bedbath/select?echoParams=all&wt=json&json.nl=arrarr&sort&start=0&q=*:*&fq=PRODUCT_ID:(11060898999)";
		
	}
	
	@Override
	public ProductCompositeDTO getProductDetails(String productId, String siteId, String bbbChannel) {
		LOGGER.info("Getting Product Details with ProductID - {}, siteId - {}, bbbChannel - {} ", productId, siteId, bbbChannel);
		ProductCompositeDTO productCompositeDTO = fetchProductData(productId, siteId, bbbChannel);
		if(null != productCompositeDTO) {
			fetchProductPriceData(productId, siteId, bbbChannel, productCompositeDTO);
			fetchSkuDetails(siteId, bbbChannel, productCompositeDTO);
		}
		return productCompositeDTO;
	}
	
	private ProductCompositeDTO fetchProductData(String productId, String siteId, String bbbChannel) {
		ProductDTO productDTO = productIntegrationService.getProductDetails(productId, siteId, bbbChannel);
		return productDetailsHelper.getProductDetails(productDTO);
	}
	
	private void fetchProductPriceData(String productId, String siteId, String bbbChannel, ProductCompositeDTO productCompositeDTO) {
		ProductPriceDTO productPriceDTO = priceIntegrationService.getProductPriceDetails(bbbChannel, siteId, productId);
		if (null != productPriceDTO) {
			productDetailsHelper.populateProductPriceData(productPriceDTO, productCompositeDTO);
		} else {
			String message = MessageFormat.format(BBBProductDetailsConstants.PRODUCT_DETAIL_ERROR_PRODUCT_PRICE_ENTITY_NOT_FOUND, productId, siteId);
			LOGGER.info(LOGGER_PLACE_HOLDERS, BBBErrorCodeConstants.EC_ENTITY_NOT_FOUND, message);
			throw new DataNotFoundException(message);
		}
	}
	
	private void fetchSkuDetails(String siteId, String bbbChannel, ProductCompositeDTO productCompositeDTO) {
		if (CollectionUtils.isNotEmpty(productCompositeDTO.getChildSKUs())) {
			List<SkuDTO> skuDTOList = skuIntegrationService.getSkuDetails(bbbChannel, siteId, productCompositeDTO.getChildSKUs());
			if (CollectionUtils.isNotEmpty(skuDTOList)) {
				List<SkuCompositeDTO> skuCompositeDTOs = productDetailsHelper.convertSkuData(skuDTOList);
				productCompositeDTO.setSkus(skuCompositeDTOs);
				productDetailsHelper.addVariantOptionDetailsToProduct(skuDTOList, productCompositeDTO);
				fetchSkuPriceDetails(siteId, bbbChannel, productCompositeDTO);
			} else {
				String message = MessageFormat.format(BBBProductDetailsConstants.PRODUCT_DETAIL_ERROR_SKU_ENTITY_NOT_FOUND, productCompositeDTO.getChildSKUs().toString(), siteId);
				LOGGER.info(LOGGER_PLACE_HOLDERS, BBBErrorCodeConstants.EC_ENTITY_NOT_FOUND, message);
				throw new DataNotFoundException(message);
			}
		}
	}
	
	private void fetchSkuPriceDetails(String siteId, String bbbChannel, ProductCompositeDTO productCompositeDTO) {
		List<SkuPriceDTO> skuPriceDTOList = priceIntegrationService.getSkuPriceDetails(bbbChannel, siteId, productCompositeDTO.getChildSKUs());
		List<String> notFoundSkus =  getSkusNotFetchedFromService(skuPriceDTOList, productCompositeDTO.getChildSKUs());
		if (CollectionUtils.isNotEmpty(skuPriceDTOList) && CollectionUtils.isEmpty(notFoundSkus)) {
			productDetailsHelper.populateSkuPriceData(skuPriceDTOList, productCompositeDTO);
		} else {
			if (CollectionUtils.isEmpty(notFoundSkus)) {
				notFoundSkus = productCompositeDTO.getChildSKUs();
			}
			String message = MessageFormat.format(BBBProductDetailsConstants.PRODUCT_DETAIL_ERROR_SKU_PRICE_ENTITY_NOT_FOUND, notFoundSkus, siteId);
			LOGGER.info(LOGGER_PLACE_HOLDERS, BBBErrorCodeConstants.EC_ENTITY_NOT_FOUND, message);
			throw new DataNotFoundException(message);
		}
	}
	
	private List<String> getSkusNotFetchedFromService(List<SkuPriceDTO> skuPriceFoundInRequest, List<String> childSkusSent) {
		List<String> notFoundSkus = new ArrayList<>();
		if (skuPriceFoundInRequest != null) {
			for (String childSku:childSkusSent) {
				if (!isSkuPresent(skuPriceFoundInRequest, childSku)) {
					notFoundSkus.add(childSku);
				}
			}
		}
		return notFoundSkus;
	}
	private boolean isSkuPresent(List<SkuPriceDTO> skuPriceFoundInRequest, String childSku) {
		boolean isPresent = false;
		for (SkuPriceDTO skuPriceDto:skuPriceFoundInRequest) {
			if (skuPriceDto.getSkuId().equalsIgnoreCase(childSku)) {
				isPresent = true;
				break;
			}
		}
		return isPresent;
	}

	@Override
	public ProductCategoryCompositeDTO getProductCategoryDetails(String productId, String siteId, String bbbChannel) {
		
		LOGGER.info("Getting Product Details with ProductID - {}, siteId - {}, bbbChannel - {} ", productId, siteId, bbbChannel);
		
		ProductCompositeDTO productCompositeDTO = fetchProductData(productId, siteId, bbbChannel);
		ProductCategoryCompositeDTO productCategoryCompositeDTO = null;
		if(null != productCompositeDTO) {
			productCategoryCompositeDTO = fetchProductCategoryDetails(siteId, bbbChannel, productCompositeDTO);
		}
		return productCategoryCompositeDTO;
	}

	private ProductCategoryCompositeDTO fetchProductCategoryDetails(String siteId, String bbbChannel,
			ProductCompositeDTO productCompositeDTO) {

		ProductCategoryCompositeDTO  categoryCompositeDTO = new ProductCategoryCompositeDTO();
		categoryCompositeDTO.setName(productCompositeDTO.getName());
		categoryCompositeDTO.setProductId(productCompositeDTO.getProductId());
		if (CollectionUtils.isNotEmpty(productCompositeDTO.getCategoryList())) {
			
			List<String> categoryIds = extractCategoryIds(productCompositeDTO.getCategoryList());
			List<CategoryDTO> categoryDTOList = categoryIntegrationService.getCategories(bbbChannel, siteId, categoryIds);
			filterActiveCategories(categoryDTOList);
			if (CollectionUtils.isNotEmpty(categoryDTOList)) {
				categoryCompositeDTO.setProductId(productCompositeDTO.getProductId());
				categoryCompositeDTO.setName(productCompositeDTO.getName());
				categoryCompositeDTO.setCategoryList(categoryDTOList);
			} else {
				String message = MessageFormat.format(BBBProductDetailsConstants.PRODUCT_DETAIL_ERROR_CATEGORY_ENTITY_NOT_FOUND, categoryIds.toString(),productCompositeDTO.getProductId(), siteId);
				LOGGER.info(LOGGER_PLACE_HOLDERS, BBBErrorCodeConstants.EC_ENTITY_NOT_FOUND, message);
				throw new DataNotFoundException(message);
			}
		}
	
		return categoryCompositeDTO;
	}

	private void filterActiveCategories(List<CategoryDTO> categoryDTOList) {
		if (CollectionUtils.isNotEmpty(categoryDTOList)) {
			Iterator<CategoryDTO> categoryIterator = categoryDTOList.iterator();
			while (categoryIterator.hasNext()) {
				CategoryDTO categoryDTO = categoryIterator.next();
				if(!categoryDTO.getIsActive()) {
					LOGGER.info("Ignoring Category id {} since it not active", categoryDTO.getCategoryId());
					categoryIterator.remove();
				}
			}
		}
	}

	private List<String> extractCategoryIds(List<CategorySummaryDTO> categoryList) {
		Collections.sort(categoryList);
		List<String> categoryIds = new ArrayList<>();
		for(CategorySummaryDTO category : categoryList) {
			categoryIds.add(category.getCategoryId());
		}
		return categoryIds;
	}
	
/*added by aakash*/
	
	@Override
	public Product getProductDetails(String productId) {
		
		//obtain the params - the key/value of productIds and values in (1060897527 or 1060898999)
		List<String> lstProductIds = new ArrayList<String>();
		lstProductIds.add(productId);
		Map<String, Object> params = getParams(lstProductIds);
		
		LOGGER.info("Getting Product Details with ProductID - {} ", productId);
		
		Product productSolr=null;
		ProductWrapper<ProductResponse> productWrapper = null;
		//ProductResponseDTO productResponseDto=null;
	
		
		/*
		baseServiceDTO.setResponseSentTime(Instant.now());
		baseServiceDTO.setRequestReceivedTime(requestReceivedTime);
		baseServiceDTO.setServiceStatus(ServiceStatus.SUCCESS);
		baseServiceDTO.setPageSize(1);
		baseServiceDTO.setPageFrom(1);
		baseServiceDTO.setPageTo(1);
		baseServiceDTO.addTraceInfo(ProductDetailsController.this.spanAccessor);
		*/
		
		//make a call here to get the response from the url
		//later may move to a repository or other service
		
		//to check we need product dto or string
		productWrapper= this.genericDownloaderDAO
				.downloadDataFromUrl(this.fusionProductsDataFetchUrlPattern, HttpMethod.GET, params,
						fusionAccessUsername, fusionAccessPassword, RESPONSE_TYPE);
		
		//obtain the data specific to a product from the wrapper solr dto
		if(productWrapper.getData()!=null)
			productSolr = fetchSolrProductInfo(productWrapper);
		
		
		return productSolr;
		
		//baseServiceDTO.setData(productResponseDto);
		//return new ResponseEntity<>(baseResponseDto, HttpStatus.OK);
	}
	
	
	/**
	 * Fetch solr product info.
	 *
	 * @param productWrapperSolrDTO the product wrapper solr dto
	 * @return the product solr DTO
	 */
	public Product fetchSolrProductInfo(ProductWrapper<ProductResponse> productWrapper){
		
		List<Product> lstProductSolrDtos;
		
		if(null!=productWrapper){
			
			ProductResponse productResponseSolrDTO =productWrapper.getData();
			
			//check whether data pertaining to a product exist or not
			lstProductSolrDtos=productResponseSolrDTO.getProducts();
			
			//return the first element of the list
			if(CollectionUtils.isNotEmpty(lstProductSolrDtos))
				return lstProductSolrDtos.get(0);
			
			
		}
		
		return null;
		
	}
	
	
	/**
	 * Gets the products details.
	 *
	 * @param productId the product id
	 * @return the products details
	 */
	/*
	@Override
	public ResponseEntity<BaseServiceDTO<List<ProductDTO>>> getProductsDetails(List<String> productIds) {
		LOGGER.info("Getting Product Details with ProductID - {} ", productIds);
		
		List<ProductDTO> lstProductDTO=null;
		BaseServiceDTO<List<ProductDTO>> baseServiceDTO = new BaseServiceDTO<>();
		baseServiceDTO.setData(lstProductDTO);
		
		//obtain the params - the key/value of productIds and values in (1060897527 or 1060898999)
		Map<String, Object> params = getParams(productIds);
		
		
//		baseServiceDTO.setResponseSentTime(Instant.now());
//		baseServiceDTO.setRequestReceivedTime(requestReceivedTime);
//		baseServiceDTO.setServiceStatus(ServiceStatus.SUCCESS);
//		baseServiceDTO.setPageSize(1);
//		baseServiceDTO.setPageFrom(1);
//		baseServiceDTO.setPageTo(1);
//		baseServiceDTO.addTraceInfo(ProductDetailsController.this.spanAccessor);
		
		
		//make a call here to get the response from the url of type multiple product ids
		//later may move to a repository or other service
		
		
		 String result = this.genericDownloaderService
				.downloadDataFromUrl(this.fusionProductsDataFetchUrlPattern, HttpMethod.GET, params,
						fusionAccessUsername, fusionAccessPassword, String.class);
		
		 
		 //need to parse the result here before we pass the result back
		 //parseResponseFromSolr
		 baseServiceDTO.setData(lstProductDTO);
		return new ResponseEntity<>(baseServiceDTO, HttpStatus.OK);
	}*/

	/*
	public Map<String, Object> getParams(List<String> productIds){
		 
		Map<String, Object> params = new HashMap<>();
		
		
		StringBuilder productIdsQueryString = new StringBuilder();
		if (CollectionUtils.isNotEmpty(productIds)) {
			int size = productIds.size();
			for (int i = 0; i < size; i++) {
				if (i == 0) {
					productIdsQueryString.append("(");
				}
				String productId = productIds.get(i);
				
				if (StringUtils.isNotBlank(productId)) {
					if (i == 0) {
						productIdsQueryString.append(productId);
					} else {
						productIdsQueryString.append(" or ").append(productId);
					}
				}
			}

			if (productIdsQueryString.length() > 1) {
				productIdsQueryString.append(")");
				// LOGGER.debug("productIds query for fusion is {}",
				// productIdsQueryString.toString());
				params.put("productIds", productIdsQueryString.toString());

			}
		}
		return params;
	}
*/

	/**
	 * Gets the params.
	 *
	 * @return the params
	 */

	public Map<String, Object> getParams(List<String> productIds){
		 
		Map<String, Object> params = new HashMap<>();
		
		
		StringBuilder productIdsQueryString = new StringBuilder();
		if (CollectionUtils.isNotEmpty(productIds)) {
			int size = productIds.size();
			
			for (int i = 0; i < size; i++) {
				String productId = productIds.get(i);
				
				if (StringUtils.isNotBlank(productId))
					productIdsQueryString.append(productId);
				
				if (i < size-1) {
					productIdsQueryString.append("|");
				}
					
				
			}

				params.put("productIds", productIdsQueryString.toString());

			
		}
		return params;
	}
	
	/**
	 * Parses the response from solr.
	 *
	 * @param completableFuture the completable future
	 */
	public void parseResponseFromSolr(String response){
		

	if (StringUtils.isNotBlank(response)) {
		try {
			JsonNode rootNode = jacksonObjectMapper.readTree(response);
			JsonNode responseNode = rootNode.get("response");
			if(null != responseNode) {
				JsonNode docsNode = responseNode.get("docs");
				if (null != docsNode) {
					Map<String, String> productDataMap = new HashMap<>();
					int docsSize = docsNode.size();

					for (int i = 0; i < docsSize; i++) {
						JsonNode doc = docsNode.get(i);
						JsonNode productIdNode = doc.get("PRODUCT_ID");
						String productId = productIdNode.asText();
						if (StringUtils.isNotEmpty(productId)) {
							productDataMap.put("Product-ID-" + productId, doc.toString());
						}
					}

				}
			}
		} catch (IOException e) {
			LOGGER.error("Error parsing the respone to JSON.", e);
		}
	}
	
}

	/* (non-Javadoc)
	 * @see com.bbb.composite.product.details.services.ProductDetailsService#getProductsDetails(java.util.List)
	 */
	@Override
	public ResponseEntity<com.bbb.core.dto.BaseServiceDTO<List<Product>>> getProductsDetails(List<String> productIds) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
