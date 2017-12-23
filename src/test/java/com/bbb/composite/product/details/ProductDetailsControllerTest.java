package com.bbb.composite.product.details;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.bbb.composite.product.details.dto.ProductCompositeDTO;
import com.bbb.composite.product.details.dto.price.ProductPriceDTO;
import com.bbb.composite.product.details.dto.price.SkuPriceDTO;
import com.bbb.composite.product.details.dto.product.BrandDTO;
import com.bbb.composite.product.details.dto.product.ProductDTO;
import com.bbb.composite.product.details.dto.sku.ColorVariantDTO;
import com.bbb.composite.product.details.dto.sku.SKUVariantValuesDTO;
import com.bbb.composite.product.details.dto.sku.SizeVariantDTO;
import com.bbb.composite.product.details.dto.sku.SkuDTO;
import com.bbb.composite.product.details.services.client.PriceIntegrationServiceImpl;
import com.bbb.composite.product.details.services.client.ProductIntegrationServiceImpl;
import com.bbb.composite.product.details.services.client.SkuIntegrationServiceImpl;
import com.bbb.core.cache.dto.CacheableDTO;
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
public class ProductDetailsControllerTest {

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
	public void getProductDetailsNoCacheTest() {
		String productId = "productId_4";
		String siteId = "siteId_2";
		String channel = "web";
		List<String> childSKUs = new ArrayList<>();
		childSKUs.add("240");
		childSKUs.add("241");
		
		ParameterizedTypeReference<BaseServiceDTO<ProductCompositeDTO>> productCompositeType = new ParameterizedTypeReference<BaseServiceDTO<ProductCompositeDTO>>() {};
		
		mockRedisLoadNotExistant(productId, siteId, channel, childSKUs);
		mockProductServiceAndLB(productId, siteId, childSKUs);
		mockSkuServiceAndLB(siteId, childSKUs);
		mockProductPriceServiceAndLB(productId, siteId);
		mockSkuPriceServiceAndLB(siteId, childSKUs);
		String uri = "/product-details/site/" + siteId + "/product/" + productId;

		BodySpec<BaseServiceDTO<ProductCompositeDTO>, ?> bodySpec = this.webTestClient.get().uri(uri)
				.accept(MediaType.APPLICATION_JSON_UTF8).exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody(productCompositeType);

		assertNotNull(bodySpec);
		BaseServiceDTO<ProductCompositeDTO> responseBody = bodySpec.returnResult().getResponseBody();
		assertNotNull(responseBody);
		assertEquals(responseBody.getServiceStatus(), ServiceStatus.SUCCESS);
		assertEquals(responseBody.getData().getProductId(), productId);
	}
	
	@Test
	public void getProductDetailsFromCacheTest() {
		String productId = "productId_4";
		String siteId = "siteId_2";
		String channel = "web";
		List<String> childSKUs = new ArrayList<>();
		childSKUs.add("240");
		childSKUs.add("241");
		
		ParameterizedTypeReference<BaseServiceDTO<ProductCompositeDTO>> productCompositeType = new ParameterizedTypeReference<BaseServiceDTO<ProductCompositeDTO>>() {};
		
		mockRedisLoadExistant(productId, siteId, channel, childSKUs);
		mockProductServiceAndLB(productId, siteId, childSKUs);
		mockSkuServiceAndLB(siteId, childSKUs);
		mockProductPriceServiceAndLB(productId, siteId);
		mockSkuPriceServiceAndLB(siteId, childSKUs);
		String uri = "/product-details/site/" + siteId + "/product/" + productId + "?channel=" + channel;

		BodySpec<BaseServiceDTO<ProductCompositeDTO>, ?> bodySpec = this.webTestClient.get().uri(uri)
				.accept(MediaType.APPLICATION_JSON_UTF8).exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody(productCompositeType);

		assertNotNull(bodySpec);
		BaseServiceDTO<ProductCompositeDTO> responseBody = bodySpec.returnResult().getResponseBody();
		assertNotNull(responseBody);
		assertEquals(responseBody.getServiceStatus(), ServiceStatus.SUCCESS);
		assertEquals(responseBody.getData().getProductId(), productId);
	}
	
