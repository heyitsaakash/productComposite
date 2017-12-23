package com.bbb.composite.product.details.services.client;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import com.bbb.composite.product.details.dto.CategoryDTO;
import com.bbb.core.cache.dto.CacheableDTO;
import com.bbb.core.cache.manager.CoreCacheManager;
import com.bbb.core.dto.BaseServiceDTO;
import com.bbb.core.dto.ServiceStatus;
import com.bbb.core.integration.rest.CoreRestTemplate;
import com.bbb.core.utils.CoreUtils;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:bootstrap-test_local.properties")
public class CategoryIntegrationServiceImplMockTest {

	@Autowired
	@InjectMocks
	CategoryIntegrationServiceImpl categoryIntegrationService = new CategoryIntegrationServiceImpl();

	@Mock
	private CoreRestTemplate asyncRestTemplate;
	@Mock
	private LoadBalancerClient loadBalancer;

	@Mock
	private CoreCacheManager coreCacheManager;
	
	@Spy
	private IntegrationServiceUtilsImpl integrationServiceUtils;
	
	@Mock
	private CoreUtils coreUtils;
	
	private static final ParameterizedTypeReference<BaseServiceDTO<List<CategoryDTO>>> RESPONSE_TYPE = new ParameterizedTypeReference<BaseServiceDTO<List<CategoryDTO>>>() {
	};

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testGetCategories() {
		String siteId = "siteId_2";
		String channel = "web";
		List<String> childCategories = new ArrayList<>();
		childCategories.add("AA");
		childCategories.add("ACD");
		childCategories.add("AC");
		String key = "category-microservice-siteId-siteId_2-categoryIds-AA-ACD-AC-bbbChannel-web";
		String url = "http://local:12331/category/site/{siteId}/category/{categoryId}?channel={bbbChannel}";
		List<Object> argsList = new ArrayList<>(3);
		argsList.add(siteId);
		argsList.add(childCategories);
		Mockito.when(coreCacheManager.getFromCacheList(key, CategoryDTO.class)).thenReturn(null);
		Mockito.when(loadBalancer.choose(Mockito.anyString())).thenReturn(getServiceInstance());
		Mockito.when(asyncRestTemplate.exchange(url, HttpMethod.GET, null, RESPONSE_TYPE, CoreUtils.toArray(argsList)))
				.thenReturn(createDummyData(siteId, childCategories));
		List<CategoryDTO> categoryDTOList = categoryIntegrationService.getCategories(channel, siteId, childCategories);
		Assert.assertNotNull(categoryDTOList);
		Assert.assertEquals("AA", categoryDTOList.get(0).getCatalogId());
		Assert.assertEquals("siteId_2", categoryDTOList.get(0).getSiteId());
		Assert.assertEquals("ACD", categoryDTOList.get(1).getCatalogId());
		Assert.assertEquals("siteId_2", categoryDTOList.get(1).getSiteId());
		Assert.assertEquals("AC", categoryDTOList.get(2).getCatalogId());
		Assert.assertEquals("siteId_2", categoryDTOList.get(2).getSiteId());
	}
	
	@SuppressWarnings({"unchecked" })
	@Test
	public void testGetCategoriesFromCache() {
		String siteId = "siteId_2";
		String channel = null;
		List<String> childCategories = new ArrayList<>();
		childCategories.add("AA");
		childCategories.add("ACD");
		childCategories.add("AC");
		String key = "category-microservice-siteId-siteId_2-categoryIds-AA-ACD-AC";
		String url = "http://local:12331/category/site/{siteId}/category/{categoryId}";
		List<Object> argsList = new ArrayList<>(3);
		argsList.add(siteId);
		argsList.add(childCategories);
		Mockito.when(coreCacheManager.getFromCacheList(key, CategoryDTO.class)).thenReturn(getDataFromCache(key, siteId, childCategories));
		List<CategoryDTO> categoryDTOList = categoryIntegrationService.getCategories(channel, siteId, childCategories);
		Assert.assertNotNull(categoryDTOList);
		Assert.assertEquals("AA", categoryDTOList.get(0).getCatalogId());
		Assert.assertEquals("siteId_2", categoryDTOList.get(0).getSiteId());
		Assert.assertEquals("ACD", categoryDTOList.get(1).getCatalogId());
		Assert.assertEquals("siteId_2", categoryDTOList.get(1).getSiteId());
		Assert.assertEquals("AC", categoryDTOList.get(2).getCatalogId());
		Assert.assertEquals("siteId_2", categoryDTOList.get(2).getSiteId());
	}
	
