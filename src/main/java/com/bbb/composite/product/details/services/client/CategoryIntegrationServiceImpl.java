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

import com.bbb.composite.product.details.dto.CategoryDTO;
import com.bbb.composite.ribbon.config.CategoryMicroserviceRibbonConfiguration;
import com.bbb.core.cache.dto.CacheableDTO;
import com.bbb.core.cache.manager.CoreCacheManager;
import com.bbb.core.dto.BaseServiceDTO;
import com.bbb.core.integration.rest.CoreRestTemplate;
import com.bbb.core.utils.CoreUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

/**
 * CategoryIntegrationService Implementation class
 * 
 * @author psh111
 *
 */
@Service("com.bbb.composite.product.details.services.CategoryIntegrationServiceImpl")
@RibbonClient(name = CategoryIntegrationServiceImpl.CLIENT_ID, configuration = CategoryMicroserviceRibbonConfiguration.class)
public class CategoryIntegrationServiceImpl implements CategoryIntegrationService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryIntegrationServiceImpl.class);
	
	public static final String CLIENT_ID = "category-microservice";
	
	private static final ParameterizedTypeReference<BaseServiceDTO<List<CategoryDTO>>> RESPONSE_TYPE = new ParameterizedTypeReference<BaseServiceDTO<List<CategoryDTO>>>() {};
	
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
	 * Function to get the Categories List 
	 * 
	 * @param bbbChannel bbb channel value
	 * @param siteId site Id as String
	 * @param childCategories List of childCategories
	 * @return value as List of CategoryDTO
	 * 
	 */
	@Override
	@HystrixCommand(commandKey="getProductCategoryDetails", groupKey="product-composite-category-ms", fallbackMethod="getCategoryDetailsFallback"
						, commandProperties={
								@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="30000")
						}, threadPoolProperties = {
								@HystrixProperty(name = "coreSize", value = "20"),
								@HystrixProperty(name = "maximumSize", value = "40"),
								@HystrixProperty(name = "maxQueueSize", value = "-1"),
								@HystrixProperty(name = "queueSizeRejectionThreshold", value = "500"),
								@HystrixProperty(name = "allowMaximumSizeToDivergeFromCoreSize", value = "true"),

					})
	public List<CategoryDTO> getCategories(String bbbChannel, String siteId, List<String> childCategories) {
		List<CategoryDTO> categoryDTOList = this.getFromCache(siteId, childCategories, bbbChannel);
		if(CollectionUtils.isNotEmpty(categoryDTOList)) {
			return categoryDTOList;
		}
		//else get from endpoint
		String uriTemplate = "/category/site/{siteId}/category/{categoryId}";
		Map<String, String> queryParams = null;
		List<Object> argsList = new ArrayList<>(3);
		argsList.add(siteId);
		argsList.add(childCategories);
		if (StringUtils.isNotBlank(bbbChannel)) {
			queryParams = new HashMap<>();
			queryParams.put("channel","{bbbChannel}");
		}
		String url = CoreUtils.constructLoadbalancedUrl(loadBalancer, CLIENT_ID, uriTemplate, queryParams);
		
		ResponseEntity<BaseServiceDTO<List<CategoryDTO>>> responseEntity = this.asyncRestTemplate.exchange(url,
				HttpMethod.GET, null, RESPONSE_TYPE, CoreUtils.toArray(argsList));
		
		BaseServiceDTO<List<CategoryDTO>> baseServiceDTO = integrationServiceUtils.extractResponseBody(responseEntity, childCategories, siteId, bbbChannel);
		
		categoryDTOList = integrationServiceUtils.extractDTOFromResponseBody(baseServiceDTO, uriTemplate, childCategories, siteId, bbbChannel);

		addToCache(siteId, childCategories, bbbChannel, baseServiceDTO);
		
		return categoryDTOList;
	}
	/**
	 * Fallback method for circuit breaker
	 * 
	 * @param bbbChannel channel value
	 * @param siteId site id
	 * @param childCategories list of chile categories
	 * @param t Throwable object 
	 * @return value as List of categoryDTO
	 */
	public List<CategoryDTO> getCategoryDetailsFallback(String bbbChannel, String siteId, List<String> childCategories, Throwable t) {
		LOGGER.error("Hystrix fallback called. Failed to get Categories data for categorydIds{}, siteId {}, channel {}",  childCategories , siteId, bbbChannel, t);
		return Collections.emptyList();
	}
	
	private void addToCache(String siteId, List<String> childCategories, String bbbChannel, BaseServiceDTO<List<CategoryDTO>> baseServiceDTO) {
		String key = this.getCacheKey(siteId, childCategories, bbbChannel);
		if((null != baseServiceDTO) && (null != baseServiceDTO.getData())) {
			LOGGER.info("Adding BaseServiceDTO<List<CategoryDTO>> in cache for key '{}'", key);
			CacheableDTO<BaseServiceDTO<List<CategoryDTO>>> cacheableDTO = new CacheableDTO<>();
			cacheableDTO.setKey(key);
			cacheableDTO.setData(baseServiceDTO);
			coreCacheManager.addToCache(cacheableDTO);
		} else {
			LOGGER.warn("Skipped adding null BaseServiceDTO<List<CategoryDTO>> in cache for key '{}'", key);
		}
	}
	
	private List<CategoryDTO> getFromCache(String siteId, List<String> childCategories, String bbbChannel) {
		List<CategoryDTO> retVal = null;
		String key = this.getCacheKey(siteId, childCategories, bbbChannel);
		LOGGER.info("Checking cache for BaseServiceDTO<List<CategoryDTO>> against key '{}'", key);
		CacheableDTO<BaseServiceDTO<List<CategoryDTO>>> cacheableDTO = coreCacheManager.getFromCacheList(key, CategoryDTO.class);
		if(null != cacheableDTO) {
			BaseServiceDTO<List<CategoryDTO>> baseServiceDTO = cacheableDTO.getData();
			retVal = baseServiceDTO.getData();
		}
		return retVal;
	}
	
	private String getCacheKey(String siteId, List<String> childCategories, String bbbChannel) {
		StringBuilder sb = new StringBuilder();
		sb.append(CLIENT_ID).append("-siteId-").append(siteId);
		if(CollectionUtils.isNotEmpty(childCategories)) {
			sb.append("-categoryIds-");
			for (int i=0; i< childCategories.size(); i++) {
				String category = childCategories.get(i);
				sb.append(category);
				if(i < (childCategories.size() - 1)) {
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