	@Test
	public void getProductDetailsNegativeTest() {
		String productId = "10417205479";
		String siteId = "siteId_2";
		String channel = "web";
		List<String> childSKUs = new ArrayList<>();
		childSKUs.add("240");
		childSKUs.add("241");
		
		ParameterizedTypeReference<BaseServiceDTO<ProductCompositeDTO>> productCompositeType = new ParameterizedTypeReference<BaseServiceDTO<ProductCompositeDTO>>() {};
		
		String uri = "/product-details/site/" + siteId + "/product/" + productId + "?channel=" + channel;

		BodySpec<BaseServiceDTO<ProductCompositeDTO>, ?> bodySpec = this.webTestClient.get().uri(uri)
				.accept(MediaType.APPLICATION_JSON_UTF8).exchange()
				.expectStatus().isNotFound()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody(productCompositeType);

		assertNotNull(bodySpec);
		BaseServiceDTO<ProductCompositeDTO> responseBody = bodySpec.returnResult().getResponseBody();
		assertNotNull(responseBody);
		assertEquals(responseBody.getServiceStatus(), ServiceStatus.ERROR);
	}
	
	
	private void mockRedisLoadNotExistant(String productId, String siteId, String bbbChannel, List<String> childSKUs) {
		Mockito.when(coreCacheManager.getFromCacheSingle(Mockito.anyString(), Mockito.any()))
		.thenReturn(null);
	}
	