	@Test
	public void testGetCategoriesChannelNull() {
		String siteId = "siteId_2";
		String channel = "";
		List<String> childCategories = new ArrayList<>();
		childCategories.add("AA");
		childCategories.add("ACD");
		childCategories.add("AC");
		String key = "category-microservice-siteId-siteId_2-categoryIds-AA-ACD-AC";
		String url = "http://local:12331/category/site/{siteId}/category/{categoryId}";
		List<Object> argsList = new ArrayList<>(3);
		argsList.add(siteId);
		argsList.add(childCategories);
		Mockito.when(coreCacheManager.getFromCacheList(key, CategoryDTO.class)).thenReturn(null);
		Mockito.when(loadBalancer.choose(Mockito.anyString())).thenReturn(getServiceInstance());
		Mockito.when(asyncRestTemplate.exchange(url, HttpMethod.GET, null, RESPONSE_TYPE, CoreUtils.toArray(argsList)))
				.thenReturn(createDummyData(siteId, childCategories));
		List<CategoryDTO> categoryDTOList = categoryIntegrationService.getCategories(channel, siteId, childCategories);
		Assert.assertNotNull(categoryDTOList);
		Assert.assertEquals("AA", categoryDTOList.get(0).getCatalogId());
		Assert.assertEquals("siteId_2", categoryDTOList.get(0).getSiteId());
		Assert.assertEquals("ACD", categoryDTOList.get(1).getCatalogId());
		Assert.assertEquals("siteId_2", categoryDTOList.get(1).getSiteId());
		Assert.assertEquals("AC", categoryDTOList.get(2).getCatalogId());
		Assert.assertEquals("siteId_2", categoryDTOList.get(2).getSiteId());
	}
	
	@SuppressWarnings({ "rawtypes","unchecked" })
	private CacheableDTO getDataFromCache(String key,String siteId,List<String> categoryIds){
		CacheableDTO cacheData=new CacheableDTO<>();
		cacheData.setKey(key);
		cacheData.setData(getCategoryData(siteId,categoryIds));
		return cacheData;
	}
	private BaseServiceDTO<List<CategoryDTO>> getCategoryData(String siteId,List<String> categoryIds){
		BaseServiceDTO<List<CategoryDTO>> baseServiceDTO = new BaseServiceDTO<>();
		baseServiceDTO.setResponseSentTime(Instant.now());
		baseServiceDTO.setServiceStatus(ServiceStatus.SUCCESS);
		baseServiceDTO.setPageSize(1);
		baseServiceDTO.setPageFrom(1);
		baseServiceDTO.setPageTo(1);
		List<CategoryDTO> categoryDTOs = new ArrayList<>();
		for (String categories : categoryIds) {
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setSiteId(siteId);
			categoryDTO.setCatalogId(categories);
			categoryDTOs.add(categoryDTO);
		}
		baseServiceDTO.setData(categoryDTOs);
		return baseServiceDTO;
	}
	
	private ResponseEntity<BaseServiceDTO<List<CategoryDTO>>> createDummyData(String siteId, List<String> categoryIds) {
		return new ResponseEntity<BaseServiceDTO<List<CategoryDTO>>>(getCategoryData(siteId,categoryIds), HttpStatus.OK);
	}

	private ServiceInstance getServiceInstance() {
		ServiceInstance instance = new ServiceInstance() {

			@Override
			public boolean isSecure() {
				return false;
			}

			@Override
			public URI getUri() {
				return null;
			}

			@Override
			public String getServiceId() {
				return null;
			}

			@Override
			public int getPort() {
				return 12331;
			}

			@Override
			public Map<String, String> getMetadata() {
				return null;
			}

			@Override
			public String getHost() {
				return "local";
			}
		};
		return instance;
	}
}
