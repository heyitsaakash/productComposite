package com.bbb.composite.product.details.services.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.bbb.composite.product.details.dto.sku.SkuDTO;
import com.bbb.composite.ribbon.config.SkuMicroserviceRibbonConfiguration;
import com.bbb.core.cache.dto.CacheableDTO;
import com.bbb.core.cache.manager.CoreCacheManager;
import com.bbb.core.dto.BaseServiceDTO;
import com.bbb.core.integration.rest.CoreRestTemplate;
import com.bbb.core.utils.CoreUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * SkuIntegrationService Implementation class
 * 
 * @author psh111
 *
 */
@Service("com.bbb.composite.product.details.services.SkuIntegrationServiceImpl")
@RibbonClient(name = SkuIntegrationServiceImpl.CLIENT_ID, configuration = SkuMicroserviceRibbonConfiguration.class)
public class SkuIntegrationServiceImpl implements SkuIntegrationService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SkuIntegrationServiceImpl.class);
	
	public static final String CLIENT_ID = "sku-microservice";
	
	private static final ParameterizedTypeReference<BaseServiceDTO<List<SkuDTO>>> RESPONSE_TYPE = new ParameterizedTypeReference<BaseServiceDTO<List<SkuDTO>>>() {};
	
	@Autowired
    @Qualifier("coreRestTemplate")
    private CoreRestTemplate asyncRestTemplate;

	@Autowired
	private LoadBalancerClient loadBalancer;

	@Autowired
    private CoreCacheManager coreCacheManager;
	
	@Autowired
	@Qualifier("com.bbb.composite.product.details.services.client.IntegrationServiceUtilsImpl")
	private IntegrationServiceUtils integrationServiceUtils;
	
	/**
	 * Fetch SKU Details based on channel, siteId and ChildSkus
	 * 
	 * @param bbbChannel channel value
	 * @param siteId site Id
	 * @param childSkus list of ChildSkus
	 * @return value as List of SkuDto
	 */
	@Override
	@HystrixCommand(commandKey="getSkuDetails", groupKey="product-composite-sku-ms", fallbackMethod="getSkuDetailsFallback"
						, commandProperties={
								@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="30000")
						}, threadPoolProperties = {
								@HystrixProperty(name = "coreSize", value = "20"),
								@HystrixProperty(name = "maximumSize", value = "40"),
								@HystrixProperty(name = "maxQueueSize", value = "-1"),
								@HystrixProperty(name = "queueSizeRejectionThreshold", value = "500"),
								@HystrixProperty(name = "allowMaximumSizeToDivergeFromCoreSize", value = "true"),

					})
	public List<SkuDTO> getSkuDetails(String bbbChannel, String siteId, List<String> childSkus) {
		List<SkuDTO> skuDTOList = this.getFromCache(siteId, childSkus, bbbChannel);
		if(CollectionUtils.isNotEmpty(skuDTOList)) {
			return skuDTOList;
		}
		//else get from endpoint
		String uriTemplate = "/sku/site/{siteId}/sku/{childSkus}";
		Map<String, String> queryParams = null;
		List<Object> argsList = new ArrayList<>(3);
		argsList.add(siteId);
		argsList.add(childSkus);
		if (StringUtils.isNotBlank(bbbChannel)) {
			queryParams = new HashMap<>();
			queryParams.put("channel","{bbbChannel}");
			argsList.add(bbbChannel);
		}
		String url = CoreUtils.constructLoadbalancedUrl(loadBalancer, CLIENT_ID, uriTemplate, queryParams);
		
		ResponseEntity<BaseServiceDTO<List<SkuDTO>>> responseEntity = this.asyncRestTemplate.exchange(url,
				HttpMethod.GET, null, RESPONSE_TYPE, CoreUtils.toArray(argsList));
		
		BaseServiceDTO<List<SkuDTO>> baseServiceDTO = integrationServiceUtils.extractResponseBody(responseEntity, childSkus, siteId, bbbChannel);
		
		skuDTOList = integrationServiceUtils.extractDTOFromResponseBody(baseServiceDTO, uriTemplate, childSkus, siteId, bbbChannel);

		addToCache(siteId, childSkus, bbbChannel, baseServiceDTO);
		
		return skuDTOList;
	}
	/**
	 * FallBack method used for circuit breaker implementation.
	 * 
	 * @param bbbChannel channel value
	 * @param siteId site Id
	 * @param childSkus list of skus
	 * @param t Throwable obj
	 * @return value as null after logging the error messsage and throwable instance.
	 */
	public List<SkuDTO> getSkuDetailsFallback(String bbbChannel, String siteId, List<String> childSkus, Throwable t) {
		LOGGER.error("Hystrix fallback called. Failed to get Sku data for skuIds{}, siteId {}, channel {}",  childSkus , siteId, bbbChannel, t);
		return Collections.emptyList();
	}
	
	private void addToCache(String siteId, List<String> childSkus, String bbbChannel, BaseServiceDTO<List<SkuDTO>> baseServiceDTO) {
		String key = this.getCacheKey(siteId, childSkus, bbbChannel);
		if((null != baseServiceDTO) && (null != baseServiceDTO.getData())) {
			LOGGER.info("Adding BaseServiceDTO<List<SkuDTO>> in cache for key '{}'", key);
			CacheableDTO<BaseServiceDTO<List<SkuDTO>>> cacheableDTO = new CacheableDTO<>();
			cacheableDTO.setKey(key);
			cacheableDTO.setData(baseServiceDTO);

			coreCacheManager.addToCache(cacheableDTO);
		} else {
			LOGGER.warn("Skipped adding null BaseServiceDTO<List<SkuDTO>> in cache for key '{}'", key);
		}
	}
	
	private List<SkuDTO> getFromCache(String siteId, List<String> childSkus, String bbbChannel) {
		List<SkuDTO> retVal = null;
		String key = this.getCacheKey(siteId, childSkus, bbbChannel);
		LOGGER.info("Checking cache for BaseServiceDTO<List<SkuDTO>> against key '{}'", key);
		CacheableDTO<BaseServiceDTO<List<SkuDTO>>> cacheableDTO = coreCacheManager.getFromCacheList(key, SkuDTO.class);
		if(null != cacheableDTO) {
			BaseServiceDTO<List<SkuDTO>> baseServiceDTO = cacheableDTO.getData();
			retVal = baseServiceDTO.getData();
		}
		return retVal;
	}
	
	private String getCacheKey(String siteId, List<String> childSkus, String bbbChannel) {
		StringBuilder sb = new StringBuilder();
		sb.append(CLIENT_ID).append("-siteId-").append(siteId);
		if(CollectionUtils.isNotEmpty(childSkus)) {
			sb.append("-skuIds-");
			for (int i=0; i< childSkus.size(); i++) {
				String sku = childSkus.get(i);
				sb.append(sku);
				if(i < (childSkus.size() - 1)) {
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