	private void mockRedisLoadExistant(String productId, String siteId, String bbbChannel, List<String> childSKUs) {
		String productDtoKey = this.getProductCacheKey(productId, siteId, bbbChannel);
		ResponseEntity<BaseServiceDTO<ProductDTO>> responseEntity = createProductDummyData(productId, childSKUs);
		BaseServiceDTO<ProductDTO> baseServiceDTO = responseEntity.getBody();
		CacheableDTO<BaseServiceDTO<ProductDTO>> cacheableDTO = new CacheableDTO<>();
		cacheableDTO.setKey(productDtoKey);
		cacheableDTO.setData(baseServiceDTO);
		
		Mockito.when(coreCacheManager.getFromCacheSingle(productDtoKey, ProductDTO.class))
		.thenReturn(cacheableDTO);
		
		String productPriceCacheKey = this.getProductPriceCacheKey(siteId, productId, bbbChannel);
		ResponseEntity<BaseServiceDTO<ProductPriceDTO>> responseEntity2 = createProductPriceDummyData(productId, siteId);
		BaseServiceDTO<ProductPriceDTO> baseServiceDTO2 = responseEntity2.getBody();
		CacheableDTO<BaseServiceDTO<ProductPriceDTO>> cacheableDTO2 = new CacheableDTO<>();
		cacheableDTO2.setKey(productPriceCacheKey);
		cacheableDTO2.setData(baseServiceDTO2);
		
		Mockito.when(coreCacheManager.getFromCacheSingle(productPriceCacheKey, ProductPriceDTO.class))
		.thenReturn(cacheableDTO2);
		
		String skuPriceCacheKey = this.getSkuPriceCacheKey(siteId, childSKUs, bbbChannel);
		ResponseEntity<BaseServiceDTO<List<SkuPriceDTO>>> responseEntity3 = createSkuPriceDummyData(siteId, childSKUs);
		BaseServiceDTO<List<SkuPriceDTO>> baseServiceDTO3 = responseEntity3.getBody();
		CacheableDTO<BaseServiceDTO<List<SkuPriceDTO>>> cacheableDTO3 = new CacheableDTO<>();
		cacheableDTO3.setKey(skuPriceCacheKey);
		cacheableDTO3.setData(baseServiceDTO3);
		
		Mockito.when(coreCacheManager.getFromCacheList(skuPriceCacheKey, SkuPriceDTO.class))
		.thenReturn(cacheableDTO3);
		
		String skusCacheKey = this.getSkusCacheKey(siteId, childSKUs, bbbChannel);
		ResponseEntity<BaseServiceDTO<List<SkuDTO>>> responseEntity4 = createSkuDummyData(childSKUs);
		BaseServiceDTO<List<SkuDTO>> baseServiceDTO4 = responseEntity4.getBody();
		CacheableDTO<BaseServiceDTO<List<SkuDTO>>> cacheableDTO4 = new CacheableDTO<>();
		cacheableDTO4.setKey(skusCacheKey);
		cacheableDTO4.setData(baseServiceDTO4);
		
		Mockito.when(coreCacheManager.getFromCacheList(skusCacheKey, SkuDTO.class))
		.thenReturn(cacheableDTO4);
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
	
	private void mockSkuServiceAndLB(String siteId, List<String> childSKUs) {
		
		String skuUrl = mockLoadBalancerAndGetUrl(SkuIntegrationServiceImpl.CLIENT_ID
													, skuMSServers, "/sku/site/{siteId}/sku/{childSkus}");
		List<Object> argsList = new ArrayList<>(3);
		argsList.add(siteId);
		argsList.add(childSKUs);
		ParameterizedTypeReference<BaseServiceDTO<List<SkuDTO>>> RESPONSE_TYPE = new ParameterizedTypeReference<BaseServiceDTO<List<SkuDTO>>>() {};
		Mockito.when(defaultRestTemplate.exchange(skuUrl, 
				HttpMethod.GET, 
				null, 
				RESPONSE_TYPE, 
				CoreUtils.toArray(argsList)))
		.thenReturn(createSkuDummyData(childSKUs));
	}
	
	private void mockProductPriceServiceAndLB(String productId, String siteId) {
		
		String priceUrl = mockLoadBalancerAndGetUrl(PriceIntegrationServiceImpl.CLIENT_ID, skuMSServers, "/price/site/{siteId}/product/{productId}");
		List<Object> argsList = new ArrayList<>(3);
		argsList.add(siteId);
		argsList.add(productId);
		ParameterizedTypeReference<BaseServiceDTO<ProductPriceDTO>> RESPONSE_TYPE = new ParameterizedTypeReference<BaseServiceDTO<ProductPriceDTO>>() {};
		Mockito.when(defaultRestTemplate.exchange(priceUrl, 
				HttpMethod.GET, 
				null, 
				RESPONSE_TYPE, 
				CoreUtils.toArray(argsList)))
		.thenReturn(createProductPriceDummyData(productId, siteId));
	}
	
	private void mockSkuPriceServiceAndLB(String siteId, List<String> childSKUs) {
		String priceUrl = mockLoadBalancerAndGetUrl(PriceIntegrationServiceImpl.CLIENT_ID, skuMSServers, "/price/site/{siteId}/sku/{skuId}");
		List<Object> argsList = new ArrayList<>(3);
		argsList.add(siteId);
		argsList.add(childSKUs);
		ParameterizedTypeReference<BaseServiceDTO<List<SkuPriceDTO>>> RESPONSE_TYPE = new ParameterizedTypeReference<BaseServiceDTO<List<SkuPriceDTO>>>() {};
		Mockito.when(defaultRestTemplate.exchange(priceUrl, 
				HttpMethod.GET, 
				null, 
				RESPONSE_TYPE, 
				CoreUtils.toArray(argsList)))
		.thenReturn(createSkuPriceDummyData(siteId, childSKUs));
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
	
	private ResponseEntity<BaseServiceDTO<List<SkuDTO>>> createSkuDummyData(List<String> childSKUs) {
		List<SkuDTO> skuDtos = new ArrayList<>();
		for (int i=0; i<2; i++) {
			SkuDTO skuDTO = new SkuDTO();
			skuDTO.setSkuId("24"+i);
			SKUVariantValuesDTO  skuVariantValues = new SKUVariantValuesDTO();
			
			ColorVariantDTO colorVariantDto = new ColorVariantDTO();
			colorVariantDto.setColorCode("RED");
			colorVariantDto.setDisplayName("RED");
			skuVariantValues.setColorVariant(colorVariantDto);
			
			SizeVariantDTO sizeVariantDTO = new SizeVariantDTO();
			sizeVariantDTO.setSizeCode("Large");
			sizeVariantDTO.setDisplayName("Large");
			skuVariantValues.setSizeVariant(sizeVariantDTO);
			
			skuDTO.setSkuVariantValues(skuVariantValues);
			skuDtos.add(skuDTO);
			skuDTO.setSkuAttributes(getAttributeList());
		}
		BaseServiceDTO<List<SkuDTO>> baseServiceDTO = new BaseServiceDTO<>();
		baseServiceDTO.setData(skuDtos);
		baseServiceDTO.setResponseSentTime(Instant.now());
		baseServiceDTO.setServiceStatus(ServiceStatus.SUCCESS);
		baseServiceDTO.setPageSize(1);
		baseServiceDTO.setPageFrom(1);
		baseServiceDTO.setPageTo(1);
		return new ResponseEntity<BaseServiceDTO<List<SkuDTO>>>(baseServiceDTO, HttpStatus.OK);
	}
	
	private ResponseEntity<BaseServiceDTO<ProductPriceDTO>> createProductPriceDummyData(String productId, String siteId) {
		ProductPriceDTO productPriceDTO = new ProductPriceDTO();
		productPriceDTO.setProductId(productId);
		productPriceDTO.setSiteId(siteId);
		productPriceDTO.setProductLowListPrice(new BigDecimal(2.4));
		productPriceDTO.setProductHighListPrice(new BigDecimal(5));
		productPriceDTO.setProductLowSalePrice(new BigDecimal(2));
		productPriceDTO.setProductHighSalePrice(new BigDecimal(4));
		productPriceDTO.setPriceRangeDescription("%L - %H");
		BaseServiceDTO<ProductPriceDTO> baseServiceDTO = new BaseServiceDTO<>();
		baseServiceDTO.setData(productPriceDTO);
		baseServiceDTO.setResponseSentTime(Instant.now());
		baseServiceDTO.setServiceStatus(ServiceStatus.SUCCESS);
		baseServiceDTO.setPageSize(1);
		baseServiceDTO.setPageFrom(1);
		baseServiceDTO.setPageTo(1);
		return new ResponseEntity<BaseServiceDTO<ProductPriceDTO>>(baseServiceDTO, HttpStatus.OK);
	}
	
	private ResponseEntity<BaseServiceDTO<List<SkuPriceDTO>>> createSkuPriceDummyData(String siteId, List<String> childSkus) {
		List<SkuPriceDTO> skuPriceDTOs = new ArrayList<>();
		for (String childSku: childSkus){
			SkuPriceDTO skuPriceDTO = new SkuPriceDTO();
			skuPriceDTO.setSiteId(siteId);
			skuPriceDTO.setSkuId(childSku);
			skuPriceDTO.setSkuListPrice(new BigDecimal(10));
			skuPriceDTO.setSkuSalePrice(new BigDecimal(8));
			skuPriceDTOs.add(skuPriceDTO);
		}
		BaseServiceDTO<List<SkuPriceDTO>> baseServiceDTO = new BaseServiceDTO<>();
		baseServiceDTO.setData(skuPriceDTOs);
		baseServiceDTO.setResponseSentTime(Instant.now());
		baseServiceDTO.setServiceStatus(ServiceStatus.SUCCESS);
		baseServiceDTO.setPageSize(1);
		baseServiceDTO.setPageFrom(1);
		baseServiceDTO.setPageTo(1);
		return new ResponseEntity<BaseServiceDTO<List<SkuPriceDTO>>>(baseServiceDTO, HttpStatus.OK);
	}

	private String getProductCacheKey(String productId, String siteId, String bbbChannel) {
		StringBuilder sb = new StringBuilder();
		sb.append("product-microservice").append("-siteId-").append(siteId).append("-productId-").append(productId);
		if (StringUtils.isNotBlank(bbbChannel)) {
			sb.append("-bbbChannel-").append(bbbChannel);
		}
		return sb.toString();
	}
	
	private String getProductPriceCacheKey(String siteId, String productId, String bbbChannel) {
		StringBuilder sb = new StringBuilder();
		sb.append("price-microservice").append("-siteId-").append(siteId)
					.append("-productId-").append(productId);
		if (StringUtils.isNotBlank(bbbChannel)) {
			sb.append("-bbbChannel-").append(bbbChannel);
		}
		return sb.toString();
	}
	
	private String getSkuPriceCacheKey(String siteId, List<String> skus, String bbbChannel) {
		StringBuilder sb = new StringBuilder();
		sb.append("price-microservice").append("-siteId-").append(siteId);
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
	
	private String getSkusCacheKey(String siteId, List<String> childSkus, String bbbChannel) {
		StringBuilder sb = new StringBuilder();
		sb.append("sku-microservice").append("-siteId-").append(siteId);
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


