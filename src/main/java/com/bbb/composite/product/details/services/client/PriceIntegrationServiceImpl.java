package com.bbb.composite.product.details.services.client;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bbb.composite.product.details.dto.price.ProductPriceDTO;
import com.bbb.composite.product.details.dto.price.SkuPriceDTO;
import com.bbb.composite.ribbon.config.PriceMicroserviceRibbonConfiguration;
import com.bbb.core.cache.dto.CacheableDTO;
import com.bbb.core.cache.manager.CoreCacheManager;
import com.bbb.core.dto.BaseServiceDTO;
import com.bbb.core.integration.rest.CoreRestTemplate;
import com.bbb.core.utils.CoreUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * PriceIntegrationService Implementation class
 * 
 * @author psh111
 *
 */
@Service("com.bbb.composite.product.details.services.PriceIntegrationServiceImpl")
@RibbonClient(name = PriceIntegrationServiceImpl.CLIENT_ID, configuration = PriceMicroserviceRibbonConfiguration.class)
public class PriceIntegrationServiceImpl implements PriceIntegrationService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PriceIntegrationServiceImpl.class);
	
	public static final String CLIENT_ID = "price-microservice";
	
	private static final ParameterizedTypeReference<BaseServiceDTO<ProductPriceDTO>> PRODUCT_PRICE_RESPONSE_TYPE = new ParameterizedTypeReference<BaseServiceDTO<ProductPriceDTO>>() {};
	private static final ParameterizedTypeReference<BaseServiceDTO<List<SkuPriceDTO>>> SKU_PRICE_RESPONSE_TYPE = new ParameterizedTypeReference<BaseServiceDTO<List<SkuPriceDTO>>>() {};
	
	@Autowired
    @Qualifier("coreRestTemplate")
    private CoreRestTemplate coreRestTemplate; 
    
    @Autowired
    private LoadBalancerClient loadBalancer;
	
    @Autowired
    private CoreCacheManager coreCacheManager;
    
    @Autowired
    @Qualifier("com.bbb.composite.product.details.services.client.IntegrationServiceUtilsImpl")
    private IntegrationServiceUtils integrationServiceUtils;
    
    
    /**
	 * Fetch product price details based on bbbchannel siteId and productId.
	 * 
	 * @param bbbChannel channel value
	 * @param siteId site id
	 * @param productId product id
	 * @return value as productPriceDTO
	 */
	@Override
	@HystrixCommand(commandKey="getProductPriceDetails", groupKey="product-composite-price-ms", fallbackMethod="getProductPriceDetailsFallback"
						, commandProperties={
								@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="30000")
						}, threadPoolProperties = {
								@HystrixProperty(name = "coreSize", value = "20"),
								@HystrixProperty(name = "maximumSize", value = "40"),
								@HystrixProperty(name = "maxQueueSize", value = "-1"),
								@HystrixProperty(name = "queueSizeRejectionThreshold", value = "500"),
								@HystrixProperty(name = "allowMaximumSizeToDivergeFromCoreSize", value = "true"),

					})
	public ProductPriceDTO getProductPriceDetails(String bbbChannel, String siteId, String productId) {
		ProductPriceDTO productPriceDTO = this.getFromCache(siteId, productId, bbbChannel);
		if(null != productPriceDTO) {
			return productPriceDTO;
		}
		//else fetch it form endpoint
		String uriTemplate = "/price/site/{siteId}/product/{productId}";
		String url = CoreUtils.constructLoadbalancedUrl(loadBalancer, CLIENT_ID, uriTemplate);
		
		ResponseEntity<BaseServiceDTO<ProductPriceDTO>> responseEntity = this.coreRestTemplate.exchange(url,
				HttpMethod.GET, null, PRODUCT_PRICE_RESPONSE_TYPE, siteId, productId);
		
		BaseServiceDTO<ProductPriceDTO> baseServiceDTO = integrationServiceUtils.extractResponseBody(responseEntity, productId, siteId, bbbChannel);
		
		productPriceDTO = integrationServiceUtils.extractDTOFromResponseBody(baseServiceDTO, uriTemplate, productId, siteId, bbbChannel);
		
		addToCache(siteId, productId, bbbChannel, baseServiceDTO);
		
		return productPriceDTO;
	}
	
	/**
	 * FallBack method used for circuit breaker implementation.
	 * 
	 * @param bbbChannel channel value
	 * @param siteId site Id
	 * @param productId product Id
	 * @param t Throwable obj
	 * @return value as null after logging the error messsage and throwable instance.
	 */
	@SuppressWarnings("squid:S1172")
	public ProductPriceDTO getProductPriceDetailsFallback(String bbbChannel, String siteId, String productId, Throwable t) {
		LOGGER.error("Hystrix fallback called.", t);
		return null;
	}
	
	/**
	 * Fetch skuPriceDetails based on bbbchannel siteId and list of sku's
	 * 
	 * @param bbbChannel channel value
	 * @param siteId site Id
	 * @param skus list of sku's
	 * @return value as List of SkuPriceDTO
	 */
	@Override
	@HystrixCommand(commandKey="getSkuPriceDetails", groupKey="product-composite-price-ms", fallbackMethod="getSkuPriceDetailsFallback"
						, commandProperties={
								@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="30000")
						}, threadPoolProperties = {
								@HystrixProperty(name = "coreSize", value = "20"),
								@HystrixProperty(name = "maximumSize", value = "40"),
								@HystrixProperty(name = "maxQueueSize", value = "-1"),
								@HystrixProperty(name = "queueSizeRejectionThreshold", value = "500"),
								@HystrixProperty(name = "allowMaximumSizeToDivergeFromCoreSize", value = "true"),

					})
	public List<SkuPriceDTO> getSkuPriceDetails(String bbbChannel, String siteId, List<String> skus) {
		List<SkuPriceDTO> skuPriceDTOList = this.getFromCache(siteId, skus, bbbChannel);
		if(CollectionUtils.isNotEmpty(skuPriceDTOList)) {
			return skuPriceDTOList;
		}
		//else get it from microservice
		String uriTemplate = "/price/site/{siteId}/sku/{skuId}";
		String url = CoreUtils.constructLoadbalancedUrl(loadBalancer, CLIENT_ID, uriTemplate);
		ResponseEntity<BaseServiceDTO<List<SkuPriceDTO>>> responseEntity = this.coreRestTemplate
				.exchange(url, HttpMethod.GET, null, SKU_PRICE_RESPONSE_TYPE, siteId, skus);
		
		BaseServiceDTO<List<SkuPriceDTO>> baseServiceDTO = integrationServiceUtils.extractResponseBody(responseEntity, skus, siteId, bbbChannel);
		
		skuPriceDTOList = integrationServiceUtils.extractDTOFromResponseBody(baseServiceDTO, uriTemplate, skus, siteId, bbbChannel);

		addToCache(siteId, skus, bbbChannel, baseServiceDTO);
		
		return skuPriceDTOList;
	}
	/**
	 * FallBack method used for circuit breaker implementation.
	 * 
	 * @param bbbChannel channel value
	 * @param siteId site Id
	 * @param skus List of SKU's
	 * @param t Throwable obj
	 * @return value as EmptyList after logging the error messsage and throwable instance.
	 */
	public List<SkuPriceDTO> getSkuPriceDetailsFallback(String bbbChannel, String siteId, List<String> skus, Throwable t) {
		LOGGER.error("Hystrix fallback called. Failed to get Sku Price data for siteId {}, skus{} and channel {}", siteId, skus, bbbChannel, t);
		return Collections.emptyList();
	}
	
	private void addToCache(String siteId, String productId, String bbbChannel, BaseServiceDTO<ProductPriceDTO> baseServiceDTO) {
		String key = this.getCacheKey(siteId, productId, bbbChannel);
		if((null != baseServiceDTO) && (null != baseServiceDTO.getData())) {
			LOGGER.info("Adding BaseServiceDTO<ProductPriceDTO> in cache for key '{}'", key);
			CacheableDTO<BaseServiceDTO<ProductPriceDTO>> cacheableDTO = new CacheableDTO<>();
			cacheableDTO.setKey(key);
			cacheableDTO.setData(baseServiceDTO);

			coreCacheManager.addToCache(cacheableDTO);
		} else {
			LOGGER.warn("Skipped adding null BaseServiceDTO<List<SkuPriceDTO>> in cache for key '{}'", key);
		}
	}
	
	private ProductPriceDTO getFromCache(String siteId, String productId, String bbbChannel) {
		ProductPriceDTO retVal = null;
		String key = this.getCacheKey(siteId, productId, bbbChannel);
		LOGGER.info("Checking cache for BaseServiceDTO<ProductPriceDTO> against key '{}'", key);
		CacheableDTO<BaseServiceDTO<ProductPriceDTO>> cacheableDTO = coreCacheManager.getFromCacheSingle(key, ProductPriceDTO.class);
		if(null != cacheableDTO) {
			BaseServiceDTO<ProductPriceDTO> baseServiceDTO = cacheableDTO.getData();
			retVal = baseServiceDTO.getData();
		}
		return retVal;
	}
	
	private String getCacheKey(String siteId, String productId, String bbbChannel) {
		StringBuilder sb = new StringBuilder();
		sb.append(CLIENT_ID).append("-siteId-").append(siteId)
					.append("-productId-").append(productId);
		if (StringUtils.isNotBlank(bbbChannel)) {
			sb.append("-bbbChannel-").append(bbbChannel);
		}
		return sb.toString();
	}
	
	private void addToCache(String siteId, List<String> skus, String bbbChannel, BaseServiceDTO<List<SkuPriceDTO>> baseServiceDTO) {
		String key = this.getCacheKey(siteId, skus, bbbChannel);
		if((null != baseServiceDTO) && (null != baseServiceDTO.getData())) {
			LOGGER.info("Adding BaseServiceDTO<List<SkuPriceDTO>> in cache for key '{}'", key);
			CacheableDTO<BaseServiceDTO<List<SkuPriceDTO>>> cacheableDTO = new CacheableDTO<>();
			cacheableDTO.setKey(key);
			cacheableDTO.setData(baseServiceDTO);

			coreCacheManager.addToCache(cacheableDTO);
		} else {
			LOGGER.warn("Skipped adding null BaseServiceDTO<List<SkuPriceDTO>> in cache for key '{}'", key);
		}
	}
	
	private List<SkuPriceDTO> getFromCache(String siteId, List<String> skus, String bbbChannel) {
		List<SkuPriceDTO> retVal = null;
		String key = this.getCacheKey(siteId, skus, bbbChannel);
		LOGGER.info("Checking cache for BaseServiceDTO<List<SkuPriceDTO>> against key '{}'", key);
		CacheableDTO<BaseServiceDTO<List<SkuPriceDTO>>> cacheableDTO = coreCacheManager.getFromCacheList(key, SkuPriceDTO.class);
		if(null != cacheableDTO) {
			BaseServiceDTO<List<SkuPriceDTO>> baseServiceDTO = cacheableDTO.getData();
			retVal = baseServiceDTO.getData();
		}
		return retVal;
	}
	
	private String getCacheKey(String siteId, List<String> skus, String bbbChannel) {
		StringBuilder sb = new StringBuilder();
		sb.append(CLIENT_ID).append("-siteId-").append(siteId);
		if(CollectionUtils.isNotEmpty(skus)) {
			sb.append("-skuIds-");
			for (int i=0; i< skus.size(); i++) {
				String sku = skus.get(i);
				sb.append(sku);
				if(i < (skus.size() - 1)) {
					sb.append("-");
				}
			}
		}
		if (StringUtils.isNotBlank(bbbChannel)) {
			sb.append("-bbbChannel-").append(bbbChannel);
		}
		return sb.toString();
	}
}
