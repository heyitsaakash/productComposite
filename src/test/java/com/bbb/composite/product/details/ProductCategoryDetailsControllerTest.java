package com.bbb.composite.product.details;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestExecutionListeners.MergeMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.BodySpec;
import org.springframework.web.client.RestTemplate;

import com.bbb.composite.product.details.dto.AttributeDTO;
import com.bbb.composite.product.details.dto.CategoryDTO;
import com.bbb.composite.product.details.dto.ProductCategoryCompositeDTO;
import com.bbb.composite.product.details.dto.product.BrandDTO;
import com.bbb.composite.product.details.dto.product.ProductDTO;
import com.bbb.composite.product.details.services.client.PriceIntegrationServiceImpl;
import com.bbb.composite.product.details.services.client.ProductIntegrationServiceImpl;
import com.bbb.core.cache.manager.CoreCacheManager;
import com.bbb.core.dto.BaseServiceDTO;
import com.bbb.core.dto.ServiceStatus;
import com.bbb.core.exceptions.DataNotFoundException;
import com.bbb.core.utils.CoreUtils;
/**
 * This Controller provides all product related actions.
 * @author skhur6
 *
 */
@RunWith(SpringRunner.class)	
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class }, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
@TestPropertySource(locations = "classpath:bootstrap-test_local.properties")
public class ProductCategoryDetailsControllerTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	private WebTestClient webTestClient;
	
	@MockBean
	private LoadBalancerClient loadBalancerClient;
	
	@Before
	public void setup() {
		this.webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port)
											.responseTimeout(Duration.ofSeconds(15000)).build();
	}
	
	/*@MockBean
	private CoreRestTemplate coreRestTemplate;*/
	
	@MockBean
	private CoreCacheManager coreCacheManager;
	
	@MockBean
	private RestTemplate defaultRestTemplate;
	
	@Value("${product-microservice.ribbon.listOfServers}")
	private String productMSservers;
	
	@Value("${price-microservice.ribbon.listOfServers}")
	private String priceMSServers;
	
	@Value("${sku-microservice.ribbon.listOfServers}")
	private String skuMSServers;
	
	@Value("${category-microservice.ribbon.listOfServers}")
	private String categoryMSServers;
	
	@Test
	public void testHealthController() {
		String uri = "/health";
		this.webTestClient.get().uri(uri)
				.accept(MediaType.APPLICATION_JSON_UTF8).exchange()
				.expectStatus().isOk();
	}
	
	/**
	 * This method just creates the dummy data.
	 * @return
	 * @throws DataNotFoundException 
	 */
	@Test
	public void getProductCategoryDetailsNoCacheTest() {
		String productId = "productId_4";
		String siteId = "siteId_2";
		String channel = "web";
		List<String> childSKUs = new ArrayList<>();
		childSKUs.add("240");
		childSKUs.add("241");
		
		ParameterizedTypeReference<BaseServiceDTO<ProductCategoryCompositeDTO>> productCompositeType = new ParameterizedTypeReference<BaseServiceDTO<ProductCategoryCompositeDTO>>() {};
		
		mockRedisLoadNotExistant(productId, siteId, channel, childSKUs);
		mockProductServiceAndLB(productId, siteId, childSKUs);
		//mockSkuServiceAndLB(siteId, childSKUs);
		//mockProductPriceServiceAndLB(productId, siteId);
		//mockSkuPriceServiceAndLB(siteId, childSKUs);
		mockCategoryerviceAndLB(siteId, childSKUs);
		String uri = "/product-category-details/site/" + siteId + "/product/" + productId;

		BodySpec<BaseServiceDTO<ProductCategoryCompositeDTO>, ?> bodySpec = this.webTestClient.get().uri(uri)
				.accept(MediaType.APPLICATION_JSON_UTF8).exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody(productCompositeType);

		assertNotNull(bodySpec);
		BaseServiceDTO<ProductCategoryCompositeDTO> responseBody = bodySpec.returnResult().getResponseBody();
		assertNotNull(responseBody);
		assertEquals(responseBody.getServiceStatus(), ServiceStatus.SUCCESS);
		assertEquals(responseBody.getData().getProductId(), productId);
	}
	
	private void mockRedisLoadNotExistant(String productId, String siteId, String bbbChannel, List<String> childSKUs) {
		Mockito.when(coreCacheManager.getFromCacheSingle(Mockito.anyString(), Mockito.any()))
		.thenReturn(null);
	}
	
	
	private void mockProductServiceAndLB(String productId, String siteId, List<String> childSKUs) {
		String productUrl = mockLoadBalancerAndGetUrl(ProductIntegrationServiceImpl.CLIENT_ID, productMSservers
														, "/product/site/{siteId}/product/{productId}");
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		
		List<Object> argsList = new ArrayList<>(3);
		argsList.add(siteId);
		argsList.add(productId);
		ParameterizedTypeReference<BaseServiceDTO<ProductDTO>> RESPONSE_TYPE = new ParameterizedTypeReference<BaseServiceDTO<ProductDTO>>() {};
		Mockito.when(defaultRestTemplate.exchange(productUrl, 
				HttpMethod.GET, 
				entity, 
				RESPONSE_TYPE, 
				CoreUtils.toArray(argsList)))
		.thenReturn(createProductDummyData(productId, childSKUs));
	}
	

	private void mockCategoryerviceAndLB(String siteId, List<String> categoryIds) {
		String priceUrl = mockLoadBalancerAndGetUrl(PriceIntegrationServiceImpl.CLIENT_ID, skuMSServers, "/category/site/{siteId}/category/{categoryIds}");
		List<Object> argsList = new ArrayList<>(3);
		argsList.add(siteId);
		argsList.add(categoryIds);
		ParameterizedTypeReference<BaseServiceDTO<List<CategoryDTO>>> RESPONSE_TYPE = new ParameterizedTypeReference<BaseServiceDTO<List<CategoryDTO>>>() {};
		Mockito.when(defaultRestTemplate.exchange(priceUrl, 
				HttpMethod.GET, 
				null, 
				RESPONSE_TYPE, 
				CoreUtils.toArray(argsList)))
		.thenReturn(createCategoryDummyData(siteId, categoryIds));
	}
	
	private String mockLoadBalancerAndGetUrl(String clientId, String serverHostNPort, String uri) {
		String[] serversList = serverHostNPort.split(",");
		String hostNPort = serversList[0];
		String[] hostNPortArray = hostNPort.split(":");
		String host = hostNPortArray[0]; 
		int port = 0;
		if (hostNPortArray.length > 1) {
			port = Integer.parseInt(hostNPortArray[1]);
		}
		Mockito.when(loadBalancerClient.choose(clientId)).thenReturn(new DefaultServiceInstance(clientId, host, port, false));
		return "http://" + host +":" + port + uri;
	}
	
	private ResponseEntity<BaseServiceDTO<ProductDTO>> createProductDummyData(String productId, List<String> childSKUs) {
		ProductDTO productDTO = createProductData(productId, childSKUs);
		BaseServiceDTO<ProductDTO> baseServiceDTO = new BaseServiceDTO<>();
		baseServiceDTO.setData(productDTO);
		baseServiceDTO.setResponseSentTime(Instant.now());
		baseServiceDTO.setServiceStatus(ServiceStatus.SUCCESS);
		baseServiceDTO.setPageSize(1);
		baseServiceDTO.setPageFrom(1);
		baseServiceDTO.setPageTo(1);
		return new ResponseEntity<BaseServiceDTO<ProductDTO>>(baseServiceDTO, HttpStatus.OK);
	}
	
	private ProductDTO createProductData(String productId, List<String> childSKUs) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setProductId(productId);
		productDTO.setChildSKUs(childSKUs);
		
		BrandDTO brandDto = new BrandDTO();
		brandDto.setBrandId("12432");
		brandDto.setBrandDesc("Ralph Lauren");
		brandDto.setBrandImage("/Image");
		brandDto.setBrandName("Ralph Lauren");
		productDTO.setBrandDTO(brandDto);
		
		productDTO.setProductAttributes(getAttributeList());
		return productDTO;
	}
	
	private List<AttributeDTO> getAttributeList() {
		List<AttributeDTO> attributes = new ArrayList<>();
		AttributeDTO attributeDto = new AttributeDTO();
		attributeDto.setAttributeName("Shipping Msg");
		attributeDto.setPlaceHolder("PDPP,PDPC");
		attributeDto.setStartDate(Instant.now().minus(2, ChronoUnit.DAYS));
		attributeDto.setEndDate(Instant.now().plus(2, ChronoUnit.DAYS));
		attributes.add(attributeDto);
		
		AttributeDTO attributeDto1 = new AttributeDTO();
		attributeDto1.setAttributeName("Single Day Delivery");
		attributeDto1.setPlaceHolder("PDPP");
		attributeDto1.setStartDate(Instant.now().minus(2, ChronoUnit.DAYS));
		attributeDto1.setEndDate(Instant.now().plus(2, ChronoUnit.DAYS));
		attributes.add(attributeDto1);

		return attributes;
	}
	

	
	private ResponseEntity<BaseServiceDTO<List<CategoryDTO>>> createCategoryDummyData(String siteId, List<String> categoryIds) {
		List<CategoryDTO> categoryDTOs = new ArrayList<>();
		for (String categories: categoryIds){
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setSiteId(siteId);
			categoryDTO.setCatalogId(categories);
			categoryDTOs.add(categoryDTO);
		}
		BaseServiceDTO<List<CategoryDTO>> baseServiceDTO = new BaseServiceDTO<>();
		baseServiceDTO.setData(categoryDTOs);
		baseServiceDTO.setResponseSentTime(Instant.now());
		baseServiceDTO.setServiceStatus(ServiceStatus.SUCCESS);
		baseServiceDTO.setPageSize(1);
		baseServiceDTO.setPageFrom(1);
		baseServiceDTO.setPageTo(1);
		return new ResponseEntity<BaseServiceDTO<List<CategoryDTO>>>(baseServiceDTO, HttpStatus.OK);
	}

}


