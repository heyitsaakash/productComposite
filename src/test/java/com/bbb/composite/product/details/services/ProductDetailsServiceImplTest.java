package com.bbb.composite.product.details.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.bbb.composite.product.details.dto.CategoryDTO;
import com.bbb.composite.product.details.dto.CategorySummaryDTO;
import com.bbb.composite.product.details.dto.ProductCategoryCompositeDTO;
import com.bbb.composite.product.details.dto.ProductCompositeDTO;
import com.bbb.composite.product.details.dto.price.ProductPriceDTO;
import com.bbb.composite.product.details.dto.price.SkuPriceDTO;
import com.bbb.composite.product.details.dto.product.ProductDTO;
import com.bbb.composite.product.details.dto.sku.SkuDTO;
import com.bbb.composite.product.details.services.client.CategoryIntegrationService;
import com.bbb.composite.product.details.services.client.PriceIntegrationService;
import com.bbb.composite.product.details.services.client.ProductIntegrationService;
import com.bbb.composite.product.details.services.client.SkuIntegrationService;
import com.bbb.core.exceptions.DataNotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class }, mergeMode = MergeMode.MERGE_WITH_DEFAULTS)
@TestPropertySource(locations = "classpath:bootstrap-test_local.properties")
public class ProductDetailsServiceImplTest {
	
	@Autowired
	@Qualifier("com.bbb.composite.product.details.services.ProductDetailsServiceImpl")
	@InjectMocks
	private ProductDetailsService productDetailsService;
	
	@Mock
	private ProductIntegrationService productIntegrationService;
	
	@Mock
	private PriceIntegrationService priceIntegrationService;
	
	@Mock
	private SkuIntegrationService skuIntegrationService;
	
	@Mock
	private CategoryIntegrationService categoryIntegrationService;
	
	private String productId = "123432";
	private String siteId = "BedBathUS";
	private String bbbChannel = "Web";
	
	@Test
	public void getProductDetails() {
		ProductDTO productDto = createProductData();
		mockProductIntegrationServices(productId, siteId, bbbChannel, productDto);
		mockProductPriceService(productId, siteId, bbbChannel);
		mockSkuService(productId, siteId, bbbChannel, productDto);
		mockSkuPriceService(productId, siteId);
		ProductCompositeDTO productCompositeDTO = productDetailsService.getProductDetails(productId, siteId, bbbChannel);
		Assert.assertEquals(productCompositeDTO.getProductId(), productId);
		Assert.assertEquals(productCompositeDTO.getSiteId(), siteId);
	}
	
	@Test
	public void getProductCategoryDetails() {
		ProductDTO productDto = createProductData();
		mockProductIntegrationServices(productId, siteId, bbbChannel, productDto);
		//mockProductPriceService(productId, siteId, bbbChannel);
		//mockSkuService(productId, siteId, bbbChannel, productDto);
		//mockSkuPriceService(productId, siteId);
		
		mockCategoryService(productId, siteId);
		ProductCategoryCompositeDTO productCategoryCompositeDTO = productDetailsService.getProductCategoryDetails(productId, siteId, bbbChannel);
		Assert.assertEquals(productCategoryCompositeDTO.getProductId(), productId);
		//Assert.assertEquals(productCategoryCompositeDTO.getCategoryList().get(0).getCategoryId(), siteId);
	}
	
	@Test(expected=DataNotFoundException.class)
	public void getProductDetailsProductPriceNotFound() {
		ProductDTO productDto = createProductData();
		mockProductIntegrationServices(productId, siteId, bbbChannel, productDto);
		productDetailsService.getProductDetails(productId, siteId, bbbChannel);
	}
	
	@Test(expected=DataNotFoundException.class)
	public void getProductDetailsChildSkusNotFound() {
		
		ProductDTO productDto = createProductData();
		mockProductIntegrationServices(productId, siteId, bbbChannel, productDto);
		mockProductPriceService(productId, siteId, bbbChannel);
		productDetailsService.getProductDetails(productId, siteId, bbbChannel);
	}
	
