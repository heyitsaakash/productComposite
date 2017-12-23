package com.bbb.composite.product.details.services.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bbb.composite.product.details.dto.product.ProductDTO;
import com.bbb.composite.ribbon.config.ProductMicroserviceRibbonConfiguration;
import com.bbb.core.cache.dto.CacheableDTO;
import com.bbb.core.cache.manager.CoreCacheManager;
import com.bbb.core.dto.BaseServiceDTO;
import com.bbb.core.exceptions.DataNotFoundException;
import com.bbb.core.integration.rest.CoreRestTemplate;
import com.bbb.core.utils.CoreUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
/**
 * ProductIntegrationService Implementation class
 * 
 * @author psh111
 *
 */
@Service("com.bbb.composite.product.details.services.ProductIntegrationServiceImpl")
@RibbonClient(name = ProductIntegrationServiceImpl.CLIENT_ID, configuration = ProductMicroserviceRibbonConfiguration.class)
public class ProductIntegrationServiceImpl implements ProductIntegrationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductIntegrationServiceImpl.class);

	public static final String CLIENT_ID = "product-microservice";

	private static final ParameterizedTypeReference<BaseServiceDTO<ProductDTO>> RESPONSE_TYPE = new ParameterizedTypeReference<BaseServiceDTO<ProductDTO>>() {
	};

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
	 * Fetch product details based on productId , site Id and bbbchannel
	 * 
	 * @param productId product Id
	 * @param siteId site Id
	 * @param bbbChannel channel value
	 * @return value as ProductDTO
	 */
	@Override
	@HystrixCommand(commandKey = "getProductDetails", groupKey = "product-composite-product-ms", fallbackMethod = "getProductDetailsFallback"
		, commandProperties = {
				@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000") 
		}, threadPoolProperties = {
				@HystrixProperty(name = "coreSize", value = "20"),
				@HystrixProperty(name = "maximumSize", value = "40"),
				@HystrixProperty(name = "maxQueueSize", value = "-1"),
				@HystrixProperty(name = "queueSizeRejectionThreshold", value = "500"),
				@HystrixProperty(name = "allowMaximumSizeToDivergeFromCoreSize", value = "true"),

	})
	public ProductDTO getProductDetails(String productId, String siteId, String bbbChannel) {
		if (productId != null && "10417205479".equalsIgnoreCase(productId)) {
			throw new DataNotFoundException("Thrown Exception Deliberately for Product ID :" + productId);
		}
		ProductDTO productDTO = this.getFromCache(productId, siteId, bbbChannel);
		if (null != productDTO) {
			return productDTO;
		}
		// else get it from microservice
		Map<String, String> queryParams = null;
		List<Object> argsList = new ArrayList<>(3);
		argsList.add(siteId);
		argsList.add(productId);
		if (StringUtils.isNotBlank(bbbChannel)) {
			queryParams = new HashMap<>();
			queryParams.put("channel", "{bbbChannel}");
			argsList.add(bbbChannel);
		}
		String uriTemplate = "/product/site/{siteId}/product/{productId}";

		String url = CoreUtils.constructLoadbalancedUrl(loadBalancer, CLIENT_ID, uriTemplate, queryParams);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
		HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

		ResponseEntity<BaseServiceDTO<ProductDTO>> responseEntity = this.coreRestTemplate.exchange(url, HttpMethod.GET,
				entity, RESPONSE_TYPE, CoreUtils.toArray(argsList));

		BaseServiceDTO<ProductDTO> baseServiceDTO = integrationServiceUtils.extractResponseBody(responseEntity,
				productId, siteId, bbbChannel);

		productDTO = integrationServiceUtils.extractDTOFromResponseBody(baseServiceDTO, uriTemplate, productId, siteId,
				bbbChannel);

		addToCache(productId, siteId, bbbChannel, baseServiceDTO);

		return productDTO;
	}

	/**
	 * FallBack method used for circuit breaker implementation.
	 * 
	 * @param productId product Id
	 * @param siteId site Id
	 * @param bbbChannel channel value
	 * @param t Throwable obj
	 * @return value as null after logging the error messsage and throwable instance.
	 */
	@SuppressWarnings("squid:S1172")
	public ProductDTO getProductDetailsFallback(String productId, String siteId, String bbbChannel, Throwable t) {
		LOGGER.error("Hystrix fallback called.", t);
		return null;
	}

	private ProductDTO getFromCache(String productId, String siteId, String bbbChannel) {
		ProductDTO retVal = null;
		String key = this.getCacheKey(productId, siteId, bbbChannel);
		LOGGER.info("Checking cache for BaseServiceDTO<ProductDTO> against key '{}'", key);
		CacheableDTO<BaseServiceDTO<ProductDTO>> cacheableDTO = coreCacheManager.getFromCacheSingle(key,
				ProductDTO.class);
		if (null != cacheableDTO) {
			BaseServiceDTO<ProductDTO> baseServiceDTO = cacheableDTO.getData();
			retVal = baseServiceDTO.getData();
		}
		return retVal;
	}

	private void addToCache(String productId, String siteId, String bbbChannel,
			BaseServiceDTO<ProductDTO> baseServiceDTO) {
		String key = this.getCacheKey(productId, siteId, bbbChannel);
		if ((null != baseServiceDTO) && (null != baseServiceDTO.getData())) {
			LOGGER.info("Adding BaseServiceDTO<ProductDTO> in cache for key '{}'", key);
			CacheableDTO<BaseServiceDTO<ProductDTO>> cacheableDTO = new CacheableDTO<>();
			cacheableDTO.setKey(key);
			cacheableDTO.setData(baseServiceDTO);

			coreCacheManager.addToCache(cacheableDTO);
		} else {
			LOGGER.warn("Skipped adding null ProductDTO in cache for key '{}'", key);
		}
	}

	private String getCacheKey(String productId, String siteId, String bbbChannel) {
		StringBuilder sb = new StringBuilder();
		sb.append(CLIENT_ID).append("-siteId-").append(siteId).append("-productId-").append(productId);
		if (StringUtils.isNotBlank(bbbChannel)) {
			sb.append("-bbbChannel-").append(bbbChannel);
		}
		return sb.toString();
	}
}