	@Test(expected=DataNotFoundException.class)
	public void getProductDetailsSkusPriceNotFound() {
		
		ProductDTO productDto = createProductData();
		mockProductIntegrationServices(productId, siteId, bbbChannel, productDto);
		mockProductPriceService(productId, siteId, bbbChannel);
		mockSkuService(productId, siteId, bbbChannel, productDto);
		productDetailsService.getProductDetails(productId, siteId, bbbChannel);
	}
	
	private ProductDTO createProductData() {
		ProductDTO productDto = new ProductDTO();
		productDto.setProductId(productId);
		productDto.setSiteId(siteId);
		List<String> childSKUs = new ArrayList<>();
		childSKUs.add("121");
		productDto.setChildSKUs(childSKUs);
		
		List<CategorySummaryDTO> categoryList = new ArrayList<>();
		CategorySummaryDTO categorySummaryDTO = new CategorySummaryDTO();
		categorySummaryDTO.setCategoryId("1");
		categorySummaryDTO.setSeqNum(1);
		categoryList.add(categorySummaryDTO);
		productDto.setRelatedCategories(categoryList);
		return productDto;
	}
	
	private void mockProductIntegrationServices(String productId, String siteId, String bbbChannel, ProductDTO productDto) {
		Mockito.when(productIntegrationService.getProductDetails(productId, siteId, bbbChannel)).thenReturn(productDto);
	}
	
	private void mockProductPriceService(String productId, String siteId, String bbbChannel) {
		ProductPriceDTO productPriceDTO = new ProductPriceDTO();
		productPriceDTO.setProductId(productId);
		productPriceDTO.setSiteId(siteId);
		productPriceDTO.setProductLowListPrice(new BigDecimal(4.0));
		Mockito.when(priceIntegrationService.getProductPriceDetails(bbbChannel, siteId, productId)).thenReturn(productPriceDTO);
	}
	
	private void mockSkuPriceService(String productId, String siteId) {
		String skuId = "121";
		List<String> childSKUs = new ArrayList<>();
		childSKUs.add(skuId);
		
		List<SkuPriceDTO> skuPriceDTOs = new ArrayList<>();
		SkuPriceDTO skuPriceDTO = new SkuPriceDTO();
		skuPriceDTO.setSkuId(skuId);
		skuPriceDTO.setSkuListPrice(new BigDecimal(4.4));
		skuPriceDTOs.add(skuPriceDTO);
		
		Mockito.when(priceIntegrationService.getSkuPriceDetails(bbbChannel, siteId, childSKUs)).thenReturn(skuPriceDTOs);
	}
	
	private void mockCategoryService(String productId, String siteId) {
		String categoryId = "1";
		List<String> categories = new ArrayList<>();
		categories.add(categoryId);
		
		List<CategoryDTO> categoryDTOs = new ArrayList<>();
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setCategoryId("1");
		categoryDTO.setIsActive(true);
		categoryDTOs.add(categoryDTO);
		
		Mockito.when(categoryIntegrationService.getCategories(bbbChannel, siteId, categories)).thenReturn(categoryDTOs);
	}
	
	private void mockSkuService(String productId, String siteId, String bbbChannel, ProductDTO productDto) {
		List<SkuDTO> skuDTOs = new ArrayList<>();
		
		List<String> childSkus = productDto.getChildSKUs();
		for (String childSku : childSkus) {
			SkuDTO skuDTO = new SkuDTO();
			skuDTO.setSkuId(childSku);
			skuDTO.setSize("M");
			skuDTO.setProductId(productId);
			skuDTOs.add(skuDTO);
		}
		Mockito.when(skuIntegrationService.getSkuDetails(bbbChannel, siteId, productDto.getChildSKUs())).thenReturn(skuDTOs);
	}
	
}
